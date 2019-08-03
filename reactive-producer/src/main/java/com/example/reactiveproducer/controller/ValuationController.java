package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.entity.Valuation;
import com.example.reactiveproducer.repository.ValuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Jakub krhovjak
 */

@RestController
public class ValuationController {

    @Autowired
    private ValuationRepository repository;

    @GetMapping("valuations")
    public Flux<Valuation> valuation() {
        return Flux.fromIterable(repository.findAll());
    }
}
