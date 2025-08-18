package com.prize.lottery.domain.user.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.user.model.UserInfo;

import java.util.Optional;

public interface IUserInfoRepository {

    void save(Aggregate<Long, UserInfo> userInfo);

    Aggregate<Long, UserInfo> ofId(Long userId);

    Optional<Aggregate<Long, UserInfo>> ofPhone(String phone);

    UserInfo query(Long userId);

    boolean exist(Long userId);

}
