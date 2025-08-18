package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class ExpertResetPwdCmd {

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
     * 账户密码
     */
    @NotBlank(message = "密码为空")
    private String pwd;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码为空")
    private String confirm;

    public ExpertResetPwdCmd validate() {
        Assert.state(this.pwd.equals(this.confirm), ResponseHandler.PASSWORD_NONE_CONSTANT);
        return this;
    }
}
