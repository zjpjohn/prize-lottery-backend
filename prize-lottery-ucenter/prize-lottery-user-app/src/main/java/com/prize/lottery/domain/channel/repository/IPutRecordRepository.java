package com.prize.lottery.domain.channel.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.channel.model.PutRecordDo;

import java.util.Optional;

public interface IPutRecordRepository {

    void save(Aggregate<Long, PutRecordDo> aggregate);

    Optional<Aggregate<Long, PutRecordDo>> ofId(Long id);

    Optional<Aggregate<Long, PutRecordDo>> ofCode(String code);

}
