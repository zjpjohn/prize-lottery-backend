package com.prize.lottery.domain.ssq.assembler;

import com.prize.lottery.domain.ssq.model.SsqCensusDo;
import com.prize.lottery.po.ssq.SsqLottoCensusPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SsqICaiAssembler {

    @Mapping(source = "chartType.type", target = "type")
    SsqLottoCensusPo toPo(SsqCensusDo census);

    List<SsqLottoCensusPo> toPos(List<SsqCensusDo> censuses);

}

