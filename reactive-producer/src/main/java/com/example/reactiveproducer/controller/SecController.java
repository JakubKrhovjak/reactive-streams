package com.example.reactiveproducer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author Jakub Krhovjak
 */

@RestController
public class SecController {

    @GetMapping("/basic")
    @PreAuthorize("hasAuthority('USER')")
    public Mono<String> basic(ServerWebExchange ex) {
        return Mono.just("ok");
    }

}

