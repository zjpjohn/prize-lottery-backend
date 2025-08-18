package com.prize.lottery.application.command.service;


import com.prize.lottery.application.vo.DltForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.dlt.DltChartCensusVo;

import java.util.List;

public interface IDltLottoCommandService {

    DltForecastVo lookupForecast(Long userId, String masterId);

    SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel);

    SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel);

    SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel);

    DltChartCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId);

    FeeDataResult<DltChartCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId);

    DltChartCensusVo getTypeLevelCensusDetail(Long userId, ChartType chartType, Integer level);

    FeeDataResult<DltChartCensusVo> getTypeLevelCensusDetailV1(Long userId, ChartType chartType, Integer level);

    List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit);

    MasterBattleVo addMasterBattle(Long userId, String masterId);

}
