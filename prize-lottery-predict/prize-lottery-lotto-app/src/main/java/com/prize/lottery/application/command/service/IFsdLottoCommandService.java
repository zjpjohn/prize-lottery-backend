package com.prize.lottery.application.command.service;


import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import com.prize.lottery.application.query.dto.N3DanFilterQuery;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;

import java.util.List;

public interface IFsdLottoCommandService {

    Fc3dForecastVo lookupForecast(Long userId, String masterId);

    SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel);

    SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel);

    SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel);

    FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel);

    NumberThreeCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId);

    FeeDataResult<NumberThreeCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId);

    NumberThreeCensusVo getTypeLevelCensusDetail(Long userId, ChartType chartType, Integer level);

    FeeDataResult<NumberThreeCensusVo> getTypeLevelCensusDetailV1(Long userId, ChartType chartType, Integer level);

    List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit);

    MasterBattleVo addMasterBattle(Long userId, String masterId);

    FeeDataResult<N3WarnRecommendVo> warnRecommend(Long userId, String period);

    Com7CombineResult com7Combine(ComCombineCalcCmd cmd);

    FeeDataResult<N3TodayPivotVo> todayPivot(Long userId, String period);

    N3Comb5StatsVo statsComb5(N3DanFilterQuery query);

}
