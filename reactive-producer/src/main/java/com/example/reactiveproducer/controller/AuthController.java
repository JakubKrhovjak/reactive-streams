package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.security.jwt.AuthUtils;
import com.example.reactiveproducer.service.DbUserDetailService;
import java.util.Objects;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * Created by Jakub krhovják on 4/5/20.
 */

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final DbUserDetailService userDetailsService;

    private final AuthUtils authUtils;

    private final PasswordEncoder passwordEncoder;

    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());

    @PostMapping(value = "/sign-in")
    public Mono<String> signIn(@RequestBody(required = false) String username) {
        return userDetailsService.findByUsername(username)
            .filter(Objects::nonNull)
            .map(UserDetails::getUsername);
    }

    @PostMapping(value = "/new-account")
    public Mono<Void> newAccount(@RequestBody AuthUtils.AuthCredential newCredential) {
        userDetailsService.newAccount(newCredential);
        return Mono.empty();
    }


    @GetMapping(value = "/login")
    public Mono<ResponseEntity<JwtTokenResponse>> generateToken(@RequestHeader("Authorization") Optional<String> authorization) {
        AuthUtils.AuthCredential credential = authorization
            .map(auth -> authUtils.getToken(auth, AuthUtils.AuthType.BASIC))
            .map(authUtils::decode)
            .map(authUtils::getCredential)
            .orElse(AuthUtils.AuthCredential.UNAUTHORIZED);

        return userDetailsService.findByUsername(credential.getUsername())
            .publishOn(Schedulers.parallel())
            .filter(details -> passwordEncoder.matches(credential.getPassword(), details.getPassword()))
            .map(details -> ResponseEntity.ok(new JwtTokenResponse(authUtils.generateToken(details))))
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping(value = "/")
    public Mono<String> signIan(@RequestBody(required = false) String username) {
        return userDetailsService.findByUsername(username)
            .filter(Objects::nonNull)
            .map(UserDetails::getUsername);
    }


    @Data
    public class JwtTokenResponse {

        final String jwtAuthToken;
    }
}
