package com.prize.lottery.domain.order.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.prize.lottery.domain.order.event.OrderChargeEvent;
import com.prize.lottery.domain.order.event.OrderMemberEvent;
import com.prize.lottery.infrast.persist.enums.OrderState;
import com.prize.lottery.infrast.persist.enums.OrderType;
import com.prize.lottery.pay.NotifyResult;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.pay.TradeState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class OrderInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 8039514519677749904L;

    private Long                id;
    //订单编号
    private String              bizNo;
    //用户标识
    private Long                userId;
    //订单类型
    private OrderType           type;
    //标准单价
    private Long                stdPrice;
    //实际单价
    private Long                realPrice;
    //购买数量
    private Integer             quantity;
    //实际支付金额
    private Long                amount;
    //支付渠道
    private PayChannel          channel;
    //订单状态
    private OrderState          state;
    //订单备注
    private String              remark;
    //订单附件信息
    private String              attach;
    //订单内容
    private Map<String, Object> content;
    //订单是否已对账
    private Integer             settled;
    //订单支付过期截止时间
    private LocalDateTime       expireTime;
    //支付时间
    private LocalDateTime       payTime;
    //订单关闭时间
    private LocalDateTime       closeTime;

    /**
     * 判断订单过期时间是否已过期
     */
    public boolean hasExpired() {
        return this.expireTime.isAfter(LocalDateTime.now());
    }

    public void notifyHandle(NotifyResult result) {
        if (result.getState() == TradeState.CLOSED) {
            this.payClose(result.getCloseTime());
            return;
        }
        if (result.getState() == TradeState.SUCCESS) {
            this.paySuccess(result.getPayTime());
        }
    }

    public void payClose(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        this.state     = OrderState.CLOSED;
    }

    public void paySuccess(LocalDateTime payTime) {
        this.payTime = payTime;
        this.state   = OrderState.SUCCESS;
        switch (this.type) {
            case MEMBER:
                this.memberHandle();
                break;
            case CHARGE:
                this.chargeHandle();
                break;
        }
    }

    private void memberHandle() {
        String           packNo   = (String) this.content.get("packNo");
        String           name     = (String) this.content.get("name");
        Integer          timeUnit = (Integer) this.content.get("timeUnit");
        OrderMemberEvent event    = new OrderMemberEvent(this.bizNo, this.userId, packNo, name, timeUnit, realPrice, channel.label());
        DomainEventPublisher.publish(event);
    }

    private void chargeHandle() {
        Long             amount = (Long) this.content.get("amount");
        Long             gift   = (Long) this.content.get("gift");
        OrderChargeEvent event  = new OrderChargeEvent(this.bizNo, this.userId, amount, gift, realPrice, channel.label());
        DomainEventPublisher.publish(event);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
