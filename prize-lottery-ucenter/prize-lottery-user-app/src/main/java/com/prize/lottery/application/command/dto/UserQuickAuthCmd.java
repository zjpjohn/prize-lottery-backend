package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuickAuthCmd extends BaseUserAuthCmd {

    /**
     * 随机字符串
     */
    @NotBlank(message = "随机字符串为空")
    private String nonceStr;
    /**
     * 签名字符串
     */
    @NotBlank(message = "签名为空")
    private String signature;

}
