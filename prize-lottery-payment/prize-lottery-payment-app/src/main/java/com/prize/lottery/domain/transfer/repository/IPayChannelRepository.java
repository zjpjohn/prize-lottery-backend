package com.prize.lottery.domain.transfer.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;

public interface IPayChannelRepository {

    void save(Aggregate<Long, PayChannelDo> aggregate);

    Aggregate<Long, PayChannelDo> ofId(Long id);

    PayChannelDo ofChannel(String channel);

}
