package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.mapper.QlcMasterMapper;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.po.qlc.QlcLottoCensusPo;
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
public class QlcForecastRepository implements IBrowseForecastRepository<QlcIcaiPo, QlcChannel> {

    private final QlcIcaiMapper     qlcIcaiMapper;
    private final QlcMasterMapper   qlcMasterMapper;
    private final LotteryInfoMapper lotteryMapper;

    @Override
    public QlcIcaiPo getLottoForecast(String masterId, Period period) {
        QlcIcaiPo data = qlcIcaiMapper.getMasterQlcICaiDetail(masterId, period.getPeriod());
        return Assert.notNull(data, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public boolean isVipMaster(String masterId, String period) {
        return qlcMasterMapper.isQlcVipMaster(masterId, period) == 1;
    }

    @Override
    public BiPredicate<String, String> vipPredicate() {
        return (String masterId, String period) -> qlcMasterMapper.isQlcVipMaster(masterId, period) == 1;
    }

    private void determinePeriod(Period period) {
        if (period == null || period.isCalculated()) {
            return;
        }
        String latestPeriod = lotteryMapper.latestPeriod(LotteryEnum.QLC.getType());
        if (latestPeriod != null && latestPeriod.compareTo(period.getPeriod()) >= 0) {
            period.calculated();
        }
    }

    @Override
    public Period latestMasterPeriod(String masterId) {
        Period period = qlcIcaiMapper.latestQlcMasterPeriod(masterId);
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestICaiPeriod() {
        Period period = qlcIcaiMapper.latestQlcICaiPeriod();
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestChartPeriod(ChartType type) {
        String period = qlcIcaiMapper.latestQlcCensusPeriod(type.getType());
        return Optional.ofNullable(period).map(p -> {
            Period latestPeriod = new Period(p);
            this.determinePeriod(latestPeriod);
            return latestPeriod;
        }).orElse(null);
    }

    @Override
    public List<QlcLottoCensusPo> getForecastFullOrVipChart(ChartType type, Period period, QlcChannel channel) {
        Assert.state(type == ChartType.VIP_CHART
                             || type == ChartType.ALL_CHART
                             || type == ChartType.ITEM_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<QlcLottoCensusPo> censusList = qlcIcaiMapper.getChannelQlcCensusList(period.getPeriod(), type.getType(), channel.getChannel());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<QlcLottoCensusPo> getTypLevelFullOrVipChart(ChartType type, Period period, Integer level) {
        Assert.state(type == ChartType.VIP_CHART || type == ChartType.ALL_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<QlcLottoCensusPo> censusList = qlcIcaiMapper.getTypeLeveledCensusList(period.getPeriod(), type.getType(), level);
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<QlcLottoCensusPo> getForecastHotOrRateChart(Period period, ChartType type) {
        Assert.state(type == ChartType.HOT_CHART || type == ChartType.RATE_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<QlcLottoCensusPo> censusList = qlcIcaiMapper.getTypedQlcCensusList(period.getPeriod(), type.getType());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<ICaiRankedDataVo> getForecastCompareData(Period period, QlcChannel channel, Integer limit) {
        String current = period.getPeriod();
        String last    = LotteryEnum.QLC.lastPeriod(current);
        return qlcIcaiMapper.getQlcComparedDatas(current, last, channel.getChannel(), limit);
    }

}
