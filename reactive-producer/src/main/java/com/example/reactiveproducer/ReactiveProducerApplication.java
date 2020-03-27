package com.example.reactiveproducer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;


@SpringBootApplication
public class ReactiveProducerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveProducerApplication.class)
            .web(WebApplicationType.REACTIVE)
            .run();
    }

    @Bean
    public LoggingEventListener mongoEventListener() {
        return new LoggingEventListener();
    };

  }
