package com.example.reactiveproducer;

import org.springframework.stereotype.Controller;

/**
 * Created by Jakub krhovják on 6/8/19.
 */
@Controller
public class RSocketProducer {


//    @MessageMapping("flux-rsocket")
//    public Flux<String> getRsocketMessage() {
//        return Flux.fromStream(() -> Stream.generate(() -> "flux-rsocket message at: " + Instant.now()))
//                .delayElements(Duration.ofSeconds(1));
//    }

}
