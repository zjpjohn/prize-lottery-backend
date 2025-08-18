package com.prize.lottery.application.command.dto;


import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.dto.SmsChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class SmsSendCmd {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String phone;
    /**
     * 短信渠道
     */
    @NotBlank(message = "发送短信渠道为空")
    @Enumerable(enums = SmsChannel.class, message = "短信渠道错误.")
    private String channel;

}
