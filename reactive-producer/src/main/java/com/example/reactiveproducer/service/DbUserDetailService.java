package com.example.reactiveproducer.service;

import com.example.reactiveproducer.entity.User;
import com.example.reactiveproducer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 3/28/20.
 */


public class DbUserDetailService {

    @Autowired
    private UserRepository userRepository;

    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
            .switchIfEmpty(Mono.defer(Mono::empty))
            .map(User::toUserDetails);

    }

//    public Mono<UserDetails> crateuser(String username) {
//        return userRepository.save()
//
//
//    }

}