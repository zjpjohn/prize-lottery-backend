package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserSmsAuthCmd extends BaseUserAuthCmd {

    /**
     * 验证码
     */
    @NotBlank(message = "验证码为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误")
    private String code;

}
