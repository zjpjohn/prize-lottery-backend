package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IPayOrderCommandService;
import com.prize.lottery.application.command.dto.ChargeOrderCreateCmd;
import com.prize.lottery.application.command.dto.MemberOrderCreateCmd;
import com.prize.lottery.application.command.vo.UnionOrderVo;
import com.prize.lottery.application.command.vo.WxNotifyResult;
import com.prize.lottery.domain.order.ability.UnionPayExecutorAbility;
import com.prize.lottery.domain.order.factory.PayOrderFactory;
import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;
import com.prize.lottery.domain.order.repository.IOrderInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.OrderState;
import com.prize.lottery.pay.NotifyResult;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.pay.UnionOrder;
import com.prize.lottery.pay.UnionPayRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PayOrderCommandService implements IPayOrderCommandService {

    public static final String ALI_NOTIFY_SUCCESS = "success";
    public static final String ALI_NOTIFY_FAIL    = "fail";

    private final PayOrderFactory         payOrderFactory;
    private final IOrderInfoRepository    orderRepository;
    private final UnionPayExecutorAbility payExecutorAbility;

    @Override
    @Transactional
    public WxNotifyResult wxPayNotify(HttpServletRequest request) {
        NotifyResult notifyResult = payExecutorAbility.notify(PayChannel.WX_PAY, request);
        if (notifyResult == null) {
            return WxNotifyResult.badRequest("回调校验失败!");
        }
        Aggregate<Long, OrderInfoDo> aggregate = orderRepository.ofNo(notifyResult.getOrderNo());
        if (aggregate == null) {
            return WxNotifyResult.badRequest("订单不存在!");
        }
        if (aggregate.getRoot().getChannel() != PayChannel.WX_PAY) {
            return WxNotifyResult.badRequest("订单渠道错误!");
        }
        //支付回调订单业务处理
        this.orderHandle(notifyResult, aggregate);
        return WxNotifyResult.success();
    }

    @Override
    @Transactional
    public String aliPayNotify(HttpServletRequest request) {
        NotifyResult notifyResult = payExecutorAbility.notify(PayChannel.ALI_PAY, request);
        if (notifyResult == null) {
            return ALI_NOTIFY_FAIL;
        }
        Aggregate<Long, OrderInfoDo> aggregate = orderRepository.ofNo(notifyResult.getOrderNo());
        if (aggregate == null || aggregate.getRoot().getChannel() != PayChannel.ALI_PAY) {
            return ALI_NOTIFY_FAIL;
        }
        //订单回调业务处理
        this.orderHandle(notifyResult, aggregate);
        return ALI_NOTIFY_SUCCESS;
    }

    private void orderHandle(NotifyResult result, Aggregate<Long, OrderInfoDo> aggregate) {
        OrderInfoDo order = aggregate.getRoot();
        //todo 分布式锁防止重复处理
        if (order.getState() == OrderState.WAIT_PAY) {
            //待支付订单状态处理
            order.notifyHandle(result);
            orderRepository.save(aggregate);
        }
    }

    @Override
    @Transactional
    public UnionOrderVo memberPrepay(MemberOrderCreateCmd command) {
        //创建会员订单信息
        OrderInfoDo payOrder = payOrderFactory.memberOrder(command);
        AggregateFactory.create(payOrder).save(orderRepository::save);
        //统一预下单处理
        return this.unionPrePay(payOrder);
    }

    /**
     * 由订单编号直接发起已创建订单的支付
     */
    @Override
    public UnionOrderVo repayOrder(String orderNo) {
        Aggregate<Long, OrderInfoDo> aggregate = orderRepository.ofNo(orderNo);
        Assert.notNull(aggregate, ResponseHandler.ORDER_NOT_EXIST);

        //订单状态与超时过期判断
        OrderInfoDo order = aggregate.getRoot();
        Assert.state(order.getState() == OrderState.WAIT_PAY, ResponseHandler.ORDER_STATE_ERROR);
        Assert.state(!order.hasExpired(), ResponseHandler.ORDER_HAS_EXPIRED);
        //重新发起统一下单请求
        return this.unionPrePay(order);
    }

    @Override
    @Transactional
    public UnionOrderVo chargePrepay(ChargeOrderCreateCmd command) {
        //创建充值订单信息
        OrderInfoDo payOrder = payOrderFactory.chargeOrder(command);
        AggregateFactory.create(payOrder).save(orderRepository::save);
        //统一预下单处理
        return this.unionPrePay(payOrder);
    }

    /**
     * 统一预下单
     *
     * @param payOrder 支付订单信息
     */
    private UnionOrderVo unionPrePay(OrderInfoDo payOrder) {
        UnionPayRequest payRequest = new UnionPayRequest();
        payRequest.setOrderNo(payOrder.getBizNo());
        payRequest.setChannel(payOrder.getChannel());
        payRequest.setDescription(payOrder.getAttach());
        payRequest.setAmount(payOrder.getAmount());
        payRequest.setSubject(payOrder.getRemark());
        payRequest.setExpireTime(payOrder.getExpireTime());
        UnionOrder unionOrder = payExecutorAbility.prepay(payRequest);
        return new UnionOrderVo().from(payOrder.getBizNo(), unionOrder);
    }

}
