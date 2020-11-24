package com.github.edgar615.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @version 2020/8/4
 * @since cpe-north-api
 */

@SpringBootApplication
@RestController
public class GatewayApplication {

    @Bean
    DevopsRoutePredicateFactory devopsRoutePredicateFactory() {
        return new DevopsRoutePredicateFactory();
    }

    @Bean
    public RemoteAddressResolver remoteAddressResolver() {
        return XForwardedRemoteAddressResolver
                .maxTrustedIndex(1);
    }

//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder, DevopsRoutePredicateFactory devopsRoutePredicateFactory) {
//        return builder.routes()
//                .route(p -> p.path("/hello")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://localhost:9001")
//                        .asyncPredicate(devopsRoutePredicateFactory.applyAsync(config -> {
//                            config.setName("user");
//                            config.setDevopsUser(Collections.singletonList("Edgar"));
//                        }))
//                )
//                .build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }
}
