package com.github.edgar615.spring.cloud.gateway.filter;

import org.assertj.core.util.Lists;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Order(-10000)
public class IpBlacklistGatewayFilterFactory
        extends AbstractGatewayFilterFactory<IpBlacklistGatewayFilterFactory.Config> {

    public IpBlacklistGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Lists.list("blacklist");
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.GATHER_LIST;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            if (config.getBlacklist().contains(ip)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private Set<String> blacklist = new HashSet<>();

        public Set<String> getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(Set<String> blacklist) {
            this.blacklist = blacklist;
        }
    }
}
