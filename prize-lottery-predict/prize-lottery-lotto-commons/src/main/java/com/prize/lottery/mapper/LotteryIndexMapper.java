package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.lottery.LotteryIndexPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryIndexMapper {

    int addLotteryIndex(LotteryIndexPo index);

    LotteryIndexPo getLotteryIndexByUk(@Param("lottery") String lottery,
                                       @Param("period") String period,
                                       @Param("type") Integer type);

    List<LotteryIndexPo> getLotteryIndices(@Param("lottery") String lottery,
                                           @Param("period") String period);

    List<LotteryIndexPo> getLotteryIndexList(PageCondition condition);

    int countLotteryIndices(PageCondition condition);

    String latestPeriod(String lottery);

    List<String> indexPeriodList(String lottery);

}
