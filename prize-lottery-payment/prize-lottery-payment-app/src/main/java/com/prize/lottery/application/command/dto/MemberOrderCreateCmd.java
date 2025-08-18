package com.prize.lottery.application.command.dto;

import com.prize.lottery.pay.PayChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class MemberOrderCreateCmd {

    @NotNull(message = "用户标识为空")
    private Long       userId;
    @NotBlank(message = "套餐标识为空")
    private String     packNo;
    @NotNull(message = "支付渠道为空")
    private PayChannel channel;

}
