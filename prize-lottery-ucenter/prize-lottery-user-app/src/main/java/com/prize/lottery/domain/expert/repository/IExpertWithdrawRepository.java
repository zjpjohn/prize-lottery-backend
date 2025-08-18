package com.prize.lottery.domain.expert.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.user.model.UserWithdrawDo;

import java.util.Optional;

public interface IExpertWithdrawRepository {

    void save(Aggregate<Long, UserWithdrawDo> aggregate);

    Optional<Aggregate<Long, UserWithdrawDo>> ofSeqNo(String seqNo);
}
