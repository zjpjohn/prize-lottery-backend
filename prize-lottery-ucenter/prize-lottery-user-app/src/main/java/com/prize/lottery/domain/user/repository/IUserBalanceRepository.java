package com.prize.lottery.domain.user.repository;


import com.prize.lottery.domain.user.model.UserBalance;

import java.util.List;
import java.util.Optional;

public interface IUserBalanceRepository {

    void save(UserBalance userBalance);

    void saveBatch(List<UserBalance> balances);

    Optional<UserBalance> ofId(Long userId);

    List<UserBalance> ofUsers(List<Long> userIds);

}
