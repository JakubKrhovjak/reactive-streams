package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.security.JWTUtil;
import com.example.reactiveproducer.service.DbUserDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;


/**
 * Created by Jakub krhovják on 3/31/20.
 */
@RestController

@RequiredArgsConstructor
public class AuthController {

    private final DbUserDetailService userDetailService;

    @PostMapping("/auth")
    public Mono<ResponseEntity<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest) {
        UserDetails block = userDetailService.findByUsername(authRequest.getUsername()).block();
        return userDetailService.findByUsername(authRequest.getUsername())
            .map(user -> ok().contentType(APPLICATION_JSON_UTF8).body(
                new AuthResponse(JWTUtil.generateToken(user.getUsername(), user.getAuthorities()), user.getUsername()))
            )
            .defaultIfEmpty(notFound().build());
    }

    @Data
    @RequiredArgsConstructor
    public static class AuthResponse {
        private final String username;
        private final String token;
    }

    @Data
    @RequiredArgsConstructor
    public static class AuthRequest {
        private final String username;
        private final String password;
    }
}
