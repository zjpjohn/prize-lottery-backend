package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResetPasswordCmd {

    /**
     * 登录账户标识
     */
    @NotNull(message = "登录账户标识为空")
    private Long   managerId;
    /**
     * 设置新密码
     */
    @NotBlank(message = "新密码为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码格式错误")
    private String password;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码为空")
    private String confirm;

    public void validate() {
        Assert.state(this.password.equals(this.confirm), ResponseHandler.PASSWORD_NOT_CONSTANT);
    }

}
