package com.example.reactiveproducer.repository;

import com.example.reactiveproducer.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 3/28/20.
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);
}