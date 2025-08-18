package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.*;
import com.prize.lottery.application.command.vo.*;

public interface IUserCommandService {

    UserLoginResult smsAuth(UserSmsAuthCmd command);

    UserLoginResult quickAuth(UserQuickAuthCmd command);

    UserLoginResult pwdAuth(UserPwdAuthCmd command);

    void resetPassword(UserResetPwdCmd command);

    UserSignResult userSign(Long userId);

    UserSignVo userSignInfo(Long userId);

    UserBalanceVo getUserBalanceDetail(Long userId);

    ExchangeResult couponExchange(Long userId);

    void loginOut(Long userId);

    void manualCharge(Long userId, Integer value);

    void switchUserWithdraw(Long userId);

    void switchUserProfit(Long userId);

    void memberOpen(MemberOpenCmd command);

    Integer memberExpire(Integer limit);

}
