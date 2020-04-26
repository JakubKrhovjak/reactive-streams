package com.example.reactiveproducer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


/**
 * Created by Jakub krhovják on 4/24/20.
 */

@Configuration
public class WebConfiguration implements WebFluxConfigurer {


    @Bean
    public RouterFunction<ServerResponse> indexRouter2(@Value("classpath:/index.html") final Resource indexHtml) {
        return route(GET("/*"), request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
    }


    @Bean
    public RouterFunction<ServerResponse> staticResourcesRouter() {
        return RouterFunctions
            .resources("/static/**", new ClassPathResource("static/"));
    }

}