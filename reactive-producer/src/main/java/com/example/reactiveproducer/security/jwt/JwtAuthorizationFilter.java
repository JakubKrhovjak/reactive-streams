package com.example.reactiveproducer.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
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

    private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository.getInstance();

    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());

    @Autowired
    JwtAuthenticator JwtAuthenticator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
          return getAuthMatcher().matches(exchange)
            .filter(m -> !m.isMatch())
            .map(matchResult -> JwtAuthenticator.getClaims(exchange))
              .flatMap(claims -> claims)
//            .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
            .switchIfEmpty(authenticationFailureHandler.onAuthenticationFailure(webFilterExchange, new InsufficientAuthenticationException("test")).then(Mono.empty()))
              .then(Mono.empty());
//            .flatMap(claims -> claims)
//            .flatMap(claims -> JwtAuthenticator.getAuthorization(claims))
//
//             .flatMap(authorization -> {
//                 SecurityContextImpl securityContext = new SecurityContextImpl();
//                 securityContext.setAuthentication(authorization);
//                 return this.securityContextRepository.save(exchange, securityContext)
//                             .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
//
//             })

//            .onErrorResume(AuthenticationException.class, e -> {
//                return authenticationFailureHandler.onAuthenticationFailure(webFilterExchange, e);
//            });

    }


    private ServerWebExchangeMatcher getAuthMatcher() {
        return ServerWebExchangeMatchers.matchers(new PathPatternParserServerWebExchangeMatcher("/auth", HttpMethod.GET));
    }




}
