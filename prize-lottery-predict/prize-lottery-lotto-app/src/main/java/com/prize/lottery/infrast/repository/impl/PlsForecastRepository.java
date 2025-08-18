package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class PlsForecastRepository implements IBrowseForecastRepository<Pl3IcaiPo, Pl3Channel> {

    private final Pl3IcaiMapper     pl3IcaiMapper;
    private final Pl3MasterMapper   pl3MasterMapper;
    private final LotteryInfoMapper lotteryMapper;

    @Override
    public Pl3IcaiPo getLottoForecast(String masterId, Period period) {
        Pl3IcaiPo data = pl3IcaiMapper.getMasterPl3ICaiDetail(masterId, period.getPeriod());
        return Assert.notNull(data, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public boolean isVipMaster(String masterId, String period) {
        return pl3MasterMapper.isPl3VipMaster(masterId, period) == 1;
    }

    @Override
    public BiPredicate<String, String> vipPredicate() {
        return (String masterId, String period) -> pl3MasterMapper.isPl3VipMaster(masterId, period) == 1;
    }

    private void determinePeriod(Period period) {
        if (period == null || period.isCalculated()) {
            return;
        }
        String latestPeriod = lotteryMapper.latestPeriod(LotteryEnum.PL3.getType());
        if (latestPeriod != null && latestPeriod.compareTo(period.getPeriod()) >= 0) {
            period.calculated();
        }
    }

    @Override
    public Period latestMasterPeriod(String masterId) {
        Period period = pl3IcaiMapper.latestMasterPl3Period(masterId);
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestICaiPeriod() {
        Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestChartPeriod(ChartType type) {
        String period = pl3IcaiMapper.latestPl3CensusPeriod(type.getType());
        return Optional.ofNullable(period).map(p -> {
            Period latestPeriod = new Period(p);
            this.determinePeriod(latestPeriod);
            return latestPeriod;
        }).orElse(null);
    }

    @Override
    public List<Pl3LottoCensusPo> getForecastFullOrVipChart(ChartType type, Period period, Pl3Channel channel) {
        Assert.state(type == ChartType.VIP_CHART
                             || type == ChartType.ALL_CHART
                             || type == ChartType.ITEM_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Pl3LottoCensusPo> censusList = pl3IcaiMapper.getChannelPl3CensusList(period.getPeriod(), type.getType(), channel.getChannel());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<Pl3LottoCensusPo> getTypLevelFullOrVipChart(ChartType type, Period period, Integer level) {
        Assert.state(type == ChartType.VIP_CHART || type == ChartType.ALL_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Pl3LottoCensusPo> censusList = pl3IcaiMapper.getTypeLeveledCensusList(period.getPeriod(), type.getType(), level);
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<Pl3LottoCensusPo> getForecastHotOrRateChart(Period period, ChartType type) {
        Assert.state(type == ChartType.HOT_CHART || type == ChartType.RATE_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Pl3LottoCensusPo> censusList = pl3IcaiMapper.getTypedPl3CensusList(period.getPeriod(), type.getType());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<ICaiRankedDataVo> getForecastCompareData(Period period, Pl3Channel channel, Integer limit) {
        String current = period.getPeriod();
        String last    = LotteryEnum.PL3.lastPeriod(current);
        return pl3IcaiMapper.getPl3ComparedDatas(channel.getChannel(), current, last, limit);
    }

}
