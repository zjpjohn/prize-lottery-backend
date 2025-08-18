package com.prize.lottery.domain.user.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.user.model.UserWithdrawDo;

import java.util.Optional;

public interface IUserWithdrawRepository {

    void save(Aggregate<Long, UserWithdrawDo> aggregate);

    Optional<Aggregate<Long, UserWithdrawDo>> ofSeqNo(String no);


}
