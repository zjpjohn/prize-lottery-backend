package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.pay.PayChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PayChannelCreateCmd {

    @NotBlank(message = "支付渠道名称为空")
    private String     name;
    @NotNull(message = "支付渠道值为空")
    private PayChannel channel;
    @NotBlank(message = "支付渠道图标链接为空")
    private String     cover;
    @NotBlank(message = "支付渠道图标值为空")
    private String     icon;
    @Length(max = 100, message = "备注说明最大长度100")
    private String     remark;
    @Enumerable(ranges = {"0", "1"}, message = "支付标识错误")
    private Integer    pay;
    @Enumerable(ranges = {"0", "1"}, message = "提现标识错误")
    private Integer    withdraw;

}
