package com.example.reactiveproducer.security;

import java.util.function.Function;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class JWTAuthConverter implements Function<ServerWebExchange, Mono<Authentication>> {
  @Override
  public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
    return Mono.empty();
//    return Mono.justOrEmpty(serverWebExchange)
//      .map(JWTUtil::getAuthorizationPayload)
//      .filter(Objects::nonNull)
//      .filter(JWTUtil.matchBearerLength())
//      .map(JWTUtil.getBearerValue())
//      .filter(token -> !token.isEmpty())
//      .map(JWTUtil::verifySignedJWT)
//      .map(JWTUtil::getUsernamePasswordAuthenticationToken)
//      .filter(Objects::nonNull);
  }
}
