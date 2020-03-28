package com.example.reactiveproducer.security;

import com.example.reactiveproducer.service.DbUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;


/**
 * @author Jakub Krhovjak
 */

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public MapReactiveUserDetailsService basicAuthService() {
        UserDetails user = User.withUsername("user").password("{noop}password").roles("REST").build();
        return new MapReactiveUserDetailsService(user);
    }

      @Bean
      @Order(1)
      public SecurityWebFilterChain basicAuthSecurityWebFilterChain(ServerHttpSecurity http) {
          AuthenticationWebFilter authenticationJWT = new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(basicAuthService()));
        return http.csrf().disable()
            .authenticationManager(new UserDetailsRepositoryReactiveAuthenticationManager(basicAuthService()))
            .authorizeExchange()
            .pathMatchers("/basic").authenticated()
            .anyExchange().permitAll()
            .and().httpBasic()
            .and()
            .build();
    }

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
    @Order(2)
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter authenticationJWT = new AuthenticationWebFilter(dbAuthenticationManager());
        return http.csrf().disable()
            .authenticationManager(dbAuthenticationManager())
            .authorizeExchange()
            .pathMatchers("/login").authenticated()
            .anyExchange().permitAll()
            .and()
            .build();
    }


}
