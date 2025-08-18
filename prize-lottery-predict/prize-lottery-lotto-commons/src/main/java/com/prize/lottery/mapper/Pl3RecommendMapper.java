package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.pl3.Pl3RecommendPo;
import com.prize.lottery.value.Period;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pl3RecommendMapper {

    int addPl3Recommend(Pl3RecommendPo recommend);

    int editPl3Recommend(Pl3RecommendPo recommend);

    Pl3RecommendPo getPl3RecommendByPeriod(String period);

    Pl3RecommendPo getPl3RecommendHitByPeriod(String period);

    List<Pl3RecommendPo> getPl3RecommendList(PageCondition condition);

    int countPl3Recommends(PageCondition condition);

    Period newestPl3RecommendPeriod();

}
