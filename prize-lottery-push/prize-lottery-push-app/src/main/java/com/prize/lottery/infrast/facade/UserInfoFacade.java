package com.prize.lottery.infrast.facade;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.api.IUserInfoService;
import com.prize.lottery.domain.facade.IUserInfoFacade;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.dto.UserInfoRepo;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.enums.ChannelScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserInfoFacade implements IUserInfoFacade {

    @DubboReference
    private IUserInfoService userInfoService;

    @Override
    public UserInfo queryUserInfo(Long userId) {
        UserInfoRepo user = userInfoService.getUserInfo(userId);
        Assert.notNull(user, ResponseErrorHandler.USER_NOT_EXIST);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setPhone(user.getPhone());
        //用户消息渠道可见范围
        List<Integer> scopes = Lists.newArrayList();
        scopes.add(ChannelScope.ALL.getScope());
        scopes.add(ChannelScope.USER.getScope());
        if (user.getExpert() == 1) {
            scopes.add(ChannelScope.EXPERT.getScope());
        }
        userInfo.setScopes(scopes);
        return userInfo;
    }
}
