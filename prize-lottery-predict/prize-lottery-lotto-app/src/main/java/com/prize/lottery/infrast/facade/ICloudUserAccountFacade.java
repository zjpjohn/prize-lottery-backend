package com.prize.lottery.infrast.facade;


import com.prize.lottery.dto.AcctVerifyRepo;
import com.prize.lottery.dto.ExpertAcctRepo;
import com.prize.lottery.dto.UserInfoRepo;

import java.util.List;

public interface ICloudUserAccountFacade {

    AcctVerifyRepo verifyBalance(Long userId, Integer balance);

    boolean isUserMember(Long userId);

    UserInfoRepo getCloudUserInfo(Long userId);

    ExpertAcctRepo getCloudUserExpert(Long userId);

    List<UserInfoRepo> getUserList(List<Long> userIds);

}
