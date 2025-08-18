package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
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
public class FsdForecastRepository implements IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> {

    private final Fc3dIcaiMapper    fc3dIcaiMapper;
    private final Fc3dMasterMapper  fc3dMasterMapper;
    private final LotteryInfoMapper lotteryMapper;

    @Override
    public Fc3dIcaiPo getLottoForecast(String masterId, Period period) {
        Fc3dIcaiPo data = fc3dIcaiMapper.getMasterFc3dICaiDetail(masterId, period.getPeriod());
        return Assert.notNull(data, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public boolean isVipMaster(String masterId, String period) {
        return fc3dMasterMapper.isFc3dVipMaster(masterId, period) == 1;
    }

    @Override
    public BiPredicate<String, String> vipPredicate() {
        return (String masterId, String period) -> fc3dMasterMapper.isFc3dVipMaster(masterId, period) == 1;
    }

    private void determinePeriod(Period period) {
        if (period == null || period.isCalculated()) {
            return;
        }
        String latestPeriod = lotteryMapper.latestPeriod(LotteryEnum.FC3D.getType());
        if (latestPeriod != null && latestPeriod.compareTo(period.getPeriod()) >= 0) {
            period.calculated();
        }
    }

    @Override
    public Period latestMasterPeriod(String masterId) {
        Period period = fc3dIcaiMapper.latestMasterFc3dPeriod(masterId);
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestICaiPeriod() {
        Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        this.determinePeriod(period);
        return period;
    }

    @Override
    public Period latestChartPeriod(ChartType type) {
        String period = fc3dIcaiMapper.latestFc3dCensusPeriod(type.getType());
        return Optional.ofNullable(period).map(p -> {
            Period latestPeriod = new Period(p);
            this.determinePeriod(latestPeriod);
            return latestPeriod;
        }).orElse(null);
    }

    @Override
    public List<Fc3dLottoCensusPo> getTypLevelFullOrVipChart(ChartType type, Period period, Integer level) {
        Assert.state(type == ChartType.VIP_CHART || type == ChartType.ALL_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Fc3dLottoCensusPo> censusList = fc3dIcaiMapper.getTypeLeveledCensusList(period.getPeriod(), type.getType(), level);
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<Fc3dLottoCensusPo> getForecastFullOrVipChart(ChartType type, Period period, Fc3dChannel channel) {
        Assert.state(type == ChartType.VIP_CHART
                             || type == ChartType.ALL_CHART
                             || type == ChartType.ITEM_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Fc3dLottoCensusPo> censusList = fc3dIcaiMapper.getChannelFc3dCensusList(period.getPeriod(), type.getType(), channel.getChannel());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<Fc3dLottoCensusPo> getForecastHotOrRateChart(Period period, ChartType type) {
        Assert.state(type == ChartType.HOT_CHART || type == ChartType.RATE_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Fc3dLottoCensusPo> censusList = fc3dIcaiMapper.getTypedFc3dCensusList(period.getPeriod(), type.getType());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<ICaiRankedDataVo> getForecastCompareData(Period period, Fc3dChannel channel, Integer limit) {
        String current = period.getPeriod();
        String last    = LotteryEnum.FC3D.lastPeriod(current);
        return fc3dIcaiMapper.getFc3dComparedDatas(channel.getChannel(), current, last, limit);
    }

}
