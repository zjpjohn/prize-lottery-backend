package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAdminCommandService;
import com.prize.lottery.application.command.dto.AdminCreateCmd;
import com.prize.lottery.application.command.dto.AdminPasswordCmd;
import com.prize.lottery.application.command.dto.ResetPasswordCmd;
import com.prize.lottery.application.query.IAdminQueryService;
import com.prize.lottery.application.query.dto.AdminPwdQuery;
import com.prize.lottery.application.query.dto.LoginLogQuery;
import com.prize.lottery.application.query.dto.MeLoginLogQuery;
import com.prize.lottery.application.vo.AdminDetailVo;
import com.prize.lottery.infrast.persist.po.AdminLoginLogPo;
import com.prize.lottery.infrast.persist.po.AdministratorPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/admin")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class AdministratorController {

    private final IAdminQueryService   adminQueryService;
    private final IAdminCommandService adminCommandService;

    @PostMapping("/")
    public void addAdminAcct(@Validated AdminCreateCmd command) {
        adminCommandService.addAdministrator(command);
    }

    @PutMapping("/reset")
    public void resetMinePassword(@Validated ResetPasswordCmd command) {
        adminCommandService.resetMinePassword(command);
    }

    @PutMapping(value = "/reset", params = "id")
    public void resetPassword(@Validated AdminPasswordCmd command) {
        adminCommandService.resetAdminPassword(command);
    }

    @PutMapping("/frozen/{id}")
    public void frozenAdminAcct(@PathVariable Long id) {
        adminCommandService.frozenAdministrator(id);
    }

    @PutMapping("/unfrozen/{id}")
    public void unfrozenAdminAcct(@PathVariable Long id) {
        adminCommandService.unfrozenAdministrator(id);
    }

    @DeleteMapping("/{id}")
    public void invalidateAcct(@PathVariable Long id) {
        adminCommandService.invalidateAdministrator(id);
    }

    @GetMapping("/{id}")
    public AdminDetailVo administrator(@NotNull(message = "查询呢用户标识为空") @PathVariable Long id,
                                       @NotNull(message = "登录用户标识为空") Long managerId) {
        return adminQueryService.getAdministratorDetail(id, managerId);
    }

    @GetMapping("/mine")
    public AdminDetailVo mineDetail(@NotNull(message = "登录用户标识为空") Long managerId) {
        return adminQueryService.getAdminDetail(managerId);
    }

    /**
     * 查询自己账户的登录日志
     */
    @GetMapping("/login/logs")
    public Page<AdminLoginLogPo> mineLoginLogs(@Validated MeLoginLogQuery query) {
        return adminQueryService.getLoginLogList(query.from());
    }

    @GetMapping("/list")
    public List<AdministratorPo> adminList(@NotNull(message = "用户标识为空") Long managerId) {
        return adminQueryService.getAdministratorList(managerId);
    }

    /**
     * 超级管理员查询指定账户的登录日志
     */
    @GetMapping(value = "/login/logs", params = "adminId")
    public Page<AdminLoginLogPo> loginLogs(@Validated LoginLogQuery query) {
        return adminQueryService.getAdminLoginLogs(query);
    }

    @GetMapping("/password")
    public String passwordQuery(@Validated AdminPwdQuery query) {
        return adminQueryService.passwordQuery(query);
    }

}
