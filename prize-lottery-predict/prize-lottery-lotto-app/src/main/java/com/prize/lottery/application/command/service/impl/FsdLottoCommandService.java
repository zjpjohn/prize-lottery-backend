package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import com.prize.lottery.application.command.executor.lotto.fsd.*;
import com.prize.lottery.application.command.service.IFsdLottoCommandService;
import com.prize.lottery.application.query.dto.N3DanFilterQuery;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FsdLottoCommandService implements IFsdLottoCommandService {

    @Resource
    private FsdCompareBrowseExe        fsdCompareBrowseExe;
    @Resource
    private FsdForecastBrowseExe       fsdForecastBrowseExe;
    @Resource
    private FsdFullOrVipChartBrowseExe fsdFullOrVipChartBrowseExe;
    @Resource
    private FsdHotOrRateChartBrowseExe fsdHotOrRateChartBrowseExe;
    @Resource
    private FsdTypeLevelChartBrowseExe fsdTypeLevelChartBrowseExe;
    @Resource
    private FsdForecastBattleExe       fsdForecastBattleExe;
    @Resource
    private FsdWarnRecommendBrowseExe  recommendBrowseExe;
    @Resource
    private Fc3dCom7CombineExe         fc3dCom7CombineExe;
    @Resource
    private FsdTodayPivotBrowseExe     todayPivotBrowseExe;
    @Resource
    private Fc3dComb5StatsExe          fc3dComb5StatsExe;

    @Override
    @Transactional
    public Fc3dForecastVo lookupForecast(Long userId, String masterId) {
        return fsdForecastBrowseExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel) {
        Fc3dChannel           fc3dChannel = Fc3dChannel.findOf(channel);
        List<BaseLottoCensus> censuses    = fsdFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, fc3dChannel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel) {
        Fc3dChannel fc3dChannel = Fc3dChannel.findOf(channel);
        return fsdFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, fc3dChannel, LottoChartAssembler::assembleItem);
    }

    @Override
    @Transactional
    public SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel) {
        Fc3dChannel           fc3dChannel = Fc3dChannel.findOf(channel);
        List<BaseLottoCensus> censuses    = fsdFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, fc3dChannel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel) {
        Fc3dChannel fc3dChannel = Fc3dChannel.findOf(channel);
        return fsdFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, fc3dChannel, LottoChartAssembler::assembleFull);
    }

    @Override
    @Transactional
    public SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel) {
        Fc3dChannel           fc3dChannel = Fc3dChannel.findOf(channel);
        List<BaseLottoCensus> censuses    = fsdFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, fc3dChannel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel) {
        Fc3dChannel fc3dChannel = Fc3dChannel.findOf(channel);
        return fsdFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, fc3dChannel, LottoChartAssembler::assembleVip);
    }

    @Override
    @Transactional
    public NumberThreeCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId) {
        List<Fc3dLottoCensusPo> censuses = fsdHotOrRateChartBrowseExe.execute(userId, chartType);
        return LottoChartAssembler.assembleFsdChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<NumberThreeCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId) {
        return fsdHotOrRateChartBrowseExe.executeV1(userId, chartType);
    }

    @Override
    @Transactional
    public NumberThreeCensusVo getTypeLevelCensusDetail(Long userId, ChartType chartType, Integer level) {
        List<Fc3dLottoCensusPo> censuses = fsdTypeLevelChartBrowseExe.execute(userId, chartType, level);
        return LottoChartAssembler.assembleFsdChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<NumberThreeCensusVo> getTypeLevelCensusDetailV1(Long userId,
                                                                         ChartType chartType,
                                                                         Integer level) {
        return fsdTypeLevelChartBrowseExe.executeV1(userId, chartType, level);
    }

    @Override
    @Transactional
    public List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit) {
        return fsdCompareBrowseExe.execute(userId, Fc3dChannel.findOf(channel), limit);
    }

    @Override
    @Transactional
    public MasterBattleVo addMasterBattle(Long userId, String masterId) {
        return fsdForecastBattleExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public FeeDataResult<N3WarnRecommendVo> warnRecommend(Long userId, String period) {
        return recommendBrowseExe.execute(userId, period);
    }

    @Override
    public Com7CombineResult com7Combine(ComCombineCalcCmd cmd) {
        List<Pair<String, Long>> pairs = fc3dCom7CombineExe.execute(cmd);
        return new Com7CombineResult(pairs);
    }

    @Override
    public FeeDataResult<N3TodayPivotVo> todayPivot(Long userId, String period) {
        return todayPivotBrowseExe.execute(userId, period);
    }

    @Override
    public N3Comb5StatsVo statsComb5(N3DanFilterQuery query) {
        return fc3dComb5StatsExe.execute(query.getPeriod(), query.getContainZu3(), query.getDan(), query.getKills(), query.getKuaList(), query.getSumList());
    }

}
