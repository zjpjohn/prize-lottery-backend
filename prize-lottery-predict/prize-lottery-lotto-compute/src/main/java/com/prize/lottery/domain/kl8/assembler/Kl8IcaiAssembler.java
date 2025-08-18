package com.prize.lottery.domain.kl8.assembler;

import com.prize.lottery.domain.kl8.model.Kl8CensusDo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Kl8IcaiAssembler {

    @Mapping(source = "chartType.type", target = "type")
    Kl8LottoCensusPo toPo(Kl8CensusDo census);

    List<Kl8LottoCensusPo> toPos(List<Kl8CensusDo> censuses);

}
