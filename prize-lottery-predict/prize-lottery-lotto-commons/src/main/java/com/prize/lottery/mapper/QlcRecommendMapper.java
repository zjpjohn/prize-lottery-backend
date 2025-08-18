package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.qlc.QlcRecommendPo;
import com.prize.lottery.value.Period;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QlcRecommendMapper {

    int addQlcRecommend(QlcRecommendPo recommend);

    int editQlcRecommend(QlcRecommendPo recommend);

    QlcRecommendPo getQlcRecommendByPeriod(String period);

    QlcRecommendPo getQlcRecommendHitByPeriod(String period);

    List<QlcRecommendPo> getQlcRecommendList(PageCondition condition);

    int countQlcRecommendList(PageCondition condition);

    Period latestQlcRecommendPeriod();
}
