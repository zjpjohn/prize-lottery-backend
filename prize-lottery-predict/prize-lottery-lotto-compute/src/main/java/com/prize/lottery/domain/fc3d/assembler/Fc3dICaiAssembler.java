package com.prize.lottery.domain.fc3d.assembler;

import com.prize.lottery.domain.fc3d.model.Fc3dCensusDo;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Fc3dICaiAssembler {

    @Mapping(source = "chartType.type", target = "type")
    Fc3dLottoCensusPo toPo(Fc3dCensusDo census);

    List<Fc3dLottoCensusPo> toPos(List<Fc3dCensusDo> censuses);
}
