package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.share.model.EarlyWarning;
import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dEarlyWarningPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Fc3dRecommendConverter {

    Fc3dComRecommendPo toPo(N3ComRecommendDo recommend);

    Fc3dEarlyWarningPo toPo(String period, EarlyWarning warning);

    N3ComRecommendDo toDo(Fc3dComRecommendPo recommend);
}
