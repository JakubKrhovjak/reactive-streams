package com.example.reactiveproducer;

import com.example.reactiveproducer.repository.ValuationRepository;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


/**
 * Created by Jakub krhovják on 6/8/19.
 */
@CrossOrigin
@RestController

@Slf4j
public class RestProducer {

    @Autowired
    ValuationRepository valuationRepository;


    @GetMapping(value = "/flux-rest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getRestMessage() {
        log.info("info");

        return Flux.interval(Duration.ofSeconds(2))
            .onErrorContinue((e, value) -> log.info("Event Canceled!"))
            .map(sequence -> valuationRepository.findAll().get(0).getState())
            .doOnNext(signal -> {
                log.info("onNext");
            })
            .doOnCancel(() ->  log.info("Cancel"))
            .doFinally(signal -> {
                log.info("Streaming finished: {}", signal);
            });
    }

    @GetMapping(value = "/test")
    public String ok() {
            return "ok";
    }

;
}
