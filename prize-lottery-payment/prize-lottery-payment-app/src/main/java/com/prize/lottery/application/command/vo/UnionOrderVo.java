package com.prize.lottery.application.command.vo;

import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.pay.UnionOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnionOrderVo {

    private String      orderNo;
    private PayChannel  channel;
    private AliPayOrder aliOrder;
    private WxPayOrder  wxOrder;

    @Data
    public static class AliPayOrder {
        private String  order;
        private String  platform;
        private Integer environment;
    }

    @Data
    public static class WxPayOrder {
        private String appId;
        private String partnerId;
        private String prepayId;
        private String packValue;
        private String nonceStr;
        private String sign;
        private String timestamp;
    }

    public UnionOrderVo from(String orderNo, UnionOrder order) {
        this.channel = order.getChannel();
        this.orderNo = orderNo;
        if (this.channel == PayChannel.ALI_PAY) {
            this.aliOrder = new AliPayOrder();
            this.aliOrder.setOrder(order.getOrder());
            this.aliOrder.setPlatform(order.getPlatform());
            this.aliOrder.setEnvironment(order.getEnvironment());
        } else if (this.channel == PayChannel.WX_PAY) {
            this.wxOrder = new WxPayOrder();
            this.wxOrder.setAppId(order.getAppId());
            this.wxOrder.setNonceStr(order.getNonceStr());
            this.wxOrder.setPackValue(order.getPackValue());
            this.wxOrder.setPartnerId(order.getPartnerId());
            this.wxOrder.setPrepayId(order.getPrepayId());
            this.wxOrder.setSign(order.getSign());
            this.wxOrder.setTimestamp(order.getTimestamp());
        }
        return this;
    }
}
