package com.example.reactiveproducer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * Created by Jakub krhovják on 6/8/19.
 */
@RestController
public class RestProducer {

    @GetMapping("flux-rest")
    public Flux<String> getRestMessage() {
        return Flux.fromStream(() -> Stream.generate(() -> "flux-rest message at: " + Instant.now()))
                .delayElements(Duration.ofSeconds(1));
    }
}
