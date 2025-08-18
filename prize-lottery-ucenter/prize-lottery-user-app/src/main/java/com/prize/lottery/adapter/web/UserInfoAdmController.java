package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IUserCommandService;
import com.prize.lottery.application.query.IAppLaunchQueryService;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.dto.ActiveUserQuery;
import com.prize.lottery.application.query.dto.AppLaunchQuery;
import com.prize.lottery.application.query.dto.UserInfoQuery;
import com.prize.lottery.application.query.vo.UserInvMasterVo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;
import com.prize.lottery.infrast.persist.po.UserBalancePo;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import com.prize.lottery.infrast.persist.vo.ActiveUserVo;
import com.prize.lottery.infrast.persist.vo.UserAdmInfoVo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/user")
@Permission(domain = LotteryAuth.MANAGER)
public class UserInfoAdmController {

    private final IUserInfoQueryService  userQueryService;
    private final IAppLaunchQueryService appLaunchService;
    private final IUserCommandService    userCommandService;

    @GetMapping("/")
    public UserAdmInfoVo userInfo(@NotNull(message = "用户标识为空") Long userId) {
        return userQueryService.getAdmUserInfo(userId);
    }

    @GetMapping("/launch/list")
    public Page<AppLauncherPo> launchList(@Validated AppLaunchQuery query) {
        return appLaunchService.getUserAppLaunchList(query);
    }

    @GetMapping("/balance")
    public UserBalancePo userBalance(@NotNull(message = "用户标识为空") Long userId) {
        return userQueryService.getUserBalanceInfo(userId);
    }

    @PutMapping("/balance/charge")
    public void manualCharge(@NotNull(message = "用户标识为空") Long userId,
                             @NotNull(message = "充值金额为空") @Positive(message = "充值金额必须为正数")
                             Integer value) {
        userCommandService.manualCharge(userId, value);
    }

    @PutMapping("/balance/withdraw")
    public void switchCanWithdraw(@NotNull(message = "用户标识为空") Long userId) {
        userCommandService.switchUserWithdraw(userId);
    }

    @GetMapping("/invite/master")
    public UserInvMasterVo userInvMaster(@NotNull(message = "用户表示为空") Long userId) {
        return userQueryService.getInviteMaster(userId);
    }

    @PutMapping("/balance/profit")
    public void switchCanProfit(@NotNull(message = "用户标识为空") Long userId) {
        userCommandService.switchUserProfit(userId);
    }

    @GetMapping("/list")
    public Page<UserInfoPo> userList(@Validated UserInfoQuery query) {
        return userQueryService.getUserInfoList(query);
    }

    @GetMapping("/active/list")
    public Page<ActiveUserVo> activeUsers(@Validated ActiveUserQuery query) {
        return userQueryService.getActiveUsers(query);
    }
}
