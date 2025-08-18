package com.prize.lottery.adapter.rpc;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.api.IUserInfoService;
import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.query.IUserExpertQueryService;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.dto.*;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.persist.po.UserBalancePo;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import com.prize.lottery.infrast.persist.po.UserMemberPo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@DubboService
@RequiredArgsConstructor
public class UserInfoServiceProvider implements IUserInfoService {

    private final UserInfoAssembler       userInfoAssembler;
    private final IUserInfoQueryService   userService;
    private final IUserExpertQueryService expertService;
    private final IUserBalanceRepository  balanceRepository;

    @Override
    public UserInfoRepo getUserInfo(Long userId) {
        UserInfoPo userInfo = userService.getUserInfo(userId);
        Assert.notNull(userInfo, ResponseHandler.USER_INFO_NONE);
        return userInfoAssembler.toRepo(userInfo);
    }

    @Override
    public UserInfoRepo getUserWithMember(Long userId) {
        UserInfoRepo userInfo = this.getUserInfo(userId);
        Boolean      member   = userService.getUserMember(userId).map(UserMemberPo::isValid).orElse(Boolean.FALSE);
        userInfo.setMember(member);
        return userInfo;
    }

    @Override
    public List<UserInfoRepo> getUserInfoList(List<Long> idList) {
        List<UserInfoPo> userList = userService.getUserList(idList);
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }
        return userInfoAssembler.toRepoList(userList);
    }

    @Override
    public UserInfoRepo getUserInfo(String phone) {
        UserInfoPo userInfo = userService.getUserInfo(phone);
        Assert.notNull(userInfo, ResponseHandler.USER_INFO_NONE);
        return userInfoAssembler.toRepo(userInfo);
    }

    @Override
    public UserBalanceRepo getUserBalance(Long userId) {
        UserBalancePo balance = userService.getUserBalanceInfo(userId);
        Assert.notNull(balance, ResponseHandler.USER_BALANCE_NONE);
        UserBalanceRepo userBalance = new UserBalanceRepo();
        userBalance.setUserId(balance.getUserId());
        userBalance.setBalance(balance.getBalance());
        //金币账户余额为金币余额+代金券余额
        userBalance.setSurplus(balance.getSurplus() + balance.getVoucher());
        return userBalance;
    }

    @Override
    public AcctVerifyRepo verifyBalance(Long userId, Integer expend) {
        AcctVerifyRepo verify   = new AcctVerifyRepo();
        Boolean        isMember = userService.getUserMember(userId).map(UserMemberPo::isValid).orElse(false);
        verify.setMember(isMember);
        return verify;
    }

    @Override
    public ExpertAcctRepo getExpertUser(Long userId) {
        ExpertAcctPo acct = expertService.getUserExpertAcct(userId);
        Assert.notNull(acct, ResponseHandler.USER_EXPERT_NONE);
        return userInfoAssembler.toRepo(acct);
    }

    @Override
    public UserAccountRepo getUserAccount(Long userId) {
        return userService.getUserAccount(userId);
    }

    @Override
    public List<UserAccountRepo> getUserAccounts(List<Long> users) {
        return userService.getUserAccounts(users);
    }

    @Override
    public boolean isUserMember(Long userId) {
        return userService.getUserMember(userId).map(UserMemberPo::isValid).orElse(false);
    }

}
