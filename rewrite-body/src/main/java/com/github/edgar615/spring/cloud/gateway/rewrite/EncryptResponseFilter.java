package com.github.edgar615.spring.cloud.gateway.rewrite;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Objects;

@Component
//@Order(NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1)
public class EncryptResponseFilter implements GlobalFilter, Ordered {

    // 通过@Order注解不起作用
    @Override
    public int getOrder() {
        // 控制在NettyWriteResponseFilter后执行
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange.mutate()
                .response(encrypteResponse(exchange)).build());
    }

    private ServerHttpResponse encrypteResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        return new ServerHttpResponseDecorator(response) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                httpHeaders.set(HttpHeaders.CONTENT_ENCODING, "application/octet-stream");
                ClientResponse clientResponse = prepareClientResponse(body, httpHeaders);
                Mono<String> modifiedBody = clientResponse.bodyToMono(String.class)
                        .flatMap( originalBody ->
                                Mono.just(Objects.requireNonNull(Base64.getEncoder().encodeToString(originalBody.getBytes()))))
                        .switchIfEmpty(Mono.empty());
                BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);

                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, response.getHeaders());
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            Mono<DataBuffer> messageBody = updateBody(getDelegate(), outputMessage);
                            HttpHeaders headers = getDelegate().getHeaders();
                            headers.setContentType(MediaType.TEXT_PLAIN);
                            if (headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
                                messageBody = messageBody.doOnNext(data -> {
                                    headers.setContentLength(data.readableByteCount());
                                });
                            }

                            return getDelegate().writeWith(messageBody);
                        }));

            }

            private Mono<DataBuffer> updateBody(ServerHttpResponse httpResponse,
                                                CachedBodyOutputMessage message) {

                Mono<DataBuffer> response = DataBufferUtils.join(message.getBody());
                // 参考ModifyResponseBodyGatewayFilterFactory
//                List<String> encodingHeaders = httpResponse.getHeaders()
//                        .getOrEmpty(HttpHeaders.CONTENT_ENCODING);
//                for (String encoding : encodingHeaders) {
//                    MessageBodyEncoder encoder = messageBodyEncoders.get(encoding);
//                    if (encoder != null) {
//                        DataBufferFactory dataBufferFactory = httpResponse.bufferFactory();
//                        response = response.publishOn(Schedulers.parallel())
//                                .map(encoder::encode).map(dataBufferFactory::wrap);
//                        break;
//                    }
//                }

                return response;

            }

            private Mono<String> extractBody(ServerHttpResponse response, ClientResponse clientResponse) {

//                List<String> encodingHeaders = response.getHeaders()
//                        .getOrEmpty(HttpHeaders.CONTENT_ENCODING);
//                for (String encoding : encodingHeaders) {
//                    MessageBodyDecoder decoder = messageBodyDecoders.get(encoding);
//                    if (decoder != null) {
//                        return clientResponse.bodyToMono(byte[].class)
//                                .publishOn(Schedulers.parallel()).map(decoder::decode)
//                                .map(bytes -> response.bufferFactory()
//                                        .wrap(bytes))
//                                .map(buffer -> prepareClientResponse(Mono.just(buffer),
//                                        response.getHeaders()))
//                                .flatMap(resp -> resp.bodyToMono(String.class));
//                    }
//                }


                return clientResponse.bodyToMono(String.class);

            }

            private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body, HttpHeaders httpHeaders) {
                ClientResponse.Builder builder = ClientResponse.create(response.getStatusCode(),
                        HandlerStrategies.withDefaults().messageReaders());
                return builder.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();
            }
        };
    }

}
