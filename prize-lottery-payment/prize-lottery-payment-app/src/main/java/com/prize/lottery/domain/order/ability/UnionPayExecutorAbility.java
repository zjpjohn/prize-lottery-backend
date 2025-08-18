package com.prize.lottery.domain.order.ability;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.pay.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UnionPayExecutorAbility {

    private final EnumerableExecutorFactory<PayChannel, UnionPayExecutor> unionPayExecutorFactory;

    public UnionPayExecutorAbility(EnumerableExecutorFactory<PayChannel, UnionPayExecutor> unionPayExecutorFactory) {
        this.unionPayExecutorFactory = unionPayExecutorFactory;
    }

    /**
     * 统一支付预下单
     *
     * @param request 预下单请求
     */
    public UnionOrder prepay(UnionPayRequest request) {
        UnionPayExecutor executor = this.unionPayExecutorFactory.of(request.getChannel());
        return executor.execute(request);
    }

    /**
     * 统一支付回调处理
     *
     * @param channel 支付渠道
     * @param request 回调请求
     */
    public NotifyResult notify(PayChannel channel, HttpServletRequest request) {
        return this.unionPayExecutorFactory.ofNullable(channel).map(executor -> executor.notify(request)).orElse(null);
    }

}
