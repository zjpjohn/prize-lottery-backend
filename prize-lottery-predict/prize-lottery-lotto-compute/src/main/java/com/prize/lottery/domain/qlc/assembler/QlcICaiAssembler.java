package com.prize.lottery.domain.qlc.assembler;

import com.prize.lottery.domain.qlc.model.QlcCensusDo;
import com.prize.lottery.po.qlc.QlcLottoCensusPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QlcICaiAssembler {

    @Mapping(source = "chartType.type", target = "type")
    QlcLottoCensusPo toPo(QlcCensusDo census);

    List<QlcLottoCensusPo> toPos(List<QlcCensusDo> censuses);

}
