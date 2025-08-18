package com.prize.lottery.domain.message.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.message.model.AnnounceInfoDo;
import com.prize.lottery.domain.message.model.AnnounceMailboxDo;

public interface IAnnounceRepository {

    void save(Aggregate<Long, AnnounceInfoDo> aggregate);

    Aggregate<Long, AnnounceInfoDo> of(Long id);

    void save(AnnounceMailboxDo mailbox);

    AnnounceMailboxDo ofMailBox(Long receiverId, String channel);

}
