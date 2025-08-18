package com.prize.lottery.application.command.service;


import com.prize.lottery.application.vo.SsqForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.ssq.SsqChartCensusVo;

import java.util.List;

public interface ISsqLottoCommandService {

    SsqForecastVo lookupForecast(Long userId, String masterId);

    SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel);

    SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel);

    SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel);

    SsqChartCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId);

    FeeDataResult<SsqChartCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId);

    SsqChartCensusVo getTypeLevelCensusDetail(Long userId, ChartType type, Integer level);

    FeeDataResult<SsqChartCensusVo> getTypeLevelCensusDetailV1(Long userId, ChartType type, Integer level);

    List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit);

    MasterBattleVo addMasterBattle(Long userId, String masterId);

}
