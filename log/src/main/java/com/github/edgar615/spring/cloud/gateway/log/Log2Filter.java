package com.github.edgar615.spring.cloud.gateway.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 15)
public class Log2Filter implements GlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log2Filter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethod().name();
        String url = request.getPath().value();
        String requestId = request.getHeaders().getFirst("X-Request-Id");
        Flux<DataBuffer> body = request.getBody();
       return DataBufferUtils.join(body)
                .flatMap(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);
//            DataBufferUtils.release(buffer);
                    String bodyString = new String(bytes, StandardCharsets.UTF_8);
                    LOGGER.info("{} {} {} {}", requestId,  method, url, bodyString);
                    return chain.filter(exchange);
                });

    }

}
