package com.prize.lottery.api;


import com.prize.lottery.dto.*;

import java.util.List;

public interface IUserInfoService {

    UserInfoRepo getUserInfo(Long userId);

    UserInfoRepo getUserWithMember(Long userId);

    List<UserInfoRepo> getUserInfoList(List<Long> idList);

    UserInfoRepo getUserInfo(String phone);

    UserBalanceRepo getUserBalance(Long userId);

    AcctVerifyRepo verifyBalance(Long userId, Integer expend);

    ExpertAcctRepo getExpertUser(Long userId);

    UserAccountRepo getUserAccount(Long userId);

    List<UserAccountRepo> getUserAccounts(List<Long> users);

    boolean isUserMember(Long userId);

}
