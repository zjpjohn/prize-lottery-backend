package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.executor.lotto.qlc.*;
import com.prize.lottery.application.command.service.IQlcLottoCommandService;
import com.prize.lottery.application.vo.QlcForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.po.qlc.QlcLottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.qlc.QlcChartCensusVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QlcLottoCommandService implements IQlcLottoCommandService {

    @Resource
    private QlcCompareBrowseExe        qlcCompareBrowseExe;
    @Resource
    private QlcForecastBrowseExe       qlcForecastBrowseExe;
    @Resource
    private QlcFullOrVipChartBrowseExe qlcFullOrVipChartBrowseExe;
    @Resource
    private QlcHotOrRateChartBrowseExe qlcHotOrRateChartBrowseExe;
    @Resource
    private QlcTypeLevelChartBrowseExe qlcTypeLevelChartBrowseExe;
    @Resource
    private QlcForecastBattleExe       qlcForecastBattleExe;

    @Override
    @Transactional
    public QlcForecastVo lookupForecast(Long userId, String masterId) {
        return qlcForecastBrowseExe.execute(userId, masterId);
    }

    @Override
    @Transactional
    public SyntheticItemCensusVo getItemCensusDetail(Long userId, String channel) {
        QlcChannel            qlcChannel = QlcChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = qlcFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, qlcChannel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticItemCensusVo> getItemCensusDetailV1(Long userId, String channel) {
        QlcChannel qlcChannel = QlcChannel.findOf(channel);
        return qlcFullOrVipChartBrowseExe.execute(userId, ChartType.ITEM_CHART, qlcChannel, LottoChartAssembler::assembleItem);
    }

    @Override
    @Transactional
    public SyntheticFullCensusVo getFullCensusDetail(Long userId, String channel) {
        QlcChannel            qlcChannel = QlcChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = qlcFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, qlcChannel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticFullCensusVo> getFullCensusDetailV1(Long userId, String channel) {
        QlcChannel qlcChannel = QlcChannel.findOf(channel);
        return qlcFullOrVipChartBrowseExe.execute(userId, ChartType.ALL_CHART, qlcChannel, LottoChartAssembler::assembleFull);
    }

    @Override
    @Transactional
    public SyntheticVipCensusVo getVipCensusDetail(Long userId, String channel) {
        QlcChannel            qlcChannel = QlcChannel.findOf(channel);
        List<BaseLottoCensus> censuses   = qlcFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, qlcChannel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<SyntheticVipCensusVo> getVipCensusDetailV1(Long userId, String channel) {
        QlcChannel qlcChannel = QlcChannel.findOf(channel);
        return qlcFullOrVipChartBrowseExe.execute(userId, ChartType.VIP_CHART, qlcChannel, LottoChartAssembler::assembleVip);
    }

    @Override
    @Transactional
    public QlcChartCensusVo getHotOrRateCensusDetail(ChartType chartType, Long userId) {
        List<QlcLottoCensusPo> censuses = qlcHotOrRateChartBrowseExe.execute(userId, chartType);
        return LottoChartAssembler.assembleQlcChart(censuses);
    }

    @Override
    @Transactional
    public FeeDataResult<QlcChartCensusVo> getHotOrRateCensusDetailV1(ChartType chartType, Long userId) {
        return qlcHotOrRateChartBrowseExe.executeV1(userId, chartType);
    }

    @Override
    @Transactional
    public FeeDataResult<QlcChartCensusVo> getTypeLevelCensusDetailV1(Long userId, ChartType chartType, Integer level) {
        return qlcTypeLevelChartBrowseExe.executeV1(userId, chartType, level);
    }

    @Override
    @Transactional
    public QlcChartCensusVo getTypeLevelCensusDetail(Long userId, ChartType chartType, Integer level) {
        List<QlcLottoCensusPo> censuses = qlcTypeLevelChartBrowseExe.execute(userId, chartType, level);
        return LottoChartAssembler.assembleQlcChart(censuses);
    }

    @Override
    @Transactional
    public List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, String channel, Integer limit) {
        return qlcCompareBrowseExe.execute(userId, QlcChannel.findOf(channel), limit);
    }

    @Override
    @Transactional
    public MasterBattleVo addMasterBattle(Long userId, String masterId) {
        return qlcForecastBattleExe.execute(userId, masterId);
    }
}
