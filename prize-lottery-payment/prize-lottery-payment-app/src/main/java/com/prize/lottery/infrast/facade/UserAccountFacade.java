package com.prize.lottery.infrast.facade;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.api.IUserInfoService;
import com.prize.lottery.domain.facade.IUserAccountFacade;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.dto.UserInfoRepo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserAccountFacade implements IUserAccountFacade {

    @DubboReference
    private IUserInfoService userInfoService;

    @Override
    public UserInfo getUserInfo(Long userId) {
        UserInfoRepo userInfo = userInfoService.getUserInfo(userId);
        Assert.notNull(userInfo, ResponseHandler.TRANSFER_ACCOUNT_NONE);
        return this.convert(userInfo);
    }

    @Override
    public UserInfo getUserInfo(String phone) {
        UserInfoRepo userInfo = userInfoService.getUserInfo(phone);
        Assert.notNull(userInfo, ResponseHandler.TRANSFER_ACCOUNT_NONE);
        return this.convert(userInfo);
    }

    @Override
    public List<UserInfo> getUserList(List<Long> idList) {
        List<UserInfoRepo> userList = userInfoService.getUserInfoList(idList);
        return userList.stream().map(this::convert).collect(Collectors.toList());
    }

    private UserInfo convert(UserInfoRepo user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setPhone(user.getPhone());
        userInfo.setNickname(user.getNickname());
        userInfo.setState(user.getState());
        return userInfo;
    }

}
