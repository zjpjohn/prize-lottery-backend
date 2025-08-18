package com.prize.lottery.domain.user.repository;


import com.prize.lottery.domain.user.model.UserSubscribe;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface IUserMasterRepository {

    void save(UserSubscribe subscribe);

    void saveBatch(List<UserSubscribe> subscribes);

    UserSubscribe ofUk(Long userId,String masterId, LotteryEnum type);

    Boolean hasSubscribed(UserSubscribe subscribe);

    void removeSubscribe(UserSubscribe subscribe);
}
