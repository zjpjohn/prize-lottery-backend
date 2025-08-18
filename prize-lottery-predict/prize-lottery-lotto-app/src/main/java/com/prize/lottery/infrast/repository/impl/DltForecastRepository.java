package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class DltForecastRepository implements IBrowseForecastRepository<DltIcaiPo, DltChannel> {

    private final DltIcaiMapper     dltIcaiMapper;
    private final DltMasterMapper   dltMasterMapper;
    private final LotteryInfoMapper lotteryMapper;

    @Override
    public DltIcaiPo getLottoForecast(String masterId, Period period) {
        DltIcaiPo data = dltIcaiMapper.getMasterDltICaiDetail(masterId, period.getPeriod());
        return Assert.notNull(data, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public boolean isVipMaster(String masterId, String period) {
        return dltMasterMapper.isDltVipMaster(masterId, period) == 1;
    }

    @Override
    public BiPredicate<String, String> vipPredicate() {
        return (String masterId, String period) -> dltMasterMapper.isDltVipMaster(masterId, period) == 1;
    }

    private void determinePeriod(Period period) {
        if (period == null || period.isCalculated()) {
            return;
        }
        String latestPeriod = lotteryMapper.latestPeriod(LotteryEnum.DLT.getType());
        if (latestPeriod != null && latestPeriod.compareTo(period.getPeriod()) >= 0) {
            period.calculated();
        }
    }

    @Override
    public Period latestMasterPeriod(String masterId) {
        Period period = dltIcaiMapper.latestDltMasterPeriod(masterId);
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestICaiPeriod() {
        Period period = dltIcaiMapper.latestDltICaiPeriod();
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestChartPeriod(ChartType type) {
        String period = dltIcaiMapper.latestDltCensusPeriod(type.getType());
        return Optional.ofNullable(period).map(p -> {
            Period latestPeriod = new Period(p);
            this.determinePeriod(latestPeriod);
            return latestPeriod;
        }).orElse(null);
    }

    @Override
    public List<DltLottoCensusPo> getForecastFullOrVipChart(ChartType type, Period period, DltChannel channel) {
        Assert.state(type == ChartType.VIP_CHART
                             || type == ChartType.ALL_CHART
                             || type == ChartType.ITEM_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<DltLottoCensusPo> censusList = dltIcaiMapper.getChannelDltCensusList(period.getPeriod(), type.getType(), channel.getChannel());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<DltLottoCensusPo> getForecastHotOrRateChart(Period period, ChartType type) {
        Assert.state(type == ChartType.HOT_CHART || type == ChartType.RATE_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<DltLottoCensusPo> censusList = dltIcaiMapper.getTypedDltCensusList(period.getPeriod(), type.getType());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<DltLottoCensusPo> getTypLevelFullOrVipChart(ChartType type, Period period, Integer level) {
        Assert.state(type == ChartType.VIP_CHART || type == ChartType.ALL_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<DltLottoCensusPo> censusList = dltIcaiMapper.getTypeLeveledCensusList(period.getPeriod(), type.getType(), level);
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<ICaiRankedDataVo> getForecastCompareData(Period period, DltChannel channel, Integer limit) {
        String current = period.getPeriod();
        String last    = LotteryEnum.DLT.lastPeriod(current);
        return dltIcaiMapper.getDltComparedDatas(channel.getChannel(), current, last, limit);
    }

}
