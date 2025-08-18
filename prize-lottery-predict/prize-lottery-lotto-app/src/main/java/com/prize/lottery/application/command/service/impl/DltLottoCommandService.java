package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.executor.lotto.dlt.*;
import com.prize.lottery.application.command.service.IDltLottoCommandService;
import com.prize.lottery.application.vo.DltForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.dlt.DltChartCensusVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DltLottoCommandService implements IDltLottoCommandService {

    @Resource
    private DltCompareBrowseExe        dltCompareBrowseExe;
    @Resource
    private DltForecastBrowseExe       dltForecastBrowseExe;
    @Resource
    private DltFullOrVipChartBrowseExe dltFullOrVipChartBrowseExe;
    @Resource
    private DltHotOrRateChartBrowseExe dltHotOrRateChartBrowseExe;
    @Resource
    private DltTypeLevelChartBrowseExe dltTypeLevelChartBrowseExe;
    @Resource
    private DltForecastBattleExe       dltForecastBattleExe;

    @Override
    @Transactional
    public DltForecastVo lookupForecast(Long userId, String masterId) {
        return dltForecastBrowseExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel) {
        DltChannel            dltChannel = DltChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = dltFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, dltChannel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel) {
        DltChannel dltChannel = DltChannel.findOf(channel);
        return dltFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, dltChannel, LottoChartAssembler::assembleItem);
    }

    @Override
    @Transactional
    public SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel) {
        DltChannel            dltChannel = DltChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = dltFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, dltChannel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel) {
        DltChannel dltChannel = DltChannel.findOf(channel);
        return dltFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, dltChannel, LottoChartAssembler::assembleFull);
    }

    @Override
    @Transactional
    public SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel) {
        DltChannel            dltChannel = DltChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = dltFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, dltChannel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel) {
        DltChannel dltChannel = DltChannel.findOf(channel);
        return dltFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, dltChannel, LottoChartAssembler::assembleVip);
    }

    @Override
    @Transactional
    public DltChartCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId) {
        List<DltLottoCensusPo> censuses = dltHotOrRateChartBrowseExe.execute(userId, chartType);
        return LottoChartAssembler.assembleDltChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<DltChartCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId) {
        return dltHotOrRateChartBrowseExe.executeV1(userId, chartType);
    }

    @Override
    @Transactional
    public DltChartCensusVo getTypeLevelCensusDetail(Long userId, ChartType chartType, Integer level) {
        List<DltLottoCensusPo> censuses = dltTypeLevelChartBrowseExe.execute(userId, chartType, level);
        return LottoChartAssembler.assembleDltChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<DltChartCensusVo> getTypeLevelCensusDetailV1(Long userId, ChartType chartType, Integer level) {
        return dltTypeLevelChartBrowseExe.executeV1(userId, chartType, level);
    }

    @Override
    @Transactional
    public List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit) {
        return dltCompareBrowseExe.execute(userId, DltChannel.findOf(channel), limit);
    }

    @Override
    @Transactional
    public MasterBattleVo addMasterBattle(Long userId, String masterId) {
        return dltForecastBattleExe.execute(userId, masterId);
    }

}
