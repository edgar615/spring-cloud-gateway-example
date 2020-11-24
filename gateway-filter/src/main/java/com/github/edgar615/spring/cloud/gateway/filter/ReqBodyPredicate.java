package com.github.edgar615.spring.cloud.gateway.filter;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class ReqBodyPredicate implements Predicate {
    @Override
    public boolean test(Object o) {
        return true;
    }
}
