package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.WarningEnums;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3EarlyWarningPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Pl3ComRecommendMapper {

    int saveEarlyWarning(List<Pl3EarlyWarningPo> warnings);

    Pl3EarlyWarningPo getEarlyWarning(@Param("period") String period, @Param("type") WarningEnums type);

    List<Pl3EarlyWarningPo> getEarlyWarnings(String period);

    int saveComRecommend(Pl3ComRecommendPo recommend);

    Pl3ComRecommendPo latestComRecommend();

    Optional<Pl3ComRecommendPo> getRecommendHit(String period);

    Pl3ComRecommendPo getComRecommend(String period);

    List<Pl3ComRecommendPo> getComRecommends(PageCondition condition);

    int countComRecommends(PageCondition condition);

    String latestRecommendPeriod();

    List<String> latestRecommendPeriods();

}
