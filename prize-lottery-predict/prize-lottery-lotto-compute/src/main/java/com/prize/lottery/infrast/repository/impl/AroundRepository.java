package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.lottery.model.LotteryAroundDo;
import com.prize.lottery.domain.lottery.repository.IAroundRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.LotteryDanConverter;
import com.prize.lottery.mapper.LotteryDanMapper;
import com.prize.lottery.po.lottery.LotteryAroundPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AroundRepository implements IAroundRepository {

    private final LotteryDanMapper    mapper;
    private final LotteryDanConverter converter;

    @Override
    public void save(Aggregate<Long, LotteryAroundDo> aggregate) {
        LotteryAroundDo root = aggregate.getRoot();
        if (root.isNew()) {
            LotteryAroundPo around = converter.toPo(root);
            mapper.addLotteryAround(around);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editLotteryAround);
    }

    @Override
    public void saveBatch(List<LotteryAroundDo> aroundList) {
        List<LotteryAroundPo> poList = converter.toAroundList(aroundList);
        mapper.addAroundList(poList);
    }

    @Override
    public void remove(Long id) {
        mapper.delLotteryAround(id);
    }

    @Override
    public Optional<Aggregate<Long, LotteryAroundDo>> of(String period, LotteryEnum type) {
        return mapper.getLotteryAround(type, period).map(converter::toDo).map(AggregateFactory::create);
    }

}
