package com.example.batchprocessing.config;

import com.example.batchprocessing.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> { // ItemProcessor<Input,Output>

    @Override
    public Customer process(Customer customer) throws Exception {
        return customer;
    }
}
