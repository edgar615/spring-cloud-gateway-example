package com.github.edgar615.spring.cloud.gateway.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 2020/8/4
 * @since cpe-north-api
 */

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }

}
