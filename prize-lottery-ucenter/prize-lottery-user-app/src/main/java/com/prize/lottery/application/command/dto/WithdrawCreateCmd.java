package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.PayChannel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class WithdrawCreateCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long       userId;
    /**
     * 提现支付渠道
     */
    @NotNull(message = "支付渠道为空")
    private PayChannel channel;
    /**
     * 提现金额
     */
    @NotNull(message = "提现金额为空")
    @Positive(message = "提现金额大于零")
    private Long       amount;

}
