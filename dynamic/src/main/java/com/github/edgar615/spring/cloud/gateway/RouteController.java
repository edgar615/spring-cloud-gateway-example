package com.github.edgar615.spring.cloud.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @PostMapping
    public String add(@RequestBody RouteDefinition routeDefinition) {
        dynamicRouteService.addRoute(routeDefinition);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        dynamicRouteService.deleteRoute(id);
        return "success";
    }
}
