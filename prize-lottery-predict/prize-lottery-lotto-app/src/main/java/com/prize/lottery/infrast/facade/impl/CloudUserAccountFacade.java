package com.prize.lottery.infrast.facade.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.api.IUserInfoService;
import com.prize.lottery.dto.AcctVerifyRepo;
import com.prize.lottery.dto.ExpertAcctRepo;
import com.prize.lottery.dto.UserInfoRepo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.facade.ICloudUserAccountFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class CloudUserAccountFacade implements ICloudUserAccountFacade {

    @DubboReference
    private IUserInfoService userInfoService;

    @Override
    public AcctVerifyRepo verifyBalance(Long userId, Integer balance) {
        return userInfoService.verifyBalance(userId, balance);
    }

    @Override
    public boolean isUserMember(Long userId) {
        return userInfoService.isUserMember(userId);
    }

    @Override
    public UserInfoRepo getCloudUserInfo(Long userId) {
        UserInfoRepo userInfo = userInfoService.getUserInfo(userId);
        return Assert.notNull(userInfo, ResponseHandler.USER_INFO_NONE);
    }

    @Override
    public ExpertAcctRepo getCloudUserExpert(Long userId) {
        ExpertAcctRepo expertAcct = userInfoService.getExpertUser(userId);
        return Assert.notNull(expertAcct, ResponseHandler.MASTER_NONE);
    }

    @Override
    public List<UserInfoRepo> getUserList(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userInfoService.getUserInfoList(userIds);
    }

}
