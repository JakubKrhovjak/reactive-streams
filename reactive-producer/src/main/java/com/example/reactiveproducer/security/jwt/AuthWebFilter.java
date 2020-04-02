package com.example.reactiveproducer.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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


public class AuthWebFilter implements WebFilter {

    private JwtAuthenticator authService;
    private  ReactiveAuthenticationManager authenticationManager;
    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());

    public AuthWebFilter(ReactiveAuthenticationManager authenticationManager, JwtAuthenticator authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return this.getAuthMatcher().matches(exchange)
//            .filter(matchResult -> matchResult.isMatch())
//            .flatMap(matchResult -> this.jwtAuthConverter.apply(exchange))
//            .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
//            .flatMap(token -> authenticate(exchange, chain, token

        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> authorization = headers.get("Authorization");
        String base64Credentials = authorization.get(0).substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] split = credentials.split(":");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(split[0], split[1]);

        WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken)
            .flatMap(authentication -> onAuthenticationSuccess(authentication, webFilterExchange))
            .onErrorResume(AuthenticationException.class, e -> authenticationFailureHandler.onAuthenticationFailure(webFilterExchange, e));


    }

    private Mono<Void> onAuthenticationSuccess(Authentication authentication,  WebFilterExchange  exchange) {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.getHeaders().add("JWT_AUTH_TOKEN", authService.generateToken("test"));
        return Mono.empty();
    }

    private ServerWebExchangeMatcher getAuthMatcher() {
        return ServerWebExchangeMatchers.matchers(new PathPatternParserServerWebExchangeMatcher("/auth", HttpMethod.GET));
    }
}
