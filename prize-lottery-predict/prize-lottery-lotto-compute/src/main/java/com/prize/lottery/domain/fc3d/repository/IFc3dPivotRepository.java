package com.prize.lottery.domain.fc3d.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.fc3d.model.Fc3dPivotDo;

import java.util.Optional;

public interface IFc3dPivotRepository {

    void save(Aggregate<Long, Fc3dPivotDo> aggregate);

    Optional<Aggregate<Long,Fc3dPivotDo>> ofPeriod(String period);

}
