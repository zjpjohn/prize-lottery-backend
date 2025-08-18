package com.prize.lottery.domain.order.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.order.model.aggregate.ChargeConfDo;

public interface IChargeConfRepository {

    void save(Aggregate<Long, ChargeConfDo> aggregate);

    Aggregate<Long, ChargeConfDo> ofId(Long id);

    void clearConfig();
}
