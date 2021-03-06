package com.example.reactiveproducer.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.impl.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 3/31/20.
 */

@Slf4j
public class AuthUtils {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${jwt.token-valididy}")
    private long tokenValidity;

    public enum AuthType {
        BASIC,
        BEARER;

        public String value() {
            return StringUtil.capitalize(name().toLowerCase());
        }
    }

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final String ROLES = "roles";

    public String generateToken(UserDetails details) {
        return Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setIssuer(TOKEN_ISSUER)
            .setAudience(TOKEN_AUDIENCE)
            .setSubject(details.getUsername())
            .setExpiration(Date.from(Instant.now().plusMillis(tokenValidity)))
            .claim(ROLES, details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
            .compact();
    }

    public Mono<Jws<Claims>> getClaims(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
            .map(auth -> getToken(auth.getCredentials().toString(), AuthType.BEARER))
            .flatMap(this::parseJwtToken);
    }

    public Mono<Authentication> getAuthorization(Jws<Claims> claims) {
        String username = claims.getBody().getSubject();
        return Mono.just(new UsernamePasswordAuthenticationToken(username, null, getAuthorities(claims)));
    }

    public String getAuthPayload(ServerWebExchange exchange) {
        return exchange.getRequest()
            .getHeaders()
            .getFirst(HttpHeaders.AUTHORIZATION);
    }

    public String getToken(String auth, AuthType type) {
        return auth.replace(type.value(), StringUtils.EMPTY).trim();
    }

    public String decode(String token) {
        byte[] credDecoded = Base64.getDecoder().decode(token);
        return new String(credDecoded, StandardCharsets.UTF_8);
    }

    public AuthCredential getCredential(String token) {
        return Try.of(() -> {
            String[] split = token.split(":");
            return new AuthCredential(split[0], split[1]);
        }).getOrElse(AuthCredential.UNAUTHORIZED);
    }

    private Mono<Jws<Claims>> parseJwtToken(String jwtToken) {
        try {
            return Mono.just(Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes())
                .requireIssuer(TOKEN_ISSUER)
                .requireAudience(TOKEN_AUDIENCE)
                .build()
                .parseClaimsJws(jwtToken));
        } catch (Exception e) {
            return Mono.empty();
        }

    }

    private List<? extends GrantedAuthority> getAuthorities(Jws<Claims> claims) {
       return ((List<String>) claims.getBody()
            .get(ROLES)).stream().map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Data
    @RequiredArgsConstructor
    public static class AuthCredential {

        public static AuthCredential UNAUTHORIZED = new AuthCredential(StringUtils.EMPTY, StringUtils.EMPTY);

        @NonNull
        private final String username;

        @NonNull
        private final String password;

    }

}
