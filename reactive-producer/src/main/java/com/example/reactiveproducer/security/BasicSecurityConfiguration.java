package com.example.reactiveproducer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;


/**
 * @author Jakub Krhovjak
 */

@EnableWebFluxSecurity
public class BasicSecurityConfiguration {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}password").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
            .authorizeExchange()
            .pathMatchers("/basic").hasRole("USER")
            .anyExchange().permitAll()
            .and().httpBasic()
            .and()
            .build();
    }

}
