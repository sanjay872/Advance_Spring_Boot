package com.reactive.webflexdemo.router;

import com.reactive.webflexdemo.handler.CustomerHandler;
import com.reactive.webflexdemo.handler.CustomerStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler customerHandler;

    @Autowired
    private CustomerStreamHandler customerStreamHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/customers", customerHandler::getAllCustomer)
                .GET("router/stream/customers",customerStreamHandler::getAllCustomers)
                .GET("/router/customer/{input}",customerHandler::getCustomerById)
                .POST("/router/customer/save",customerHandler::saveCustomer)
                .build();
    }

}
