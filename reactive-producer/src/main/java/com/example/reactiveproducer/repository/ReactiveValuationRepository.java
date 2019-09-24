package com.example.reactiveproducer.repository;

import com.example.reactiveproducer.entity.Valuation;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


/**
 * @author Jakub Krhovjak
 */
public interface ReactiveValuationRepository extends ReactiveCrudRepository<Valuation, Long> {

    @Query("select v from Valuation v where v.state = 'FINISHED'")
    Flux<Valuation> changed();
}
