package com.prize.lottery.domain.agent.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.user.model.UserWithdrawDo;

import java.util.Optional;

public interface IAgentWithdrawRepository {

    void save(Aggregate<Long, UserWithdrawDo> aggregate);

    Optional<Aggregate<Long, UserWithdrawDo>> ofSeqNo(String seqNo);

}
