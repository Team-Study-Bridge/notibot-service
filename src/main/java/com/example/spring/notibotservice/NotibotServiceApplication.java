package com.example.spring.notibotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotibotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotibotServiceApplication.class, args);
    }
}
