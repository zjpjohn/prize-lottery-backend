package com.prize.lottery.domain.user.repository;


import com.prize.lottery.domain.user.model.UserSign;

public interface IUserSignRepository {

    void save(UserSign userSign);

    UserSign ofId(Long userId);
}
