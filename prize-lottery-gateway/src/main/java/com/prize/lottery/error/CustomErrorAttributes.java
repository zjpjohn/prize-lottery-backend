package com.prize.lottery.error;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServiceUnavailableException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.net.ConnectException;
import java.util.Map;

@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final String NOT_FOUND_MESSAGE           = "请求不存在，请确认请求.";
    private static final String INTERNAL_SERVER_MESSAGE     = "系统内部错误，请联系管理员.";
    private static final String SERVICE_UNAVAILABLE_MESSAGE = "服务不可用，请稍后再试.";
    private static final String GATEWAY_TIMEOUT_MESSAGE     = "网关请求超时，请稍后再试.";
    private static final String ERROR_STATUS_FIELD          = "status";
    private static final String ERROR_CODE_FIELD            = "code";
    private static final String ERROR_MESSAGE_FIELD         = "error";
    private static final String ERROR_TIME_FIELD            = "timestamp";

    private final Map<HttpStatusCode, String> messageAttr = Maps.newHashMap();

    public CustomErrorAttributes() {
        messageAttr.put(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        messageAttr.put(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_MESSAGE);
        messageAttr.put(HttpStatus.SERVICE_UNAVAILABLE, SERVICE_UNAVAILABLE_MESSAGE);
        messageAttr.put(HttpStatus.GATEWAY_TIMEOUT, GATEWAY_TIMEOUT_MESSAGE);
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Pair<HttpStatusCode, String> attrs      = errorStatus(this.getError(request));
        Map<String, Object>          attributes = Maps.newLinkedHashMap();

        attributes.put(ERROR_STATUS_FIELD, attrs.getKey().value());
        attributes.put(ERROR_CODE_FIELD, attrs.getKey().value());
        attributes.put(ERROR_MESSAGE_FIELD, attrs.getValue());
        attributes.put(ERROR_TIME_FIELD, System.currentTimeMillis());
        return attributes;

    }

    public Pair<HttpStatusCode, String> errorStatus(Throwable error) {
        if (error instanceof NotFoundException) {
            return Pair.of(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
        if (error instanceof ServiceUnavailableException || error instanceof ConnectException) {
            return Pair.of(HttpStatus.SERVICE_UNAVAILABLE, SERVICE_UNAVAILABLE_MESSAGE);
        }
        if (error instanceof TimeoutException) {
            return Pair.of(HttpStatus.GATEWAY_TIMEOUT, GATEWAY_TIMEOUT_MESSAGE);
        }
        if (error instanceof ResponseStatusException exception) {
            String message = messageAttr.getOrDefault(exception.getStatusCode(), exception.getMessage());
            return Pair.of(exception.getStatusCode(), message);
        }
        return Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_MESSAGE);
    }

}
