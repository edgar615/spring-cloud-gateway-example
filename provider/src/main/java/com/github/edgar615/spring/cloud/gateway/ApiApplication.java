package com.github.edgar615.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class);
    }
}
