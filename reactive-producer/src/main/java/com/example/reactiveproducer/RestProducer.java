package com.example.reactiveproducer;

import com.example.reactiveproducer.entity.Valuation;
import com.example.reactiveproducer.repository.ReactiveValuationRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;


/**
 * Created by Jakub krhovják on 6/8/19.
 */
@CrossOrigin
@RestController

@Slf4j
public class RestProducer {

    //    @Autowired
    //    ValuationRepository valuationRepository;

    @Autowired
    ReactiveValuationRepository reactiveCrudRepository;


    //    @GetMapping(value = "/flux-rest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //    public Flux<String> getRestMessage() {
    //        log.info("info");
    //
    //        return Flux.interval(Duration.ofSeconds(2))
    //            .onErrorContinue((e, value) -> log.info("Event Canceled!"))
    //            .map(sequence -> valuationRepository.findAll().get(0).getState())
    //            .doOnNext(signal -> {
    //                log.info("onNext");
    //            })
    //            .doOnCancel(() -> log.info("Cancel"))
    //            .doFinally(signal -> {
    //                log.info("Streaming finished: {}", signal);
    //            });
    //    }



    @GetMapping(value = "/flux-rest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Valuation> getRestMessageR() {
        log.info("info");



        Disposable subscribe = reactiveCrudRepository.findAll().subscribe(v -> log.info("Val: {}", v.getState()));
        return reactiveCrudRepository.changed()
            .doOnError(signal -> log.info("onError: {}", signal))
            .doOnNext(signal -> log.info("onNext: {}", signal))
            .repeat();



        //        return Flux.interval(Duration.ofSeconds(2))
        //            .flatMap(V -> reactiveCrudRepository.findAll())
        ////            .onErrorContinue((e, value) -> log.info("Event Canceled!"))
        ////            .
        ////             .subscribe(sequence -> reactiveCrudRepository.findAll())
        //
        //             .doOnNext(signal -> {
        //                log.info("onNext: {}", signal);
        //            })
        //            .doOnCancel(() -> log.info("Cancel"))
        //            .doFinally(signal -> {
        //                log.info("Streaming finished: {}", signal);
        //            });
    }


    @Autowired
    DatabaseClient  client;

    @GetMapping(value = "/test")
    public Flux<Valuation> ok() {
        //"\"select v from Valuation v where v.state = 'FINISHED'\""
//        return client.execute("select v from Valuation v where v.state = 'FINISHED")
//            .from(Valuation.class)
//            .fetch()
//            .first();
        return client.select()
            .from(Valuation.class)
            .fetch()
            .all()
            .doOnNext(it ->  log.info("{}", it))

            .as(Valuation:: getState)
            .expectNextCount(1)
            .verifyComplete();

//        reactiveCrudRepository.findAll().subscribe(v -> log.info("{}", v)).dispose();
//        return reactiveCrudRepository.findAll();
    }

    ;
}
