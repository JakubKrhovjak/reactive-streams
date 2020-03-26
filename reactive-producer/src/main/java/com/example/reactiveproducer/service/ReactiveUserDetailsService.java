package com.example.reactiveproducer.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 3/26/20.
 */

@Service
public class ReactiveUserDetailsService implements ReactiveUserDetailsPasswordService {

//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        Optional<UserDetails> maybeUser = users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst();
//        return maybeUser.map(Mono::just).orElse(Mono.empty());
//    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails userDetails, String s) {
        return null;
    }
}
