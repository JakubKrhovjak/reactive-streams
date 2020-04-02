package com.example.reactiveproducer.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;


/**
 * Created by Jakub krhovják on 3/31/20.
 */
public class JwtAuthenticator {

    @Value("${jwt-secret-key}")
    private String jwtSecretKey;

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

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

}
