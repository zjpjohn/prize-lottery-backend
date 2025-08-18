package com.prize.lottery.application.command.dto;

import com.prize.lottery.pay.PayChannel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ChargeOrderCreateCmd {

    @NotNull(message = "用户标识为空")
    private Long       userId;
    @NotNull(message = "充值配置标识为空")
    private Long       confId;
    @NotNull(message = "支付渠道为空")
    private PayChannel channel;

}
