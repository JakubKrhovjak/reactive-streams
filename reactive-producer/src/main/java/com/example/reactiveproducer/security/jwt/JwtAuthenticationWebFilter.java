package com.example.reactiveproducer.security.jwt;

import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.example.reactiveproducer.security.SecurityConfiguration.JWT_AUTH_TOKEN;


/**
 * Created by Jakub krhovják on 4/1/20.
 */


@CrossOrigin("*")
public class JwtAuthenticationWebFilter implements WebFilter {

    private AuthUtils authService;
    private ReactiveAuthenticationManager authenticationManager;
    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager, AuthUtils authService) {
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
        HttpHeaders headers = exchange.getExchange()
            .getResponse()
            .getHeaders();



        headers.add(JWT_AUTH_TOKEN, authService.generateToken(authentication));


        headers.add("Access-Control-Allow-Origin", "*");
//        headers.add("Access-Control-Allow-Methods", "*");
//        headers.add("Access-Control-Allow-Headers", "*");
        headers.add("Access-Control-Expose-Headers", "*");
//        headers.add("Access-Control-Allow-Credentials", "true");

        return Mono.empty();

    }

    private ServerWebExchangeMatcher getAuthMatcher() {
        return ServerWebExchangeMatchers.matchers(new PathPatternParserServerWebExchangeMatcher("/auth", HttpMethod.HEAD));
    }
}
