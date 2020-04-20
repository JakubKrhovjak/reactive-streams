package com.example.reactiveproducer.service;

import com.example.reactiveproducer.entity.User;
import com.example.reactiveproducer.repository.UserRepository;
import com.example.reactiveproducer.security.jwt.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 3/28/20.
 */

@Service
@RequiredArgsConstructor
public class DbUserDetailService {

    private final UserRepository userRepository;

    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
            .switchIfEmpty(Mono.defer(Mono::empty))
            .map(User::toUserDetails);

    }

    public void newAccount(AuthUtils.AuthCredential newCredential) {
        User user = new User().setUsername(newCredential.getUsername()).setPassword(newCredential.getPassword());
        userRepository.save(user).subscribe();
    }

}