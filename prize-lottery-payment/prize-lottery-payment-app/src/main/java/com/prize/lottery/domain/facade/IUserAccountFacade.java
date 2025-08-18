package com.prize.lottery.domain.facade;


import com.prize.lottery.domain.facade.dto.UserInfo;

import java.util.List;

public interface IUserAccountFacade {

    UserInfo getUserInfo(Long userId);

    UserInfo getUserInfo(String phone);

    List<UserInfo> getUserList(List<Long> idList);

}
