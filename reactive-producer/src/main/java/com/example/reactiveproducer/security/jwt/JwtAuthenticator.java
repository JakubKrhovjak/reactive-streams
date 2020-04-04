package com.example.reactiveproducer.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.example.reactiveproducer.security.jwt.JwtAuthenticator.AuthCredential.UNAUTHORIZED;


/**
 * Created by Jakub krhovják on 3/31/20.
 */

@Slf4j
public class JwtAuthenticator {

    @Value("${jwt-secret-key}")
    private String jwtSecretKey;

    enum AuthType {
        BASIC,
        BEARER;

        public String value() {
            return StringUtil.capitalize(name().toLowerCase());
        }
    }

    public static final String TOKEN_TYPE = "JWT";

    public static final String TOKEN_ISSUER = "secure-api";

    public static final String TOKEN_AUDIENCE = "secure-app";

    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());

    public String generateToken(String username) {
        return Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setIssuer(TOKEN_ISSUER)
            .setAudience(TOKEN_AUDIENCE)
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
            //            .claim("rol", roles)
            .compact();
        //            .;
    }

    public Mono<? extends Authentication> getAuthentication(ServerWebExchange exchange) {
        return extractCredential(exchange)
            .map(credential -> new UsernamePasswordAuthenticationToken(credential.username, credential.password))
            .onErrorReturn(new UsernamePasswordAuthenticationToken(UNAUTHORIZED.username, UNAUTHORIZED.password));
    }

    private Mono<AuthCredential> extractCredential(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
            .map(this::getAuthPayload)
            .map(auth ->  getToken(auth, AuthType.BASIC))
            .map(this::decode)
            .map(this::getCredential);
    }

    public Mono<Jws<Claims>> getClaims(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
            .map(this::getAuthPayload)
            .map(auth -> getToken(auth, AuthType.BEARER))
            .flatMap(this::parseJwtToken);
    }

    public Mono<Authentication> getAuthorization(Jws<Claims> claims) {
        var username = claims
            .getBody()
            .getSubject();

        var authorities = ((List<?>) claims.getBody()
            .get("rol")).stream()
            .map(authority -> new SimpleGrantedAuthority((String) authority))
            .collect(Collectors.toList());

       return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
    }

    private String getAuthPayload(ServerWebExchange exchange) {
        String auth = exchange.getRequest()
            .getHeaders()
            .getFirst(HttpHeaders.AUTHORIZATION);

        return auth == null ? StringUtils.EMPTY : auth;
    }

    private String getToken(String auth, AuthType type) {
        return auth.replace(type.value(),  StringUtils.EMPTY).trim();
    }

    private String decode(String token) {
        byte[] credDecoded = Base64.getDecoder().decode(token);
        return new String(credDecoded, StandardCharsets.UTF_8);
    }

    private AuthCredential getCredential(String token) {
        String[] split = token.split(":");
        return new AuthCredential(split[0], split[1]);
    }

    private Mono<Jws<Claims>> parseJwtToken(String jwtToken) {
        try {
           return Mono.just(Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode(jwtSecretKey.getBytes()))
                .parseClaimsJws(jwtToken));
        } catch (Exception e) {
            return Mono.empty();
        }

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
