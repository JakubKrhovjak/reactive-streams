package com.example.reactiveproducer.security;

import com.example.reactiveproducer.security.jwt.AuthUtils;
import com.example.reactiveproducer.security.jwt.JwtAuthenticationManager;
import com.example.reactiveproducer.security.jwt.SecurityContextRepository;
import com.example.reactiveproducer.service.DbUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;


/**
 * @author Jakub Krhovjak
 */

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    public static final String JWT_AUTH_TOKEN = "jwtAuthToken";

    @Bean
    public DbUserDetailService userDetailService() {
        return new DbUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    @Primary
    public ReactiveAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager();
    }

    @Bean
    public AuthUtils jwtUtils() {
        return new AuthUtils();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new SecurityContextRepository();
    }

    @Bean
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {

        return http.csrf()
            .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .securityContextRepository(securityContextRepository())
            .authorizeExchange()
            .and()
            .authorizeExchange()
            .pathMatchers("/auth").permitAll()
            .anyExchange().hasAuthority("USER")
            .and()
            .build();
    }

    @Bean
    WebFilter addCsrfToken() {
        return (exchange, next) -> exchange
            .<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
            .doOnSuccess(token -> {}) // do nothing, just subscribe :/
            .then(next.filter(exchange));
    }
}
