package com.prize.lottery.domain.user.repository;


import com.prize.lottery.domain.user.model.UserFocus;

public interface IUserFocusRepository {

    void save(UserFocus focus);

    UserFocus ofUk(Long userId, String masterId);

    boolean hasFocused(Long userId, String masterId);

    void remove(UserFocus focus);

}
