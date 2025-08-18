package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserResetPwdCmd {

    @NotNull(message = "用户标识为空")
    private Long   userId;
    @NotBlank(message = "验证码为空")
    private String code;
    @NotBlank(message = "新密码为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码格式错误")
    private String password;
    @NotBlank(message = "确认密码为空")
    private String confirm;

    public UserResetPwdCmd passwordValidate() {
        Assert.state(this.password.equals(this.confirm), ResponseHandler.PASSWORD_NONE_CONSTANT);
        return this;
    }

}
