package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.pay.PayChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class TransferAuditCmd {

    @NotBlank(message = "支付编号")
    private String     transNo;
    @NotNull(message = "支付渠道为空")
    private PayChannel channel;
    @NotNull(message = "审核状态为空")
    @Enumerable(ranges = {"2", "3"}, message = "审核状态错误")
    private Integer    audit;
    @Length(max = 50, message = "审核意见长度不超过50")
    private String     message;

}
