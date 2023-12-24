package com.reactive.webflexdemo.handler;

import com.reactive.webflexdemo.dao.CustomerDao;
import com.reactive.webflexdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> getAllCustomer(ServerRequest request){
        Flux<Customer> customer=dao.getCustomersByStream();
        return ServerResponse.ok()
                .body(customer,Customer.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request){
        Integer id=Integer.valueOf(request.pathVariable("input"));
//      dao.getCustomersByStream().filter(c->c.getId()==id).take(1).single();
        Mono<Customer> customerMono= dao.getCustomersByStreamWithoutDelay().filter(c->c.getId()==id).next();
        return ServerResponse.ok()
                .body(customerMono,Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono= request.bodyToMono(Customer.class);
        Mono<String> saveResponse=customerMono.map(dto->dto.getId()+":"+dto.getName());
        return ServerResponse.ok()
                .body(saveResponse,Customer.class);
    }
}
