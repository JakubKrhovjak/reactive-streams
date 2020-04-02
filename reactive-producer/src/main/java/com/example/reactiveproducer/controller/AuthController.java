package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.security.jwt.JwtAuthenticator;
import com.example.reactiveproducer.service.DbUserDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Jakub krhovják on 3/31/20.
 */
@RestController

@RequiredArgsConstructor
public class AuthController {

    private final DbUserDetailService userDetailService;
    private final JwtAuthenticator authService;

//    @PostMapping("/auth")
//    public Mono<ResponseEntity<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest) {
//        UserDetails block = userDetailService.findByUsername(authRequest.getUsername()).block();
//        return userDetailService.findByUsername(authRequest.getUsername())
//            .map(user -> ok().contentType(APPLICATION_JSON_UTF8).body(
//                new AuthResponse(user.getUsername(), authService.generateToken(user)))
//            )
//            .defaultIfEmpty(notFound().build());
//    }

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
