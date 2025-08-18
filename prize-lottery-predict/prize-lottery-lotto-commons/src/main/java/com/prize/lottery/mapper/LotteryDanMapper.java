package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryAroundPo;
import com.prize.lottery.po.lottery.LotteryHoneyPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryDanMapper {

    int addAroundList(List<LotteryAroundPo> aroundList);

    int addLotteryAround(LotteryAroundPo around);

    int editLotteryAround(LotteryAroundPo around);

    int delLotteryAround(Long id);

    Optional<LotteryAroundPo> getLotteryAround(@Param("lotto") LotteryEnum lotto, @Param("period") String period);

    List<LotteryAroundPo> getLotteryAroundList(PageCondition condition);

    int countLotteryArounds(PageCondition condition);

    List<String> getAroundPeriods(@Param("lotto") LotteryEnum lotto);

    String latestAroundPeriod(@Param("lotto") LotteryEnum lotto);

    int addHoneyList(List<LotteryHoneyPo> honeyList);

    int addLotteryHoney(LotteryHoneyPo honey);

    int delLotteryHoney(Long id);

    Optional<LotteryHoneyPo> getLotteryHoney(@Param("type") LotteryEnum type, @Param("period") String period);

    List<String> getHoneyPeriods(@Param("type") LotteryEnum type);

    String latestHoneyPeriod(@Param("type") LotteryEnum type);

    List<LotteryHoneyPo> getLotteryHoneyList(PageCondition condition);

    int countLotteryHoneys(PageCondition condition);

}

