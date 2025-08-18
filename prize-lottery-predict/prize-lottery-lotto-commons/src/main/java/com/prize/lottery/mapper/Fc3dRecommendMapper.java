package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.fc3d.Fc3dRecommendPo;
import com.prize.lottery.value.Period;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Fc3dRecommendMapper {

    int addFc3dRecommend(Fc3dRecommendPo recommend);

    int editFc3dRecommend(Fc3dRecommendPo recommend);

    Fc3dRecommendPo getFc3dRecommendByPeriod(String period);

    Fc3dRecommendPo getFc3dRecommendHitByPeriod(String period);

    List<Fc3dRecommendPo> getFc3dRecommendList(PageCondition condition);

    int countFc3dRecommends(PageCondition condition);

    Period newestFc3dRecommendPeriod();

}
