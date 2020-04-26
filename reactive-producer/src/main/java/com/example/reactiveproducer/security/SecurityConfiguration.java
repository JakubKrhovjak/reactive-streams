package com.example.reactiveproducer.security;

import com.example.reactiveproducer.security.jwt.AuthUtils;
import com.example.reactiveproducer.security.jwt.JwtAuthenticationManager;
import com.example.reactiveproducer.security.jwt.SecurityContextRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;


/**
 * @author Jakub Krhovjak
 */

@Slf4j
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

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
    UrlBasedCorsConfigurationSource corsConfig() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000");
        corsConfig.setMaxAge(Long.valueOf("3600"));
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }


    @Bean
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
//            .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
//            .and()
            .cors()
            .configurationSource(corsConfig())
            .and()
            .securityContextRepository(securityContextRepository())
            .exceptionHandling()
            .authenticationEntryPoint((exchange, e) -> {
                return Mono.fromRunnable(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                });
            }).accessDeniedHandler((exchange, e) -> {
                return Mono.fromRunnable(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                });
            })
            .and()
            .authorizeExchange()
            .pathMatchers(    "/", "/static/**", "/sign-in", "/new-account", "/login").permitAll()
            .anyExchange().hasAuthority("USER")
            .and()
            .build();
    }




//    @Bean
//    WebFilter addCsrfToken() {
//        return (exchange, next) -> exchange
//            .<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
//            .doOnSuccess(token -> {}) // do nothing, just subscribe :/
//            .then(next.filter(exchange));
//    }


}
