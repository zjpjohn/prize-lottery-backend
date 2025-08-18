package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.dlt.DltRecommendPo;
import com.prize.lottery.value.Period;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DltRecommendMapper {

    int addDltRecommend(DltRecommendPo recommend);

    int editDltRecommend(DltRecommendPo recommend);

    DltRecommendPo getDltRecommendByPeriod(String period);

    DltRecommendPo getDltRecommendHitByPeriod(String period);

    List<DltRecommendPo> getDltRecommendList(PageCondition condition);

    int countDltRecommendList(PageCondition condition);

    Period latestDltRecommendPeriod();
}
