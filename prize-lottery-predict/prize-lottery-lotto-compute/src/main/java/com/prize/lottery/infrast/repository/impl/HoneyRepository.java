package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.lottery.model.LotteryHoneyDo;
import com.prize.lottery.domain.lottery.repository.IHoneyRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.LotteryDanConverter;
import com.prize.lottery.mapper.LotteryDanMapper;
import com.prize.lottery.po.lottery.LotteryHoneyPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HoneyRepository implements IHoneyRepository {

    private final LotteryDanMapper    mapper;
    private final LotteryDanConverter converter;

    @Override
    public void save(Aggregate<Long, LotteryHoneyDo> aggregate) {
        LotteryHoneyDo root  = aggregate.getRoot();
        LotteryHoneyPo honey = converter.toPo(root);
        mapper.addLotteryHoney(honey);
    }

    @Override
    public void saveBatch(List<LotteryHoneyDo> honeyList) {
        List<LotteryHoneyPo> poList = converter.toHoneyList(honeyList);
        mapper.addHoneyList(poList);
    }

    @Override
    public void remove(Long id) {
        mapper.delLotteryHoney(id);
    }

    @Override
    public Optional<Aggregate<Long, LotteryHoneyDo>> of(String period, LotteryEnum type) {
        return mapper.getLotteryHoney(type, period).map(converter::toDo).map(AggregateFactory::create);
    }

}
