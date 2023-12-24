package com.reactive.webflexdemo.service;

import com.reactive.webflexdemo.dao.CustomerDao;
import com.reactive.webflexdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getAllCustomer(){
        long start=System.currentTimeMillis();
        List<Customer> customers= customerDao.getCustomers();
        long end=System.currentTimeMillis();
        System.out.println("Start at: "+start+" and End at: "+end+" and Total time: "+(end-start));
        return customers;
    }

    public Flux<Customer> getAllCustomerByStream() {
        long start=System.currentTimeMillis();
        Flux<Customer> customers= customerDao.getCustomersByStream();
        long end=System.currentTimeMillis();
        System.out.println("Start at: "+start+" and End at: "+end+" and Total time: "+(end-start));
        return customers;
    }
}
