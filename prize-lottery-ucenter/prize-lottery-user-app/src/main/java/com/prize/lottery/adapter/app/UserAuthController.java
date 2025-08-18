package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.command.IUserCommandService;
import com.prize.lottery.application.command.dto.UserPwdAuthCmd;
import com.prize.lottery.application.command.dto.UserQuickAuthCmd;
import com.prize.lottery.application.command.dto.UserSmsAuthCmd;
import com.prize.lottery.application.command.vo.UserLoginResult;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/user")
public class UserAuthController {

    private final IUserCommandService userCommandService;

    @PostMapping(value = "/auth", params = "type=1")
    public UserLoginResult smsAuth(@Validated UserSmsAuthCmd login) {
        return userCommandService.smsAuth(login);
    }

    @PostMapping(value = "/auth", params = "type=2")
    public UserLoginResult authQuick(@Validated UserQuickAuthCmd command) {
        return userCommandService.quickAuth(command);
    }

    @PostMapping(value = "/auth", params = "type=3")
    public UserLoginResult pwdAuth(UserPwdAuthCmd command) {
        return userCommandService.pwdAuth(command);
    }

    @PostMapping("/loginOut")
    public void loginOut(@NotNull(message = "账户标识为空") Long userId) {
        userCommandService.loginOut(userId);
    }

}
