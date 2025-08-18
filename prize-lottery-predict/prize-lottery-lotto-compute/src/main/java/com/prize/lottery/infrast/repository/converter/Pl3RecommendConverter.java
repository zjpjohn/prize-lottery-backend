package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.share.model.EarlyWarning;
import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3EarlyWarningPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Pl3RecommendConverter {

    Pl3ComRecommendPo toPo(N3ComRecommendDo recommend);

    Pl3EarlyWarningPo toPo(String period, EarlyWarning warning);

    N3ComRecommendDo toDo(Pl3ComRecommendPo recommend);
}
