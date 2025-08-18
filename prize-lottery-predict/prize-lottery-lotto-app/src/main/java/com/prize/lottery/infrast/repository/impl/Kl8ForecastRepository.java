package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Kl8IcaiMapper;
import com.prize.lottery.mapper.Kl8MasterMapper;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.BiPredicate;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class Kl8ForecastRepository implements IBrowseForecastRepository<Kl8IcaiInfoPo, Kl8Channel> {

    private final Kl8IcaiMapper   kl8IcaiMapper;
    private final Kl8MasterMapper kl8MasterMapper;

    @Override
    public Kl8IcaiInfoPo getLottoForecast(String masterId, Period period) {
        Kl8IcaiInfoPo detail = kl8IcaiMapper.getMasterKl8IcaiDetail(masterId, period.getPeriod());
        return Assert.notNull(detail, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public boolean isVipMaster(String masterId, String period) {
        return false;
    }

    @Override
    public BiPredicate<String, String> vipPredicate() {
        return (String masterId, String period) -> true;
    }

    @Override
    public Period latestMasterPeriod(String masterId) {
        return kl8IcaiMapper.latestKl8MasterPeriod(masterId);
    }

    @Override
    public Period latestICaiPeriod() {
        return kl8IcaiMapper.latestKl8IcaiPeriod();
    }

    @Override
    public Period latestChartPeriod(ChartType type) {
        return null;
    }

    @Override
    public List<Kl8LottoCensusPo> getForecastFullOrVipChart(ChartType type, Period period, Kl8Channel channel) {
        Assert.state(type == ChartType.ALL_CHART, ResponseHandler.CHART_TYPE_ERROR);
        List<Kl8LottoCensusPo> censusList = kl8IcaiMapper.getTypedKl8CensusList(period.getPeriod(), type.getType());
        Assert.state(!CollectionUtils.isEmpty(censusList), ResponseHandler.CHART_NOT_MODIFIED);
        return censusList;
    }

    @Override
    public List<ICaiRankedDataVo> getForecastCompareData(Period period, Kl8Channel channel, Integer limit) {
        String current = period.getPeriod();
        String last    = LotteryEnum.KL8.lastPeriod(current);
        return kl8IcaiMapper.getKl8ComparedDatas(channel.getChannel(), current, last, limit);
    }

    @Override
    public List<Kl8LottoCensusPo> getForecastHotOrRateChart(Period period, ChartType type) {
        return null;
    }

    @Override
    public <R extends BaseLottoCensus> List<R> getTypLevelFullOrVipChart(ChartType type, Period period, Integer level) {
        return null;
    }
}
