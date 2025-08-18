package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberOpenCmd {

    @NotNull(message = "用户表示为空")
    private Long   userId;
    @NotBlank(message = "套餐标识为空")
    private String packNo;
    //支付方式备注
    private String channel = "";

}
