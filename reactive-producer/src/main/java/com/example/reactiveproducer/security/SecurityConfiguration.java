package com.example.reactiveproducer.security;

import com.example.reactiveproducer.security.jwt.AuthUtils;
import com.example.reactiveproducer.security.jwt.JwtAuthenticationManager;
import com.example.reactiveproducer.security.jwt.SecurityContextRepository;
import com.example.reactiveproducer.service.DbUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
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
    @Order(1)
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader(JWT_AUTH_TOKEN);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return http.csrf().disable()
            .cors().configurationSource(urlBasedCorsConfigurationSource)
//            .cors().disable()
    .and()
            .exceptionHandling()
            .authenticationEntryPoint((swe, e) -> {
                return Mono.fromRunnable(() -> {
                    swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                });
            }).accessDeniedHandler((swe, e) -> {
                return Mono.fromRunnable(() -> {
                    swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                });
            })
            .and()
            .authorizeExchange()
            .and()
            .securityContextRepository(securityContextRepository())
            .authorizeExchange()
            .pathMatchers("/auth").permitAll()
            .anyExchange().hasAuthority("USER")
            .and()
            .build();
    }
}
