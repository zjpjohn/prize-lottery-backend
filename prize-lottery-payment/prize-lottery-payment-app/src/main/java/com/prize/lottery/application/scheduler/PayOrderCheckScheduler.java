package com.prize.lottery.application.scheduler;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.application.command.executor.PayOrderCheckExecutor;
import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;
import com.prize.lottery.domain.order.repository.IOrderInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PayOrderCheckScheduler {

    private final Executor              asyncPayExecutor;
    private final IOrderInfoRepository  orderRepository;
    private final PayOrderCheckExecutor orderCheckExecutor;

    public PayOrderCheckScheduler(IOrderInfoRepository orderRepository,
                                  PayOrderCheckExecutor orderCheckExecutor,
                                  @Qualifier("asyncPayExecutor") Executor asyncPayExecutor) {
        this.orderRepository    = orderRepository;
        this.orderCheckExecutor = orderCheckExecutor;
        this.asyncPayExecutor   = asyncPayExecutor;
    }

    /**
     * 每分钟检查待支付已过期订单并关闭订单
     */
    @Scheduled(initialDelay = 60, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void payCheck() {
        List<OrderInfoDo> orderList = orderRepository.expireOrders();
        if (CollectionUtils.isNotEmpty(orderList)) {
            orderList.forEach(this::asyncCheckExecute);
        }
    }

    /**
     * 异步检查过期待支付订单
     */
    private void asyncCheckExecute(OrderInfoDo order) {
        asyncPayExecutor.execute(() -> {
            try {
                orderCheckExecutor.execute(order);
            } catch (Exception error) {
                log.error(error.getMessage(), error);
            }
        });
    }
}
