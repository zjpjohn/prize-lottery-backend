package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.RuleState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class TransRuleModifyCmd {

    @NotNull(message = "唯一标识为空")
    private Long      id;
    @Positive(message = "审核门槛金额必须为正数")
    private Long      throttle;
    @Enumerable(ranges = {"0", "1"}, message = "强制审核标识错误")
    private Integer   force;
    @Length(max = 100, message = "规则描述最大长度为100")
    private String    remark;
    //规则状态
    private RuleState state;

}
