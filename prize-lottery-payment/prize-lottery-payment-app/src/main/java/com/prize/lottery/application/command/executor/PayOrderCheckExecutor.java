package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;
import com.prize.lottery.domain.order.repository.IOrderInfoRepository;
import com.prize.lottery.infrast.persist.enums.OrderState;
import com.prize.lottery.pay.OrderResult;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.pay.TradeState;
import com.prize.lottery.pay.UnionPayExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderCheckExecutor {

    private final IOrderInfoRepository                                    orderRepository;
    private final EnumerableExecutorFactory<PayChannel, UnionPayExecutor> executorFactory;

    @Transactional
    public void execute(OrderInfoDo orderInfo) {
        PayChannel       payChannel  = orderInfo.getChannel();
        UnionPayExecutor payExecutor = executorFactory.ofNullable(payChannel).orElse(null);
        if (payExecutor == null) {
            log.warn("暂无配置{}支付通道，请配置该支付通道.", payChannel.label());
            return;
        }
        Aggregate<Long, OrderInfoDo> aggregate = AggregateFactory.create(orderInfo);
        if (orderInfo.getState() != OrderState.WAIT_PAY) {
            return;
        }
        OrderResult result = payExecutor.query(orderInfo.getBizNo(), "");
        if (result == null) {
            // 直接关闭已在系统中创建但未在第三方系统未创建的订单
            log.warn("{}端订单[{}]未创建.", payChannel.getRemark(), orderInfo.getBizNo());
            orderInfo.payClose(LocalDateTime.now());
            orderRepository.save(aggregate);
            return;
        }
        TradeState tradeState = result.getState();
        if (tradeState == TradeState.SUCCESS) {
            orderInfo.paySuccess(result.getPayTime());
        } else if (tradeState == TradeState.WAIT_PAY || tradeState == TradeState.CLOSED) {
            orderInfo.payClose(LocalDateTime.now());
        }
        orderRepository.save(aggregate);
        if (tradeState == TradeState.WAIT_PAY) {
            //关闭支付宝端待支付订单
            log.info("关闭{}订单编号[{}]的待支付订单.", payChannel.getRemark(), orderInfo.getBizNo());
            payExecutor.close(orderInfo.getBizNo(), "");
        }
    }
}
