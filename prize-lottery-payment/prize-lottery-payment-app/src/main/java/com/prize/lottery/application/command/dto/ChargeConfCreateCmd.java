package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@Data
public class ChargeConfCreateCmd {

    @NotBlank(message = "充值名称为空")
    @Length(max = 30, message = "名称最长30个字")
    private String  name;
    @NotNull(message = "充值金币数量为空")
    @Positive(message = "充值金币大于0")
    private Long    amount;
    @NotNull(message = "充值奖励为空")
    @Range(min = 0, message = "奖励金额不小于0")
    private Long    gift;
    @Range(min = 0, message = "优先级不小于0")
    private Integer priority;

}
