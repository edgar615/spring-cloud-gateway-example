package com.github.edgar615.spring.cloud.gateway;

import org.springframework.cloud.gateway.route.RouteDefinition;

public interface DynamicRouteService {

    void addRoute(RouteDefinition routeDefinition);

    void deleteRoute(String id);
}
