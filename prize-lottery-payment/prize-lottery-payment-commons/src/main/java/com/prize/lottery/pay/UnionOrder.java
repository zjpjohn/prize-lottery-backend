package com.prize.lottery.pay;

import lombok.Data;


@Data
public class UnionOrder {

    //支付渠道:ali_pay,wx_pay
    private PayChannel channel;

    /*支付宝预下单返回订单信息*/
    private String  order;
    private String  platform;
    private Integer environment;

    /*微信预下单返回信息*/
    private String appId;
    private String partnerId;
    private String prepayId;
    private String packValue;
    private String nonceStr;
    private String timestamp;
    private String sign;

}
