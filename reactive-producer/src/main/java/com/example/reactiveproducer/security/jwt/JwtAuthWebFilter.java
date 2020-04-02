package com.example.reactiveproducer.security.jwt;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * Created by Jakub krhovják on 4/1/20.
 */


public class JwtAuthWebFilter implements WebFilter {

    private JwtAuthenticator authService;
    private  ReactiveAuthenticationManager authenticationManager;
    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());

    public JwtAuthWebFilter(ReactiveAuthenticationManager authenticationManager, JwtAuthenticator authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
        return this.getAuthMatcher().matches(exchange)
            .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
            .flatMap(matchResult -> authService.getAuthentication(exchange))
            .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
            .flatMap(authentication -> authenticationManager.authenticate(authentication))
            .flatMap(authentication -> onAuthenticationSuccess(authentication, webFilterExchange))
            .onErrorResume(AuthenticationException.class, e -> authenticationFailureHandler.onAuthenticationFailure(webFilterExchange, e));
   }

    private Mono<Void> onAuthenticationSuccess(Authentication authentication,  WebFilterExchange  exchange) {
      exchange.getExchange()
            .getResponse()
            .getHeaders()
            .add("JWT_AUTH_TOKEN", authService.generateToken("test"));
      return Mono.empty();

    }

    private ServerWebExchangeMatcher getAuthMatcher() {
        return ServerWebExchangeMatchers.matchers(new PathPatternParserServerWebExchangeMatcher("/auth", HttpMethod.GET));
    }
}
