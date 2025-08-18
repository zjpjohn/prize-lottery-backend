package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Num3ComWarnMapper {

    int addNum3ComWarn(Num3ComWarningPo warning);

    int editNum3ComWarn(Num3ComWarningPo warning);

    Optional<Num3ComWarningPo> getNum3ComWarnById(Long id);

    Optional<Num3ComWarningPo> getNum3ComWarnByUk(@Param("type") LotteryEnum type, @Param("period") String period);

    List<Num3ComWarningPo> getNum3ComWarnList(PageCondition condition);

    int countNum3ComWarns(PageCondition condition);

    String latestWarnPeriod(@Param("type") LotteryEnum type);

    List<String> comWarnPeriods(@Param("type") LotteryEnum type);

}
