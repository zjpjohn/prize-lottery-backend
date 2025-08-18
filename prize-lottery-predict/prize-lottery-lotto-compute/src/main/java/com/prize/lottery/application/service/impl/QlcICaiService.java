package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.service.IQlcICaiService;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.mapper.QlcMasterMapper;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class QlcICaiService implements IQlcICaiService {

    private final static Integer QLC_RANGE = 2800;

    private final QlcIcaiMapper       qlcIcaiMapper;
    private final QlcMasterMapper     qlcMasterMapper;
    private final LotteryInfoMapper   lotteryInfoMapper;
    private final ICaiDomainAbility   qlcICaiDomainAbility;
    @Qualifier(OpenForecastConfigurer.QLC_FETCHER)
    private final OpenForecastFetcher qlcForecastFetcher;

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    @Override
    public void fetchForecast(String period) {
        int exist = qlcMasterMapper.hasExistRatePeriod(period);
        Assert.state(exist == 0, String.format("%s期数据已存在", period));
        qlcForecastFetcher.fetchDelay(period, 4600);
    }

    @Override
    public void fetchHistoryForecast() {
        //最新开奖数据，一定要先抓取开奖数据
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.QLC.getType());
        Assert.notNull(lottoPeriod, ResponseHandler.NO_OPEN_LOTTERY);
        //最新预测数据
        Period latest = qlcIcaiMapper.latestQlcICaiPeriod();
        //开始抓取期号
        String begin = Optional.ofNullable(latest)
                               .map(period -> PeriodCalculator.qlcAddPeriod(period.getPeriod(), 1))
                               .orElseGet(() -> PeriodCalculator.qlcPeriod(lottoPeriod, 30));
        //开始抓取期号不小于最新开奖期号
        Assert.state(lottoPeriod.compareTo(begin) >= 0, ResponseHandler.NO_FETCH_HISTORY);
        //每次请求最多抓取15期，1小时抓取1期全部数据
        final long current = System.currentTimeMillis();
        IntStream.range(0, 12)
                 .mapToObj(i -> Pair.of(PeriodCalculator.qlcAddPeriod(begin, i), current + i * QLC_RANGE * 1000L))
                 .filter(e -> e.getKey().compareTo(lottoPeriod) <= 0)
                 .forEach(e -> qlcForecastFetcher.fetchAsync(e.getKey(), e.getValue(), QLC_RANGE));
    }

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    @Override
    public void fetchLastForecast(String before, Integer size) {
        Period latest = qlcIcaiMapper.latestQlcICaiPeriod();
        int    newest = Optional.ofNullable(latest).map(v -> Integer.valueOf(v.getPeriod())).orElse(0);
        Assert.state(newest < Integer.parseInt(before), "历史数据已存在");
        long millis = System.currentTimeMillis();
        IntStream.range(0, size)
                 .mapToObj(i -> Pair.of(PeriodCalculator.qlcPeriod(before, i), millis + i * 900L * 1000))
                 .filter(e -> Integer.parseInt(e.getLeft()) > newest)
                 .forEach(e -> qlcForecastFetcher.fetchAsync(e.getLeft(), e.getRight(), 900));
    }

    /**
     * 初始化计算历史数据命中
     */
    @Override
    public void batchCalcQlcHit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.QLC.getType());
        Assert.state(StringUtils.isNotBlank(latest), "没有开奖数据");
        List<String> periods = qlcIcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(qlcICaiDomainAbility::calcForecastHit);
    }

    /**
     * 初始化计算专家历史命中率
     */
    @Override
    public void initCalcMasterRate() {
        List<String> periods = qlcIcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(qlcICaiDomainAbility::calcMasterRate);
    }

    /**
     * 初始化计算历史专家排名
     */
    @Override
    public void initCalcMasterRank() {
        List<String> periods = qlcMasterMapper.getUnRankedMasterPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(qlcICaiDomainAbility::calcMasterRank);
    }

    @Override
    public void fetchIncrHistory() {
        Period latest = qlcIcaiMapper.latestQlcICaiPeriod();
        Assert.notNull(latest, ResponseHandler.NO_FORECAST_DATA);

        String begin   = PeriodCalculator.dltPeriod(latest.getPeriod(), 30);
        long   current = System.currentTimeMillis();
        IntStream.range(0, 31)
                 .mapToObj(i -> Pair.of(PeriodCalculator.dltAddPeriod(begin, 1), current + i * 1000 * 1000L))
                 .forEach(e -> qlcForecastFetcher.incrFetchAsync(e.getKey(), e.getValue(), 1000));
    }

    @Override
    public void calcIncrAllHit() {
        List<String> periods = qlcIcaiMapper.getIncrUnCalculatedPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(qlcICaiDomainAbility::calcIncrHit);
    }

    @Override
    public void incrCalcMasterRate() {
        List<String> periods = qlcIcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(qlcICaiDomainAbility::calcIncrMasterRate);
    }

}
