package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.executor.lotto.ssq.*;
import com.prize.lottery.application.command.service.ISsqLottoCommandService;
import com.prize.lottery.application.vo.SsqForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.po.ssq.SsqLottoCensusPo;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.ssq.SsqChartCensusVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SsqLottoCommandService implements ISsqLottoCommandService {

    @Resource
    private SsqCompareBrowseExe        ssqCompareBrowseExe;
    @Resource
    private SsqForecastBrowseExe       ssqForecastBrowseExe;
    @Resource
    private SsqFullOrVipChartBrowseExe ssqFullOrVipChartBrowseExe;
    @Resource
    private SsqHotOrRateChartBrowseExe ssqHotOrRateChartBrowseExe;
    @Resource
    private SsqTypeLevelChartBrowseExe ssqTypeLevelChartBrowseExe;
    @Resource
    private SsqForecastBattleExe       ssqForecastBattleExe;

    @Override
    @Transactional
    public SsqForecastVo lookupForecast(Long userId, String masterId) {
        return ssqForecastBrowseExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel) {
        SsqChannel            ssqChannel = SsqChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = ssqFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, ssqChannel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel) {
        SsqChannel ssqChannel = SsqChannel.findOf(channel);
        return ssqFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, ssqChannel, LottoChartAssembler::assembleItem);
    }

    @Override
    @Transactional
    public SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel) {
        SsqChannel            ssqChannel = SsqChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = ssqFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, ssqChannel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel) {
        SsqChannel ssqChannel = SsqChannel.findOf(channel);
        return ssqFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, ssqChannel, LottoChartAssembler::assembleFull);
    }

    @Override
    @Transactional
    public SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel) {
        SsqChannel            ssqChannel = SsqChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = ssqFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, ssqChannel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel) {
        SsqChannel ssqChannel = SsqChannel.findOf(channel);
        return ssqFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, ssqChannel, LottoChartAssembler::assembleVip);
    }

    @Override
    @Transactional
    public SsqChartCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId) {
        List<SsqLottoCensusPo> censuses = ssqHotOrRateChartBrowseExe.execute(userId, chartType);
        return LottoChartAssembler.assembleSsqChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SsqChartCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId) {
        return ssqHotOrRateChartBrowseExe.executeV1(userId, chartType);
    }

    @Override
    @Transactional
    public SsqChartCensusVo getTypeLevelCensusDetail(Long userId, ChartType type, Integer level) {
        List<SsqLottoCensusPo> censuses = ssqTypeLevelChartBrowseExe.execute(userId, type, level);
        return LottoChartAssembler.assembleSsqChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SsqChartCensusVo> getTypeLevelCensusDetailV1(Long userId, ChartType type, Integer level) {
        return ssqTypeLevelChartBrowseExe.executeV1(userId, type, level);
    }

    @Override
    @Transactional
    public List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit) {
        return ssqCompareBrowseExe.execute(userId, SsqChannel.findOf(channel), limit);
    }

    @Override
    @Transactional
    public MasterBattleVo addMasterBattle(Long userId, String masterId) {
        return ssqForecastBattleExe.execute(userId, masterId);
    }

}
