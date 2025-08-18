package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class ExpertWithdrawCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long    userId;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码为空")
    private String  code;
    /**
     * 提现金额
     */
    @Range(min = 1, message = "提现金额错误")
    private Integer withdraw;

}
