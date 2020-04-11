package com.example.reactiveproducer;


import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


/**
 * Created by Jakub krhovják on 4/10/20.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReactiveProducerApplication.class)
public class Itest {

    @Autowired
    ApplicationContext context;


    @Ignore
    @Test
    void name() {

        WebTestClient build = WebTestClient.bindToApplicationContext(context).configureClient().build();
        build.get()
            .uri("/basic")
           .header(HttpHeaders.AUTHORIZATION, "test")
             .exchange();

    }

}
