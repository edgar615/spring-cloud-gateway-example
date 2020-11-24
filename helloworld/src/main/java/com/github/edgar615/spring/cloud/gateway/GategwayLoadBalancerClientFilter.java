//package com.github.edgar615.spring.cloud.gateway;
//
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.gateway.config.LoadBalancerProperties;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;
//
///**
// * @author 张雨舟
// * @Date 2020/8/6
// */
//@Service
//public class GategwayLoadBalancerClientFilter implements GlobalFilter, Ordered {
//
//    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
//        String body = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
//        System.out.println(body);
//        System.out.println(url);
//        System.out.println(schemePrefix);
//        return chain.filter(exchange);
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 10100;
//    }
//}
