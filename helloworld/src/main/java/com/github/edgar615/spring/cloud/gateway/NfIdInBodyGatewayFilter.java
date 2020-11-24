//package com.github.edgar615.spring.cloud.gateway;
//
//import org.springframework.cloud.client.DefaultServiceInstance;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;
//
///**
// * @author 张雨舟
// * @Date 2020/8/7
// */
//public class NfIdInBodyGatewayFilter implements GatewayFilter, Ordered {
//    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
//        String body = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
//        // preserve the original url
//        addOriginalRequestUrl(exchange, url);
//        DefaultServiceInstance instance = new DefaultServiceInstance("controller-1", "controller", "localhost", 9001, false);
//
////        if (instance == null) {
////            throw NotFoundException.create(properties.isUse404(),
////                    "Unable to find instance for " + url.getHost());
////        }
//
//        URI uri = exchange.getRequest().getURI();
//
//        // if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
//        // if the loadbalancer doesn't provide one.
//        String overrideScheme = instance.isSecure() ? "https" : "http";
//        if (schemePrefix != null) {
//            overrideScheme = url.getScheme();
//        }
//
////        URI requestUrl = loadBalancer.reconstructURI(
////                new LoadBalancerClientFilter.DelegatingServiceInstance(instance, overrideScheme), uri);
//
////        log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
//        try {
//            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI("http://localhost:9001"));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(body);
//        System.out.println(url);
//        System.out.println(schemePrefix);
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 10010;
//    }
//}
