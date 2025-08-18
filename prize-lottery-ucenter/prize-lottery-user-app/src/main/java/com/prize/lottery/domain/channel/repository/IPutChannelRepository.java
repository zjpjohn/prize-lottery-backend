package com.prize.lottery.domain.channel.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.channel.model.PutChannelDo;

public interface IPutChannelRepository {

    void save(Aggregate<Long, PutChannelDo> aggregate);

    Aggregate<Long, PutChannelDo> ofId(Long id);

}
