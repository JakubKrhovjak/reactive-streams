package com.example.reactiveproducer.service;

import com.example.reactiveproducer.entity.User;
import com.example.reactiveproducer.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 3/28/20.
 */

//@Service
@RequiredArgsConstructor
public class MongoUserDetailService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return Mono.just(new User().setUsername("user").setPassword("user").setRoles(List.of("ROLE_USER")));
//        return userRepository.findByUsername(username).switchIfEmpty(Mono.defer(() -> {
//            return Mono.error(new UsernameNotFoundException("User Not Found"));
//        })).map(User::toUserDetails);
    }
}