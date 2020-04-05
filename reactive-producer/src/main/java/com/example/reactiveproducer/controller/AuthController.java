package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.security.jwt.AuthUtils;
import com.example.reactiveproducer.service.DbUserDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
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

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public Mono<ResponseEntity<JwtTokenResponse>> generateToken(@RequestHeader("Authorization") String authorization, ServerWebExchange exchange) {
        String token = authUtils.getToken(authorization, AuthUtils.AuthType.BASIC);
        String decode = authUtils.decode(token);
        AuthUtils.AuthCredential credential = authUtils.getCredential(decode);

        return userDetailsService.findByUsername(credential.getUsername())
            .publishOn(Schedulers.parallel())
            .filter(details -> passwordEncoder.matches(credential.getPassword(), details.getPassword()))
            .map(details -> ResponseEntity.ok(new JwtTokenResponse(authUtils.generateToken(new UsernamePasswordAuthenticationToken(details,
                details.getPassword(), details.getAuthorities())))))
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Data
    public class JwtTokenResponse {

        final String jwtAuthToken;
    }
}
