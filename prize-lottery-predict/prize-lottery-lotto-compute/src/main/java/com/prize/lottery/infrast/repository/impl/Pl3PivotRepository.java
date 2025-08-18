package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.pl3.model.Pl3PivotDo;
import com.prize.lottery.domain.pl3.repository.IPl3PivotRepository;
import com.prize.lottery.infrast.repository.converter.LotteryPivotConverter;
import com.prize.lottery.mapper.Pl3PivotMapper;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3PivotRepository implements IPl3PivotRepository {

    private final Pl3PivotMapper        mapper;
    private final LotteryPivotConverter converter;

    @Override
    public void save(Aggregate<Long, Pl3PivotDo> aggregate) {
        Pl3PivotDo root = aggregate.getRoot();
        if (root.isNew()) {
            Pl3PivotPo pl3Pivot = converter.toPo(root);
            mapper.addPl3Pivot(pl3Pivot);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editPl3Pivot);
    }

    @Override
    public Optional<Aggregate<Long, Pl3PivotDo>> ofPeriod(String period) {
        return mapper.getPl3Pivot(period).map(converter::toDo).map(AggregateFactory::create);
    }

}
