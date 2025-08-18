package com.prize.lottery.domain.dlt.assembler;

import com.prize.lottery.domain.dlt.model.DltCensusDo;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DltICaiAssembler {


    @Mapping(source = "chartType.type", target = "type")
    DltLottoCensusPo toPo(DltCensusDo census);

    List<DltLottoCensusPo> toPos(List<DltCensusDo> censuses);

}
