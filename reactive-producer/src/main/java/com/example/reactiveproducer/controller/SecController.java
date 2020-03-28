package com.example.reactiveproducer.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * @author Jakub Krhovjak
 */

@RestController



public class SecController {

    @GetMapping("basic")
    public String basic(Mono<Principal> principal) {
        return "ok";
    }

    @GetMapping("free")
    public String free() {
        return "ok";
    }


    @GetMapping("login")
    public String login() {
        return "ok";
    }
}

