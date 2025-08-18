package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AdminLoginCmd {

    /**
     * 账户名称
     */
    @NotBlank(message = "账户名称为空")
    private String name;
    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码为空")
    private String password;

}
