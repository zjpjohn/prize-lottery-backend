package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.ssq.SsqRecommendPo;
import com.prize.lottery.value.Period;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsqRecommendMapper {

    int addSsqRecommend(SsqRecommendPo recommend);

    int editSsqRecommend(SsqRecommendPo recommend);

    SsqRecommendPo getSsqRecommendByPeriod(String period);

    SsqRecommendPo getSsqRecommendHitByPeriod(String period);

    Period latestSsqRecommendPeriod();

    List<SsqRecommendPo> getSsqRecommendList(PageCondition condition);

    int countSsqRecommendList(PageCondition condition);
}
