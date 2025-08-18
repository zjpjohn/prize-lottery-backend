package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.command.IAdminCommandService;
import com.prize.lottery.application.command.dto.AdminLoginCmd;
import com.prize.lottery.application.vo.AdminAuthVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
public class AdminAuthController {

    private final IAdminCommandService adminCommandService;

    @PostMapping("/auth")
    public AdminAuthVo adminAuth(@Validated AdminLoginCmd command) {
        return adminCommandService.adminAuth(command);
    }

    @PostMapping("/loginOut")
    public void loginOut(@NotNull(message = "管理员标识为空") Long managerId) {
        adminCommandService.loginOut(managerId);
    }

}
