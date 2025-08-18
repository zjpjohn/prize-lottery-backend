package com.prize.lottery.plugins.access;

import com.cloud.arch.web.utils.RequestUtils;
import com.prize.lottery.plugins.AbsGlobalFilter;
import com.prize.lottery.plugins.FilterOrder;
import com.prize.lottery.plugins.access.service.IRequestAccessService;
import com.prize.lottery.utils.GatewayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.cloud.arch.web.WebTokenConstants.FORBIDDEN_MESSAGE;

@Slf4j
@Component
public class GatewayAccessFilter extends AbsGlobalFilter implements Ordered {

    private final IRequestAccessService requestAccessService;

    public GatewayAccessFilter(IRequestAccessService requestAccessService) {
        this.requestAccessService = requestAccessService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request   = exchange.getRequest();
        String            ipAddress = RequestUtils.ipAddress(request);
        exchange.getAttributes().put(GatewayConstants.REQUEST_IP_ATTR_KEY, ipAddress);
        if (requestAccessService.blackLimit(ipAddress)) {
            ServerHttpResponse response = exchange.getResponse();
            return errorResponse(response, HttpStatus.FORBIDDEN, FORBIDDEN_MESSAGE, HttpStatus.FORBIDDEN.value());
        }
        return chain.filter(exchange)
                    .doFinally(s -> exchange.getAttributes().remove(GatewayConstants.REQUEST_IP_ATTR_KEY));
    }

    @Override
    public int getOrder() {
        return FilterOrder.GATEWAY_ACCESS.getOrder();
    }
}
