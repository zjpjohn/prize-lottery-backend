package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.share.model.Num3LayerFilterDo;
import com.prize.lottery.domain.share.repository.INum3LayerRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.Num3ComWarnConverter;
import com.prize.lottery.mapper.Num3LayerMapper;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Num3LayerRepository implements INum3LayerRepository {

    private final Num3LayerMapper      mapper;
    private final Num3ComWarnConverter converter;

    @Override
    public void save(Num3LayerFilterDo root) {
        Num3LayerFilterPo layerFilter = converter.toPo(root);
        mapper.saveNum3LayerFilter(layerFilter);
    }

    @Override
    public Optional<Num3LayerFilterDo> ofUk(String period, LotteryEnum type) {
        return Optional.ofNullable(mapper.getNum3LayerFilterByUk(period, type)).map(converter::toDo);
    }

    @Override
    public Optional<Num3LayerFilterDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getNum3LayerFilter(id)).map(converter::toDo);
    }

}
