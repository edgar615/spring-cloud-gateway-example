package com.github.edgar615.spring.cloud.gateway;

import com.google.common.collect.Lists;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * DevopsRoutePredicateFactory
 *
 * @author 张雨舟
 * @Date 2020/8/24
 */
public class DevopsRoutePredicateFactory extends AbstractRoutePredicateFactory<DevopsRoutePredicateFactory.Config> {
    public DevopsRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            List<HttpCookie> cookies = serverWebExchange.getRequest()
                    .getCookies()
                    .get(config.getName());
            if (cookies == null || cookies.isEmpty()) {
                return false;
            } else {
                String userId = cookies.get(0).getValue();
                return config.getDevopsUser().contains(userId);
            }
        };
    }

    @Validated
    public static class Config {

        @NotEmpty
        private String name;

        @NotEmpty
        private List<String> devopsUser;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getDevopsUser() {
            return devopsUser;
        }

        public void setDevopsUser(List<String> devopsUser) {
            this.devopsUser = devopsUser;
        }
    }
}