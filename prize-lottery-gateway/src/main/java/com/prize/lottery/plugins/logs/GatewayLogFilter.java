package com.prize.lottery.plugins.logs;

import com.prize.lottery.plugins.AbsGlobalFilter;
import com.prize.lottery.plugins.FilterOrder;
import com.prize.lottery.plugins.logs.domain.GatewayLog;
import com.prize.lottery.utils.GatewayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Slf4j
@Component
public class GatewayLogFilter extends AbsGlobalFilter implements Ordered {

    private static final String ACCESS_LOG_KEY   = "access_log";
    private static final String REQUEST_LOGGABLE = "loggable";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        Boolean loggable = Optional.ofNullable(route)
                                   .map(Route::getMetadata)
                                   .map(v -> (Boolean) v.get(REQUEST_LOGGABLE))
                                   .orElse(false);
        if (loggable) {
            return preRequest(route, exchange).then(Mono.defer(() -> chain.filter(exchange)))
                                              .doFinally(s -> responseLog(exchange));
        }
        return chain.filter(exchange);

    }


    /**
     * 打印请求日志
     *
     * @param exchange {@link ServerWebExchange}
     */
    private Mono<Void> preRequest(Route route, ServerWebExchange exchange) {
        ServerHttpRequest request     = exchange.getRequest();
        URI               requestURI  = request.getURI();
        MediaType         mediaType   = request.getHeaders().getContentType();
        Date              requestTime = new Date();
        String            method      = request.getMethod().name();
        GatewayLog gatewayLog = GatewayLog.builder()
                                          .ip(exchange.getAttribute(GatewayConstants.REQUEST_IP_ATTR_KEY))
                                          .mediaType(mediaType)
                                          .method(method)
                                          .schema(requestURI.getScheme())
                                          .headers(exchange.getRequest().getHeaders())
                                          .path(requestURI.getPath())
                                          .server(route.getId())
                                          .reqTime(requestTime)
                                          .build();
        return Mono.defer(() -> {
            exchange.getAttributes().put(ACCESS_LOG_KEY, gatewayLog);
            return Mono.empty();
        });
    }

    /**
     * 打印响应日志
     *
     * @param exchange {@link ServerWebExchange}
     */
    private void responseLog(ServerWebExchange exchange) {
        GatewayLog gatewayLog = exchange.getAttribute(ACCESS_LOG_KEY);
        if (gatewayLog != null) {
            Date resTime = new Date();
            gatewayLog.setResTime(resTime);
            gatewayLog.setExecTime(resTime.getTime() - gatewayLog.getReqTime().getTime());
            gatewayLog.setStatus(exchange.getResponse().getStatusCode().value());
            log.info(gatewayLog.toString());
            //clear request log attribute
            exchange.getAttributes().remove(ACCESS_LOG_KEY);
        }
    }

    @Override
    public int getOrder() {
        return FilterOrder.GATEWAY_LOG.getOrder();
    }
}
