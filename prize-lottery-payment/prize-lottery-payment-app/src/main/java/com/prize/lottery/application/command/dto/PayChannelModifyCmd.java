package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PayChannelModifyCmd {

    @NotNull(message = "唯一标识为空")
    private Long    id;
    private String  name;
    private String  cover;
    private String  icon;
    @Length(max = 100, message = "备注说明最大长度100")
    private String  remark;
    @Enumerable(ranges = {"0", "1"}, message = "优先级可选值错误")
    private Integer priority;
    @Enumerable(ranges = {"0", "1"}, message = "支付标识错误")
    private Integer pay;
    @Enumerable(ranges = {"0", "1"}, message = "提现标识错误")
    private Integer withdraw;
}
