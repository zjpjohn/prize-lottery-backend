package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.fc3d.model.Fc3dPivotDo;
import com.prize.lottery.domain.fc3d.repository.IFc3dPivotRepository;
import com.prize.lottery.infrast.repository.converter.LotteryPivotConverter;
import com.prize.lottery.mapper.Fc3dPivotMapper;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Fc3dPivotRepository implements IFc3dPivotRepository {

    private final Fc3dPivotMapper       mapper;
    private final LotteryPivotConverter converter;

    @Override
    public void save(Aggregate<Long, Fc3dPivotDo> aggregate) {
        Fc3dPivotDo root = aggregate.getRoot();
        if (root.isNew()) {
            Fc3dPivotPo fc3dPivot = converter.toPo(root);
            mapper.addFc3dPivot(fc3dPivot);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editFc3dPivot);
    }

    @Override
    public Optional<Aggregate<Long, Fc3dPivotDo>> ofPeriod(String period) {
        return mapper.getFc3dPivot(period).map(converter::toDo).map(AggregateFactory::create);
    }

}
