package com.prize.lottery.domain.pl3.assembler;

import com.prize.lottery.domain.pl3.model.Pl3CensusDo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Pl3ICaiAssembler {

    @Mapping(source = "chartType.type", target = "type")
    Pl3LottoCensusPo toPo(Pl3CensusDo census);

    List<Pl3LottoCensusPo> toPos(List<Pl3CensusDo> censuses);

}
