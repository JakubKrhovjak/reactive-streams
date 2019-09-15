package com.example.reactiveproducer.controller;

import com.example.reactiveproducer.domain.ValuationResponse;
import com.example.reactiveproducer.entity.Valuation;
import com.example.reactiveproducer.repository.ValuationRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static java.util.stream.Collectors.toList;


/**
 * @author Jakub krhovjak
 */
@Slf4j
@RestController
@CrossOrigin
public class ValuationController {

    @Autowired
    private ValuationRepository repository;

    Mapper dozerMapper = new DozerBeanMapper();
    ModelMapper modelMapper = new ModelMapper();


    BoundMapperFacade<Valuation, ValuationResponse> boundMapper = new DefaultMapperFactory.Builder().build().getMapperFacade(Valuation.class, ValuationResponse.class);
    DefaultMapperFactory factory = new DefaultMapperFactory.Builder().build();

    MapperFacade mapperFacade = new DefaultMapperFactory.Builder().build().getMapperFacade();

    @GetMapping("valuations")
    public Flux<Valuation> valuation() {
        return Flux.fromIterable(repository.findAll());
    }

    @GetMapping("valuations2")
    public void valuation2() {
        List<Valuation> all = repository.findAll();

        List<ValuationResponse> measure =  measure("Dozer", dozerMapper, all);
//        List<ValuationResponse> measure2 =  measure("Orika", new DefaultMapperFactory.Builder().build().getDefault(), all);

        long start = System.nanoTime();
        List<ValuationResponse> collect = all.stream()
            .map(v -> boundMapper.map(v))
            .collect(toList());
        long end = System.nanoTime();

        log.info("Orika bound: {}", (end - start) / 1000000);



        long start4 = System.nanoTime();
        List<ValuationResponse> collect4 = all.stream()
            .map(v -> mapperFacade.map(v, ValuationResponse.class))
            .collect(toList());
        long end4 = System.nanoTime();
        log.info("Orika : {}", (end4 - start4) / 1000000);


        long start5 = System.nanoTime();
        List<ValuationResponse> collect5 = all.stream()
            .map(v -> modelMapper.map(v, ValuationResponse.class))
            .collect(toList());
        long end5 = System.nanoTime();
        log.info("Model mapper : {}", (end5 - start5) / 1000000);


    }


    List<ValuationResponse> measure(String label, Mapper mapper,  List<Valuation> all) {
        long start = System.nanoTime();
        List<ValuationResponse> collect = all.stream()
            .map(v -> modelMapper.map(v, ValuationResponse.class))
            .collect(toList());
        long end = System.nanoTime();
        log.info(label +" Elapsed: {}",  (end - start) / 1000000);
        return collect;
    }


}
