package com.prize.lottery.application.command.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPwdAuthCmd extends BaseUserAuthCmd {

    @NotBlank(message = "登录密码为空")
    private String password;

}
