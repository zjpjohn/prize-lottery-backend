package com.prize.lottery.domain.message.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.message.model.ChannelInfoDo;

public interface IChannelInfoRepository {

    void save(Aggregate<Long, ChannelInfoDo> aggregate);

    Aggregate<Long, ChannelInfoDo> ofId(Long id);

    ChannelInfoDo ofUk(String channel);
}
