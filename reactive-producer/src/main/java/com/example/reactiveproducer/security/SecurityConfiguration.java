package com.example.reactiveproducer.security;

import com.example.reactiveproducer.security.jwt.AuthUtils;
import com.example.reactiveproducer.security.jwt.JwtAuthenticationManager;
import com.example.reactiveproducer.security.jwt.SecurityContextRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
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

//    @Bean
//    UrlBasedCorsConfigurationSource corsConfig() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOrigin("*");
//        corsConfig.setMaxAge(Long.valueOf("3600"));
//        corsConfig.setAllowedHeaders(List.of("Set-Cookie", "XSRF-TOKEN" ));
//        corsConfig.addAllowedMethod("*");
////        corsConfig.setA
//
//        UrlBasedCorsConfigurationSource source =
//            new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return source;
//    }

    @Bean
    public SecurityWebFilterChain userSecurityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf()
            .requireCsrfProtectionMatcher(new NegatedServerWebExchangeMatcher(new PathPatternParserServerWebExchangeMatcher("/sign-in")))
            .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .cors().disable()
//            .configurationSource(corsConfig())
//            .and()
            .securityContextRepository(securityContextRepository())
            .authorizeExchange()
            .and()
            .authorizeExchange()
            .pathMatchers(  "/sign-in", "/new-account", "/login").permitAll()
            .anyExchange().hasAuthority("USER")
            .and()
            .build();
    }

//
//    @Bean
//    WebFilter addCsrfToken() {
//        return new CrTokenFilter();
////        return (exchange, next) -> exchange
////            .<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
////            .doOnSuccess(token -> {}) // do nothing, just subscribe :/
////            .then(next.filter(exchange));
//    }

    public static class CrTokenFilter implements WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            return exchange
                .<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> {
                    ServerHttpResponse response = exchange.getResponse();

                    log.info("Token:", token);
                })
                .then(chain.filter((exchange)));

        }
    }
}
