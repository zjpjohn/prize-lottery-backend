package com.prize.lottery.domain.transfer.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.command.dto.PayChannelCreateCmd;
import com.prize.lottery.application.command.dto.PayChannelModifyCmd;
import com.prize.lottery.pay.PayChannel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class PayChannelDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -994126836684074212L;

    private Long       id;
    private String     seq;
    private String     name;
    private PayChannel channel;
    private String     cover;
    private String     icon;
    private String     remark;
    private Integer    priority;
    private Integer    pay;
    private Integer    withdraw;

    public PayChannelDo(PayChannelCreateCmd command, BiConsumer<PayChannelCreateCmd, PayChannelDo> converter) {
        this.seq      = IdWorker.uuid();
        this.priority = 0;
        converter.accept(command, this);
        this.pay      = Optional.ofNullable(this.pay).orElse(0);
        this.withdraw = Optional.ofNullable(this.withdraw).orElse(0);
    }

    public void modify(PayChannelModifyCmd command, BiConsumer<PayChannelModifyCmd, PayChannelDo> converter) {
        converter.accept(command, this);
    }

    /**
     * 支付通道是否支持支付
     */
    public boolean canPay() {
        return this.pay == 1;
    }

    /**
     * 支付通道是否支持提现
     */
    public boolean canWithdraw() {
        return this.withdraw == 1;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
