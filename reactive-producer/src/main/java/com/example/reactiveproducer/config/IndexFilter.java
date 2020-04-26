package com.example.reactiveproducer.config;

import org.springframework.stereotype.Component;


/**
 * Created by Jakub krhovják on 4/26/20.
 */

@Component
public class IndexFilter { //implements WebFilter {
//
//    @Value("classpath:/index.html")
//    private Resource indexHtml;

//    @SneakyThrows
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        if (exchange.getRequest().getURI().getPath().equals("/")) {
//            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path(indexHtml.).build()).build());
//        }
//
//        return chain.filter(exchange);
//    }
}
