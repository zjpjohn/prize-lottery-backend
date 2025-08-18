package com.prize.lottery.domain.pl3.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.pl3.model.Pl3PivotDo;

import java.util.Optional;

public interface IPl3PivotRepository {

    void save(Aggregate<Long, Pl3PivotDo> aggregate);

    Optional<Aggregate<Long,Pl3PivotDo>> ofPeriod(String period);

}
