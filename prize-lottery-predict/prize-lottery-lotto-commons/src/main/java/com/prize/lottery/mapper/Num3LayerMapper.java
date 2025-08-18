package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import com.prize.lottery.vo.Num3LayerStateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface Num3LayerMapper {

    int saveNum3LayerFilter(Num3LayerFilterPo filter);

    Num3LayerFilterPo getNum3LayerFilter(Long id);

    Num3LayerFilterPo getNum3LayerFilterByUk(@Param("period") String period, @Param("type") LotteryEnum type);

    List<Num3LayerFilterPo> getNum3LayerFilters(PageCondition condition);

    Optional<Num3LayerStateVo> getNum3LayerState(@Param("type") LotteryEnum type);

    int countNum3LayerFilters(PageCondition condition);

    String latestPeriod(@Param("type") LotteryEnum type);

    List<String> layerPeriods(@Param("type") LotteryEnum type);

}
