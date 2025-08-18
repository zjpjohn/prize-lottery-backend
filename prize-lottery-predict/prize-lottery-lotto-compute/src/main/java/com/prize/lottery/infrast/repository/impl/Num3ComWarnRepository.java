package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.share.model.Num3ComWarnDo;
import com.prize.lottery.domain.share.repository.INum3ComWarnRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.Num3ComWarnConverter;
import com.prize.lottery.mapper.Num3ComWarnMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Num3ComWarnRepository implements INum3ComWarnRepository {

    private final Num3ComWarnMapper    mapper;
    private final Num3ComWarnConverter converter;

    @Override
    public void save(Aggregate<Long, Num3ComWarnDo> aggregate) {
        Num3ComWarnDo root = aggregate.getRoot();
        if (root.isNew()) {
            mapper.addNum3ComWarn(converter.toPo(root));
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editNum3ComWarn);
    }

    @Override
    public Optional<Aggregate<Long, Num3ComWarnDo>> ofId(Long id) {
        return mapper.getNum3ComWarnById(id).map(converter::toDo).map(AggregateFactory::create);
    }

    @Override
    public Optional<Aggregate<Long, Num3ComWarnDo>> ofUk(LotteryEnum type, String period) {
        return mapper.getNum3ComWarnByUk(type, period).map(converter::toDo).map(AggregateFactory::create);
    }

}
