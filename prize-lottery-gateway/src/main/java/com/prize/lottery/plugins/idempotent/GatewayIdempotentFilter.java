package com.prize.lottery.plugins.idempotent;

import com.prize.lottery.plugins.AbsGlobalFilter;
import com.prize.lottery.plugins.FilterOrder;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GatewayIdempotentFilter extends AbsGlobalFilter implements Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return FilterOrder.GATEWAY_IDEMPOTENT.getOrder();
    }

}
