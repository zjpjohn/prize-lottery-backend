package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.Num3LottoIndexPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Num3LottoIndexMapper {

    int addNum3LottoIndex(Num3LottoIndexPo index);

    Num3LottoIndexPo getLottoIndexById(Long id);

    Num3LottoIndexPo getLottoIndexByUk(@Param("type") LotteryEnum type, @Param("period") String period);

    List<Num3LottoIndexPo> getLottoIndices(PageCondition condition);

    int countLottoIndices(PageCondition condition);

    String latestIndexPeriod(@Param("type") LotteryEnum type);

    List<String> getIndexPeriods(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

}
