package com.prize.lottery.domain.user.repository;


import com.prize.lottery.domain.user.model.UserLogin;

public interface IUserLoginRepository {

    void save(UserLogin login);

    UserLogin ofId(Long userId);

    boolean existDevice(String deviceId);

    boolean hasBindDevice(String deviceId);

}
