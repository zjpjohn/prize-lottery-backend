package com.prize.lottery.domain.withdraw.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.withdraw.model.WithdrawLevelDo;

public interface IWithdrawLevelRepository {

    void save(Aggregate<Long, WithdrawLevelDo> aggregate);

    Aggregate<Long, WithdrawLevelDo> ofId(Long id);

}
