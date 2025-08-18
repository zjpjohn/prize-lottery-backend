package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserResetCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long   userId;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误")
    private String code;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String phone;
    /**
     * 账户密码
     * 包含大小写字母数字
     */
    @NotBlank(message = "密码为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码格式错误")
    private String pwd;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码格式错误")
    private String confirm;

}
