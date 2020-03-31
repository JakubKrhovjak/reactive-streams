package com.example.reactiveproducer.security;

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
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;


/**
 * @author Jakub Krhovjak
 */

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

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
    @Order(1)
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter authenticationJWT = new AuthenticationWebFilter(dbAuthenticationManager());
        authenticationJWT.setAuthenticationSuccessHandler(new JWTAuthSuccessHandler());

        return http.csrf().disable()
//            .authenticationManager(dbAuthenticationManager())
//            .
//            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange()
            .pathMatchers("/","/login", "/auth/**")
            .permitAll()
            .and()
            .addFilterAt(authenticationJWT, SecurityWebFiltersOrder.FIRST)
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
             .addFilterAt(new JWTAuthWebFilter(), SecurityWebFiltersOrder.HTTP_BASIC)

            .build();
    }

//    @Bean
//    public MapReactiveUserDetailsService basicAuthService() {
//        UserDetails user = User.withUsername("user").password("{noop}password").roles("REST").build();
//        return new MapReactiveUserDetailsService(user);
//    }
//
//    @Bean
//    @Order(2)
//    public SecurityWebFilterChain basicAuthSecurityWebFilterChain(ServerHttpSecurity http) {
//        return http.csrf().disable()
//            .authenticationManager(new UserDetailsRepositoryReactiveAuthenticationManager(basicAuthService()))
//            .authorizeExchange()
//            .pathMatchers("/basic/**").authenticated()
//            .anyExchange().permitAll()
//            .and().httpBasic()
//            .and()
//            .build();
//    }


}
