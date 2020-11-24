package com.github.edgar615.spring.cloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ElapsedGatewayFilter implements GlobalFilter, Ordered {
    public static final int ELAPSED_ORDER = -100000;

    private static final Logger LOGGER = LoggerFactory.getLogger(ElapsedGatewayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long endTime = System.currentTimeMillis();
            LOGGER.info("{}, elapsed {}ms", exchange.getRequest().getURI().getRawPath(), endTime - startTime);
        }));
    }

    @Override
    public int getOrder() {
        return ELAPSED_ORDER;
    }
}
