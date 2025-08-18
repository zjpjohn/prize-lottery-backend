package com.prize.lottery.domain.facade;


import com.prize.lottery.domain.facade.dto.UserInfo;

public interface IUserInfoFacade {

    UserInfo queryUserInfo(Long userId);

}
