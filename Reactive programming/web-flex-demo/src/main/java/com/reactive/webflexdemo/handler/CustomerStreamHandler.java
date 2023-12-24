package com.reactive.webflexdemo.handler;

import com.reactive.webflexdemo.dao.CustomerDao;
import com.reactive.webflexdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {
    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> getAllCustomers(ServerRequest request){
        Flux<Customer> customerFlux=dao.getCustomersByStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerFlux,Customer.class);
    }
}
