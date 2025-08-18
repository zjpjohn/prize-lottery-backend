package com.prize.lottery.application.command.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IUserCommandService;
import com.prize.lottery.application.command.dto.*;
import com.prize.lottery.application.command.executor.*;
import com.prize.lottery.application.command.vo.*;
import com.prize.lottery.application.query.IMobileQueryService;
import com.prize.lottery.domain.user.ability.UserMemberAbility;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.user.repository.IUserMemberRepository;
import com.prize.lottery.dto.SmsChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService implements IUserCommandService {

    private final UserAttendanceExecutor    userSignInExe;
    private final UserAuthLoginExecutor     userAuthLoginExe;
    private final CouponExchangeExecutor    couponExchangeExe;
    private final IMobileQueryService       mobileQueryService;
    private final UserBalanceDetailExecutor userBalanceDetailExe;
    private final UserAuthLoginOutExecutor  userAuthLoginOutExe;
    private final UserResetPasswordExecutor userResetPasswordExe;
    private final IUserBalanceRepository    balanceRepository;
    private final UserMemberAbility         userMemberAbility;
    private final IUserMemberRepository     userMemberRepository;

    @Override
    public UserLoginResult smsAuth(UserSmsAuthCmd command) {
        //短信验证码登录手机号校验
        boolean checkResult = mobileQueryService.checkSmsCode(command.getPhone(), command.getCode(), SmsChannel.LOGIN);
        Assert.state(checkResult, ResponseHandler.VERIFY_CODE_ERROR);

        //手机号登录注册
        return userAuthLoginExe.execute(command);
    }

    @Override
    public UserLoginResult quickAuth(UserQuickAuthCmd command) {
        //一键登录手机号有效性校验
        Boolean checked = mobileQueryService.checkAuthMobile(command.getPhone(), command.getNonceStr(), command.getSignature());
        Assert.state(checked, ResponseHandler.MOBILE_INVALID_ERROR);

        //手机号登录注册
        return userAuthLoginExe.execute(command);
    }

    @Override
    public UserLoginResult pwdAuth(UserPwdAuthCmd command) {
        return userAuthLoginExe.execute(command);
    }

    @Override
    public void resetPassword(UserResetPwdCmd command) {
        userResetPasswordExe.execute(command.passwordValidate());
    }

    @Override
    public void loginOut(Long userId) {
        userAuthLoginOutExe.execute(userId);
    }

    @Override
    public void manualCharge(Long userId, Integer value) {
        UserBalance balance = balanceRepository.ofId(userId).orElseThrow(ResponseHandler.USER_BALANCE_NONE);
        balance.manualCharge(value);
        balanceRepository.save(balance);
    }

    @Override
    public void switchUserWithdraw(Long userId) {
        UserBalance userBalance = balanceRepository.ofId(userId)
                                                   .map(UserBalance::switchCanWithdraw)
                                                   .orElseThrow(ResponseHandler.USER_BALANCE_NONE);
        balanceRepository.save(userBalance);
    }

    @Override
    public void switchUserProfit(Long userId) {
        UserBalance userBalance = balanceRepository.ofId(userId)
                                                   .map(UserBalance::switchCanProfit)
                                                   .orElseThrow(ResponseHandler.USER_BALANCE_NONE);
        balanceRepository.save(userBalance);
    }

    @Override
    public UserSignResult userSign(Long userId) {
        return userSignInExe.userSign(userId);
    }

    @Override
    public UserSignVo userSignInfo(Long userId) {
        return userSignInExe.getUserSign(userId);
    }

    @Override
    public UserBalanceVo getUserBalanceDetail(Long userId) {
        return userBalanceDetailExe.execute(userId);
    }

    @Override
    public ExchangeResult couponExchange(Long userId) {
        return couponExchangeExe.execute(userId);
    }

    @Override
    public void memberOpen(MemberOpenCmd command) {
        userMemberAbility.manualMember(command.getUserId(), command.getPackNo(), command.getChannel());
    }

    @Override
    public Integer memberExpire(Integer limit) {
        return userMemberRepository.expiredMembers(limit);
    }
}
