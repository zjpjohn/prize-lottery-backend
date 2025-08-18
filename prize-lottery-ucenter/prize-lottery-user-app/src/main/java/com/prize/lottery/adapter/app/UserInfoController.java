package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IUserCommandService;
import com.prize.lottery.application.command.IWithdrawCommandService;
import com.prize.lottery.application.command.dto.UserResetPwdCmd;
import com.prize.lottery.application.command.dto.WithdrawCreateCmd;
import com.prize.lottery.application.command.vo.ExchangeResult;
import com.prize.lottery.application.command.vo.UserBalanceVo;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.vo.AppWithdrawVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.po.UserBalanceLogPo;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import com.prize.lottery.infrast.persist.po.UserMemberLogPo;
import com.prize.lottery.infrast.persist.po.UserMemberPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/user")
@Permission(domain = LotteryAuth.USER)
public class UserInfoController {

    private final IUserCommandService     userCommandService;
    private final IUserInfoQueryService   userQueryService;
    private final IWithdrawCommandService withdrawCommandService;
    private final IWithdrawQueryService   withdrawQueryService;

    @GetMapping("/")
    public UserInfoPo userInfo(@NotNull(message = "用户标识为空") Long userId) {
        return userQueryService.getUserInfo(userId);
    }

    @PutMapping("/reset")
    public void resetPassword(@Validated UserResetPwdCmd command) {
        userCommandService.resetPassword(command);
    }

    @GetMapping("/balance")
    public UserBalanceVo userBalance(@NotNull(message = "用户标识为空") Long userId) {
        return userCommandService.getUserBalanceDetail(userId);
    }

    @GetMapping("/balance/logs")
    public Page<UserBalanceLogPo> balanceLogs(@Validated BalanceLogQuery query) {
        return userQueryService.getUserBalanceLogs(query.from());
    }

    @PostMapping("/coupon")
    public ExchangeResult couponExchange(@NotNull(message = "用户标识为空") Long userId) {
        return userCommandService.couponExchange(userId);
    }

    @GetMapping("/coupon/exchange/logs")
    public Page<UserBalanceLogPo> couponExchangeLogs(@Validated CouponExchangeQuery query) {
        return userQueryService.getUserBalanceLogs(query.from());
    }

    @GetMapping("/consume/logs")
    public Page<UserBalanceLogPo> consumeRecords(@Validated ConsumeRecordQuery query) {
        return userQueryService.getUserBalanceLogs(query.from());
    }

    @PostMapping("/withdraw")
    public void userWithdraw(@Validated WithdrawCreateCmd command) {
        withdrawCommandService.payWithdraw(command, TransferScene.USER_REWARD_TRANS);
    }

    @GetMapping("/withdraw/list")
    public Page<AppWithdrawVo> withdrawList(@Validated AppWithdrawQuery query) {
        return withdrawQueryService.getAppUserWithdrawList(query);
    }

    @GetMapping("/member")
    public UserMemberPo userMember(@NotNull(message = "用户标识为空") Long userId) {
        return userQueryService.getUserMember(userId).orElseThrow(ResponseHandler.USER_MEMBER_NONE);
    }

    @GetMapping("/member/log")
    public Page<UserMemberLogPo> memberLogs(@Validated MemberLogQuery query) {
        return userQueryService.getMemberLogList(query);
    }

}
