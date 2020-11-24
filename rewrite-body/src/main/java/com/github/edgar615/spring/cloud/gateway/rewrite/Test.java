package com.github.edgar615.spring.cloud.gateway.rewrite;

import java.util.Base64;

public class Test {
    public static void main(String[] args) {
        System.out.println(Base64.getEncoder().encodeToString("{\"foo\":\"bar\"}".getBytes()));
    }
}
