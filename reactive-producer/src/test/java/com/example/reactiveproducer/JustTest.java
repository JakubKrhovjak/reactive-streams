package com.example.reactiveproducer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.List;


/**
 * Created by Jakub krhovják on 4/4/20.
 */


public class JustTest {

    public static final String TOKEN_TYPE = "JWT";

    public static final String TOKEN_ISSUER = "secure-api";

    public static final String TOKEN_AUDIENCE = "secure-app";

    private String jwtSecretKey = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";


    @org.junit.jupiter.api.Test
    void name() {

        String token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setIssuer(TOKEN_ISSUER)
            .setAudience(TOKEN_AUDIENCE)
            .setSubject("test")
            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
            .claim("rol", List.of("USER"))
            .compact();

        byte[] encode = Base64.getEncoder().encode(jwtSecretKey.getBytes());


        Jwts.parserBuilder()
            .setSigningKey(jwtSecretKey.getBytes())

//            .setSigningKey(Base64.getEncoder().encode(jwtSecretKey.getBytes()))
            .requireIssuer(TOKEN_ISSUER)
            .requireAudience(TOKEN_AUDIENCE)
            .build()
            .parseClaimsJws(token);

    }
}
