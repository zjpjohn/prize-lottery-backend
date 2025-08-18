package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.share.model.Num3ComWarnDo;
import com.prize.lottery.domain.share.model.Num3LayerFilterDo;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Num3ComWarnConverter {

    Num3ComWarnDo toDo(Num3ComWarningPo warning);

    Num3ComWarningPo toPo(Num3ComWarnDo warn);

    Num3LayerFilterDo toDo(Num3LayerFilterPo filter);

    Num3LayerFilterPo toPo(Num3LayerFilterDo filter);

}
