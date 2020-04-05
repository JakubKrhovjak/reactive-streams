package com.example.reactiveproducer.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 4/4/20.
 */
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private AuthUtils jwtAuthenticator;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
      return jwtAuthenticator.getClaims(authentication)
             .flatMap(claims -> jwtAuthenticator.getAuthorization(claims));
    }
}
