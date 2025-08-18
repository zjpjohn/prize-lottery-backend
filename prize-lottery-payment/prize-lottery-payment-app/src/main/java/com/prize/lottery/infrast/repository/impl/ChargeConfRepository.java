package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.order.model.aggregate.ChargeConfDo;
import com.prize.lottery.domain.order.repository.IChargeConfRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.ChargeConfMapper;
import com.prize.lottery.infrast.persist.po.ChargeConfPo;
import com.prize.lottery.infrast.repository.converter.ChargeConfConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChargeConfRepository implements IChargeConfRepository {

    private final ChargeConfMapper    mapper;
    private final ChargeConfConverter converter;

    @Override
    public void save(Aggregate<Long, ChargeConfDo> aggregate) {
        ChargeConfDo root = aggregate.getRoot();
        if (root.isNew()) {
            ChargeConfPo chargeConf = converter.toPo(root);
            mapper.addChargeConf(chargeConf);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editChargeConf);
    }

    @Override
    public Aggregate<Long, ChargeConfDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getChargeConf(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.CHARGE_CONF_NONE);
    }

    @Override
    public void clearConfig() {
        mapper.removeInvalidConf();
    }
}
