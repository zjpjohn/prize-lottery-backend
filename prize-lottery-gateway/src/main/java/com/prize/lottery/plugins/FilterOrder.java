package com.prize.lottery.plugins;


import com.cloud.arch.web.WebTokenConstants;
import lombok.Getter;

@Getter
public enum FilterOrder {
    GATEWAY_ACCESS(WebTokenConstants.AUTH_FILTER_ORDER - 1),
    GATEWAY_IDEMPOTENT(WebTokenConstants.AUTH_FILTER_ORDER + 1),
    GATEWAY_LOG(WebTokenConstants.AUTH_FILTER_ORDER + 2);

    private final int order;

    FilterOrder(int order) {
        this.order = order;
    }

}
