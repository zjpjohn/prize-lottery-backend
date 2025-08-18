package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserRegisterCmd {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String  phone;
    /**
     * 手机验证码:6位数字验证码
     */
    @NotBlank(message = "验证码为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误")
    private String  code;
    /**
     * 邀请渠道
     * 1-彩票站
     * 2-用户邀请
     * 3-直接下载
     */
    @Enumerable(enums = RegisterChannel.class, message = "注册渠道错误.")
    private Integer channel = 3;
    /**
     * 注册邀请码
     */
    private String  invite;

}
