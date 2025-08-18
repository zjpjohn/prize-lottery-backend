package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import com.prize.lottery.application.command.executor.lotto.pls.*;
import com.prize.lottery.application.command.service.IPlsLottoCommandService;
import com.prize.lottery.application.query.dto.N3DanFilterQuery;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlsLottoCommandService implements IPlsLottoCommandService {

    @Resource
    private PlsCompareBrowseExe          plsCompareBrowseExe;
    @Resource
    private PlsForecastBrowseExe         plsForecastBrowseExe;
    @Resource
    private PlsFullOrVipChartBrowseExe   plsFullOrVipChartBrowseExe;
    @Resource
    private PlsHotOrRateChartBrowseExe   plsHotOrRateChartBrowseExe;
    @Resource
    private PlsTypeLeveledChartBrowseExe plsTypeLeveledChartBrowseExe;
    @Resource
    private PlsForecastBattleExe         plsForecastBattleExe;
    @Resource
    private PlsWarnRecommendBrowseExe    recommendBrowseExe;
    @Resource
    private Pl3Com7CombineExe            pl3Com7CombineExe;
    @Resource
    private PlsTodayPivotBrowseExe       todayPivotBrowseExe;
    @Resource
    private Pl3Comb5StatsExe             pl3Comb5StatsExe;

    @Override
    @Transactional
    public Pl3ForecastVo lookupForecast(Long userId, String masterId) {
        return plsForecastBrowseExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel) {
        Pl3Channel            pl3Channel = Pl3Channel.findOf(channel);
        List<BaseLottoCensus> censuses   = plsFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, pl3Channel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel) {
        Pl3Channel pl3Channel = Pl3Channel.findOf(channel);
        return plsFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, pl3Channel, LottoChartAssembler::assembleItem);
    }

    @Override
    @Transactional
    public SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel) {
        Pl3Channel            pl3Channel = Pl3Channel.findOf(channel);
        List<BaseLottoCensus> censuses   = plsFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, pl3Channel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel) {
        Pl3Channel pl3Channel = Pl3Channel.findOf(channel);
        return plsFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, pl3Channel, LottoChartAssembler::assembleFull);
    }

    @Override
    @Transactional
    public SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel) {
        Pl3Channel            pl3Channel = Pl3Channel.findOf(channel);
        List<BaseLottoCensus> censuses   = plsFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, pl3Channel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel) {
        Pl3Channel pl3Channel = Pl3Channel.findOf(channel);
        return plsFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, pl3Channel, LottoChartAssembler::assembleVip);
    }

    @Override
    @Transactional
    public NumberThreeCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId) {
        List<Pl3LottoCensusPo> censuses = plsHotOrRateChartBrowseExe.execute(userId, chartType);
        return LottoChartAssembler.assemblePlsChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<NumberThreeCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId) {
        return plsHotOrRateChartBrowseExe.executeV1(userId, chartType);
    }

    @Override
    @Transactional
    public NumberThreeCensusVo getTypeLeveledCensusDetail(Long userId, ChartType type, Integer level) {
        List<Pl3LottoCensusPo> censuses = plsTypeLeveledChartBrowseExe.execute(userId, type, level);
        return LottoChartAssembler.assemblePlsChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<NumberThreeCensusVo> getTypeLeveledCensusDetailV1(Long userId, ChartType type, Integer level) {
        return plsTypeLeveledChartBrowseExe.executeV1(userId, type, level);
    }

    @Override
    @Transactional
    public List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit) {
        return plsCompareBrowseExe.execute(userId, Pl3Channel.findOf(channel), limit);
    }

    @Override
    @Transactional
    public MasterBattleVo addMasterBattle(Long userId, String masterId) {
        return plsForecastBattleExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public FeeDataResult<N3WarnRecommendVo> warnRecommend(Long userId, String period) {
        return recommendBrowseExe.execute(userId, period);
    }

    @Override
    public Com7CombineResult com7Combine(ComCombineCalcCmd cmd) {
        List<Pair<String, Long>> pairs = pl3Com7CombineExe.execute(cmd);
        return new Com7CombineResult(pairs);
    }

    @Override
    public FeeDataResult<N3TodayPivotVo> todayPivot(Long userId, String period) {
        return todayPivotBrowseExe.execute(userId, period);
    }

    @Override
    public N3Comb5StatsVo comb5Stats(N3DanFilterQuery query) {
        return pl3Comb5StatsExe.execute(query.getPeriod(), query.getContainZu3(), query.getDan(), query.getKills(), query.getKuaList(), query.getSumList());
    }

}
