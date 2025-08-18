package com.prize.lottery.domain.browse.repository;


import com.cloud.arch.enums.Value;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;

import java.util.List;
import java.util.function.BiPredicate;

public interface IBrowseForecastRepository<T, V extends Value<String>> {

    T getLottoForecast(String masterId, Period period);

    boolean isVipMaster(String masterId, String period);

    BiPredicate<String, String> vipPredicate();

    Period latestMasterPeriod(String masterId);

    Period latestICaiPeriod();

    Period latestChartPeriod(ChartType type);

    <R extends BaseLottoCensus> List<R> getTypLevelFullOrVipChart(ChartType type, Period period, Integer level);

    <R extends BaseLottoCensus> List<R> getForecastFullOrVipChart(ChartType type, Period period, V channel);

    <R extends BaseLottoCensus> List<R> getForecastHotOrRateChart(Period period, ChartType type);

    List<ICaiRankedDataVo> getForecastCompareData(Period period, V channel, Integer limit);

}
