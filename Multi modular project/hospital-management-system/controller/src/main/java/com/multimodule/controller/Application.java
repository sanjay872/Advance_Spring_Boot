package com.multimodule.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.multimodule.*"})
@EntityScan(basePackages = {"com.multimodule.*"})
@EnableJpaRepositories(basePackages = {"com.multimodule.*"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
