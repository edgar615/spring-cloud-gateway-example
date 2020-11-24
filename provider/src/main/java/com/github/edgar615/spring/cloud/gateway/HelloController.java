package com.github.edgar615.spring.cloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张雨舟
 * @Date 2020/8/6
 */
@RestController
public class HelloController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Value("${server.port}")
    private int port;

    @GetMapping("/str")
    public String string() throws InterruptedException {
        return "Hello Gateway";
    }

    @PostMapping("/body")
    public Map<String, Object> body(@RequestBody Map<String, Object> body, HttpServletRequest request) throws InterruptedException {
        return body;
    }

    @GetMapping("/hello")
    public Map<String, Object> hello(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String header = enumeration.nextElement();
            headerMap.put(header, request.getHeader(header));
        }
        Map<String, Object> map= new HashMap<>();
        map.put("header", headerMap);
        map.put("query", request.getQueryString());
        return map;
    }

    @GetMapping("/foo/{value}")
    public Map<String, String> foo(@PathVariable("value") String value) {
        return Collections.singletonMap("value", value);
    }

    @GetMapping("/hystrix")
    public Map<String, String> hystrix() throws InterruptedException {
        System.out.println("hystrix");
        throw new RuntimeException("error");
//        TimeUnit.SECONDS.sleep(5);
//        return Collections.singletonMap("path", "hystrix");
    }

    @GetMapping("/resp-header")
    public Map<String, String> dedupeResp(HttpServletResponse response) {
        response.setHeader("X-Response-Foo", "bar");
        response.setHeader("password", "it is password");
        return Collections.singletonMap("path", "reps-header");
    }

    @GetMapping("/fallback")
    public Map<String, Object> fallback(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String header = enumeration.nextElement();
            headerMap.put(header, request.getHeader(header));
        }
        return Collections.singletonMap("fallbackHeader", headerMap);
    }

}
