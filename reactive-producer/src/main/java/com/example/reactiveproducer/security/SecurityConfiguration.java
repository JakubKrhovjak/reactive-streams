package com.example.reactiveproducer.security;

import com.example.reactiveproducer.security.jwt.JwtAuthenticationWebFilter;
import com.example.reactiveproducer.security.jwt.JwtAuthenticator;
import com.example.reactiveproducer.security.jwt.JwtAuthorizationFilter;
import com.example.reactiveproducer.service.DbUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebFilter;


/**
 * @author Jakub Krhovjak
 */

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    public static final String JWT_AUTH_TOKEN = "jwt-auth-token";

    @Bean
    public DbUserDetailService userDetailService() {
      return new DbUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager dbAuthenticationManager() {
        return new DbAuthenticationManager(passwordEncoder(), userDetailService());
    }

    @Bean
    public WebFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationWebFilter(dbAuthenticationManager(), authService());
    }

    @Bean
    public WebFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public JwtAuthenticator authService() {
        return new JwtAuthenticator();
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
            .authenticationManager(dbAuthenticationManager())
            .authorizeExchange()
            .and()
            .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAfter(jwtAuthorizationFilter(), SecurityWebFiltersOrder.FIRST)

            .authorizeExchange()
            .anyExchange().hasAuthority("USER")
            .and()
            .build();
    }
}
