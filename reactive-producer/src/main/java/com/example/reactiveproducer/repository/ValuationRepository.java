package com.example.reactiveproducer.repository;

import com.example.reactiveproducer.entity.Valuation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Jakub krhovjak
 */
@Repository
public interface ValuationRepository extends ReactiveMongoRepository<Valuation, String> {
}
