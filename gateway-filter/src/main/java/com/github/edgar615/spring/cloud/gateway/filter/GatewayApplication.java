package com.github.edgar615.spring.cloud.gateway.filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @version 2020/8/4
 * @since cpe-north-api
 */

@SpringBootApplication
@RestController
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }

//    @Bean
//    public GlobalFilter customFilter() {
//        return new CustomGlobalFilter();
//    }
//
//    public class CustomGlobalFilter implements GlobalFilter, Ordered {
//
//        @Override
//        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////            log.info("custom global filter");
//            return chain.filter(exchange);
//        }
//
//        @Override
//        public int getOrder() {
//            return -1;
//        }
//    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rewrite_request_obj", r -> r.method("GET")
                        .filters(f -> f.modifyResponseBody(String.class, String.class, MediaType.APPLICATION_JSON_VALUE,
                                (serverWebExchange, s) -> {
                                    return Mono.just(s.toUpperCase());
                                })).uri("http://localhost:9001"))
                .build();
    }
//
//    static class Hello {
//        String message;
//
//        public Hello() {
//        }
//
//        public Hello(String message) {
//            this.message = message;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//    }
//    @GetMapping("/fallback")
//    public Mono<Map<String, String>> fallback() {
//        return Mono.just(Collections.singletonMap("path", "gateway-fallback"));
//    }

}
