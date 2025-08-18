package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.WarningEnums;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dEarlyWarningPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Fc3dComRecommendMapper {

    int saveEarlyWarning(List<Fc3dEarlyWarningPo> warnings);

    Fc3dEarlyWarningPo getEarlyWarning(@Param("period") String period, @Param("type") WarningEnums type);

    List<Fc3dEarlyWarningPo> getEarlyWarnings(String period);

    int saveFc3dComRecommend(Fc3dComRecommendPo recommend);

    String latestRecommendPeriod();

    List<String> fc3dRecommendPeriods();

    Fc3dComRecommendPo latestComRecommend();

    Optional<Fc3dComRecommendPo> getRecommendHit(String period);

    Fc3dComRecommendPo getFc3dComRecommend(String period);

    List<Fc3dComRecommendPo> getFc3dComRecommends(PageCondition condition);

    int countFc3dComRecommends(PageCondition condition);
}
