package com.github.edgar615.spring.cloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-10000)
public class BodyLogGatewayFilterFactory
        extends AbstractGatewayFilterFactory<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyLogGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {
            Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
            LOGGER.info("request body is:{}", requestBody);

            return chain.filter(exchange);
        };
    }

}
