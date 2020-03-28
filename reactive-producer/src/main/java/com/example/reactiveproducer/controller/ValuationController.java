package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.entity.Valuation;
import com.example.reactiveproducer.repository.ValuationRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;


/**
 * @author Jakub krhovjak
 */
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ValuationController {

    private final ValuationRepository valuationRepository;
    Flux<Valuation> stream;

    @PostConstruct
    public void init() throws InterruptedException {
//        Disposable dfsd = valuationRepository.save(new Valuation().setName("2").setDescription("3").setId("dfsd")).subscribe();
        Disposable subscription;
         stream = findAll();
//        Flux<Valuation> stream = valuationRepository.findValuation();
        stream.doOnNext(valuation -> System.err.println("---------" + valuation)).subscribe();
//          Disposable dfsd = valuationRepository.save(new Valuation().setName("2").setDescription("3")).subscribe();
//          Thread.sleep(2000);
//     valuationRepository.save(new Valuation().setName("2").setDescription("4")).subscribe();
    }


    @Tailable
    public Flux<Valuation> findAll() {
        return valuationRepository.findAll();
    }


    @GetMapping("valuations")
    public Flux<Valuation> valuation() {
        valuationRepository.save(new Valuation().setName("5").setDescription("4")).subscribe();
//        stream.doOnNext(valuation -> System.err.println("---------" + valuation)).;
        return valuationRepository.findAll();
    }

    @GetMapping("valuations2")
    public void valuation2() {
//        List<Valuation> all = repository.findAll();

//        List<ValuationResponse> measure =  measure("Dozer", dozerMapper, all);
////        List<ValuationResponse> measure2 =  measure("Orika", new DefaultMapperFactory.Builder().build().getDefault(), all);
//
//        long start = System.nanoTime();
//        List<ValuationResponse> collect = all.stream()
//            .map(v -> boundMapper.map(v))
//            .collect(toList());
//        long end = System.nanoTime();
//
//        log.info("Orika bound: {}", (end - start) / 1000000);
//
//
//
//        long start4 = System.nanoTime();
//        List<ValuationResponse> collect4 = all.stream()
//            .map(v -> mapperFacade.map(v, ValuationResponse.class))
//            .collect(toList());
//        long end4 = System.nanoTime();
//        log.info("Orika : {}", (end4 - start4) / 1000000);
//
//
//        long start5 = System.nanoTime();
//        List<ValuationResponse> collect5 = all.stream()
//            .map(v -> modelMapper.map(v, ValuationResponse.class))
//            .collect(toList());
//        long end5 = System.nanoTime();
//        log.info("Model mapper : {}", (end5 - start5) / 1000000);
//

    }

//
//    List<ValuationResponse> measure(String label, Mapper mapper,  List<Valuation> all) {
//        long start = System.nanoTime();
//        List<ValuationResponse> collect = all.stream()
//            .map(v -> modelMapper.map(v, ValuationResponse.class))
//            .collect(toList());
//        long end = System.nanoTime();
//        log.info(label +" Elapsed: {}",  (end - start) / 1000000);
//        return collect;
//    }


}
