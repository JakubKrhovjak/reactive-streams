package com.example.reactiveproducer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Jakub Krhovjak
 */

@RestController
public class SecController {


    @GetMapping("basic")
    public String basic() {
        return "ok";
    }

    @GetMapping("free")
    public String free() {
        return "ok";
    }
}
