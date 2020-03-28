package com.example.reactiveproducer.security;

import com.example.reactiveproducer.service.DbUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * Created by Jakub krhovják on 3/28/20.
 */
@RequiredArgsConstructor
public class DbAuthenticationManager implements ReactiveAuthenticationManager {

    private final PasswordEncoder passwordEncoder;
    private final DbUserDetailService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String username = authentication.getName();
        return this.userDetailsService.findByUsername(username)
            .publishOn(Schedulers.parallel())
            .filter( u -> passwordEncoder.matches((String) authentication.getCredentials(), u.getPassword()))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("Invalid Credentials"))))
            .map( u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()) );
    }
}
