package com.reactive.webflexdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testDataMono(){
        Mono<String> monoString=Mono.just("testMono").log();
        monoString.subscribe(System.out::println); // ends with onComplete
    }

    @Test
    public void testErrorInMono(){
        Mono<?> monoString=Mono.just("testMono")
                .then(Mono.error(new RuntimeException("Error in mono!")))
                .log();
        monoString.subscribe(System.out::println,(e)-> System.out.println(e.getMessage())); // ends with OnError
    }

    @Test
    public void testDataFlux(){
        Flux<String> fluxString=Flux.just("1","2","3","4")
                .concatWithValues("5")
                .log();
        fluxString.subscribe(System.out::println); // ends with onComplete
    }

    @Test
    public void testErrorInFlux(){
        Flux<String> fluxString=Flux.just("1","2","3","4")
                .concatWithValues("5")
                .concatWith(Flux.error(new RuntimeException("Error in flux!")))
                .concatWithValues("6") // won't work, the flow stops after an error
                .log();
        fluxString.subscribe(System.out::println); // ends with onError
    }
}
