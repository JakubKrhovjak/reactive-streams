package com.example.reactiveproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveProducerApplication.class, args);
    }
//
//    @Bean
//    public RSocket rSocket() {
//        return RSocketFactory
//                .connect()
//                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//                .frameDecoder(PayloadDecoder.DEFAULT)
//                .transport(TcpClientTransport.create(7000))
//                .start()
//                .block();
//    }
//
//
//    @Bean
//    RSocketRequester requester(RSocketStrategies rSocketStrategies) {
//        return RSocketRequester
//                .wrap(this.rSocket(), new MimeType(MimeTypeUtils.APPLICATION_JSON_VALUE), rSocketStrategies);
//    }

}
