package com.prize.lottery.pay;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResult {

    //支付系统流水号
    private String        tradeNo;
    //订单编号
    private String        orderNo;
    //订单状态
    private TradeState    state;
    //订单金额，单位-分
    private Long          amount;
    //支付时间
    private LocalDateTime payTime;
    //支付标识
    private String        payerId;

}
