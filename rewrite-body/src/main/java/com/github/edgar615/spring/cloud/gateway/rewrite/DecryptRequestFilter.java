package com.github.edgar615.spring.cloud.gateway.rewrite;

import org.bouncycastle.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

//@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class DecryptRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    ServerHttpRequest request = decryptRequest(exchange.getRequest(), dataBuffer);
                    return chain.filter(exchange.mutate().request(request).build());
                });

    }

    private ServerHttpRequest decryptRequest(ServerHttpRequest request, DataBuffer dataBuffer) {
        DataBufferUtils.retain(dataBuffer);
        Flux<DataBuffer> cachedFlux = Flux
                .defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
        String body = toRaw(cachedFlux);
        byte[] decryptedBodyBytes = Base64.getDecoder().decode(body.getBytes(StandardCharsets.UTF_8));
        return new ServerHttpRequestDecorator(request) {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(request.getHeaders());
                if (decryptedBodyBytes.length > 0) {
                    httpHeaders.setContentLength(decryptedBodyBytes.length);
                }
//                httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return Flux.just(body).
                        map(s -> new DefaultDataBufferFactory().wrap(decryptedBodyBytes));
            }
        };
    }

    private static String toRaw(Flux<DataBuffer> body) {
        AtomicReference<String> rawRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            rawRef.set(Strings.fromUTF8ByteArray(bytes));
        });
        return rawRef.get();
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
