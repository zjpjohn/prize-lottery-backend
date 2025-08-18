package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@Data
public class ChargeConfEditCmd {

    @NotNull(message = "配置标识为空")
    private Long        id;
    @Length(max = 30, message = "名称最长30个字")
    private String      name;
    @Positive(message = "充值金币大于0")
    private Long        amount;
    @Range(min = 0, message = "奖励金额不小于0")
    private Long        gift;
    @Range(min = 0, message = "优先级不小于0")
    private Integer     priority;
    private CommonState state;
}
