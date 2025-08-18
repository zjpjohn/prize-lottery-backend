package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class WithdrawCheckCmd {

    /**
     * 提现请求标识
     */
    @NotBlank(message = "提现请求标识为空")
    private String  seq;
    /**
     * 审核操作
     */
    @NotNull(message = "审核操作为空")
    @Enumerable(ranges = {"1", "2"}, message = "审核操作错误.")
    private Integer action;
    /**
     * 审核备注说明
     */
    private String  message;

}
