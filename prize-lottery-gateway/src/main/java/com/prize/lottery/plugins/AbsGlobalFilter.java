package com.prize.lottery.plugins;

import com.alibaba.fastjson2.JSON;
import com.cloud.arch.web.domain.BodyData;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public abstract class AbsGlobalFilter implements GlobalFilter {

    /**
     * 返回错误响应信息
     *
     * @param response 响应信息
     * @param status   响应状态
     * @param error    错误信息
     * @param code     错误状态码
     */
    public Mono<Void> errorResponse(ServerHttpResponse response, HttpStatus status, String error, Integer code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        BodyData<Serializable> data   = new BodyData<>(error, code);
        DataBuffer             buffer = response.bufferFactory().wrap(JSON.toJSONBytes(data));
        return response.writeWith(Flux.just(buffer));
    }

}
