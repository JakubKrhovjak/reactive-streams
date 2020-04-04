package com.example.reactiveproducer.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 4/2/20.
 */
public class JwtAuthorizationFilter implements WebFilter {

    @Autowired
    JwtAuthenticator JwtAuthenticator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return getAuthMatcher().matches(exchange)
//            .filter(m -> !m.isMatch())
//            .flatMap(matchResult -> JwtAuthenticator.getAuthentication(exchange))
//
//            .flatMap(e -> chain.filter(exchange));
        return Mono.empty();
    }


    private ServerWebExchangeMatcher getAuthMatcher() {
        return ServerWebExchangeMatchers.matchers(new PathPatternParserServerWebExchangeMatcher("/auth", HttpMethod.GET));
    }




}
