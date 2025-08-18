package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.service.IDltICaiService;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
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
public class DltICaiService implements IDltICaiService {

    private final static Integer DLT_RANGE = 3000;

    private final DltIcaiMapper       dltIcaiMapper;
    private final DltMasterMapper     dltMasterMapper;
    private final LotteryInfoMapper   lotteryInfoMapper;
    private final ICaiDomainAbility   dltICaiDomainAbility;
    @Qualifier(OpenForecastConfigurer.DLT_FETCHER)
    private final OpenForecastFetcher dltForecastFetcher;

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    @Override
    public void fetchForecast(String period) {
        int exist = dltMasterMapper.hasExistRatePeriod(period);
        Assert.state(exist == 0, String.format("%s期数据已存在", period));
        dltForecastFetcher.fetchDelay(period, 9000);
    }

    @Override
    public void fetchHistoryForecast() {
        //最新开奖数据，一定要先抓取开奖数据
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.DLT.getType());
        Assert.notNull(lottoPeriod, ResponseHandler.NO_OPEN_LOTTERY);
        //最新预测数据
        Period latest = dltIcaiMapper.latestDltICaiPeriod();
        //开始抓取期号
        String begin = Optional.ofNullable(latest)
                               .map(period -> PeriodCalculator.dltAddPeriod(period.getPeriod(), 1))
                               .orElseGet(() -> PeriodCalculator.dltPeriod(lottoPeriod, 30));
        //开始抓取期号不小于最新开奖期号
        Assert.state(lottoPeriod.compareTo(begin) >= 0, ResponseHandler.NO_FETCH_HISTORY);
        //每次请求最多抓取10期，1小时抓取1期全部数据
        final long current = System.currentTimeMillis();
        IntStream.range(0, 11)
                 .mapToObj(i -> Pair.of(PeriodCalculator.dltAddPeriod(begin, i), current + i * DLT_RANGE * 1000L))
                 .filter(e -> e.getKey().compareTo(lottoPeriod) <= 0)
                 .forEach(e -> dltForecastFetcher.fetchAsync(e.getKey(), e.getValue(), DLT_RANGE));
    }

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    @Override
    public void fetchLastForecast(String before, Integer size) {
        Period latest = dltIcaiMapper.latestDltICaiPeriod();
        int    newest = Optional.ofNullable(latest).map(v -> Integer.valueOf(v.getPeriod())).orElse(0);
        Assert.state(newest < Integer.parseInt(before), ResponseHandler.HAS_EXIST_HISTORY);
        long millis = System.currentTimeMillis();
        IntStream.range(0, size)
                 .mapToObj(i -> Pair.of(PeriodCalculator.dltPeriod(before, i), millis + i * 900L * 1000))
                 .filter(e -> Integer.parseInt(e.getLeft()) > newest)
                 .forEach(e -> dltForecastFetcher.fetchAsync(e.getLeft(), e.getRight(), 900));
    }

    /**
     * 初始化计算历史数据命中
     */
    @Override
    public void batchCalcDltHit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.DLT.getType());
        Assert.state(StringUtils.isNotBlank(latest), ResponseHandler.NO_OPEN_LOTTERY);
        List<String> periods = dltIcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(dltICaiDomainAbility::calcForecastHit);
    }

    /**
     * 初始化计算专家历史命中率
     */
    @Override
    public void initCalcMasterRate() {
        List<String> periods = dltIcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(dltICaiDomainAbility::calcMasterRate);
    }

    /**
     * 初始化计算历史专家排名
     */
    @Override
    public void initCalcMasterRank() {
        List<String> periods = dltMasterMapper.getUnRankedMasterPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(dltICaiDomainAbility::calcMasterRank);
    }

    @Override
    public void fetchIncrHistory() {
        Period latest = dltIcaiMapper.latestDltICaiPeriod();
        Assert.notNull(latest, ResponseHandler.NO_FORECAST_DATA);

        String begin   = PeriodCalculator.dltPeriod(latest.getPeriod(), 30);
        long   current = System.currentTimeMillis();
        IntStream.range(0, 31)
                 .mapToObj(i -> Pair.of(PeriodCalculator.dltAddPeriod(begin, 1), current + i * 1000 * 1000L))
                 .forEach(e -> dltForecastFetcher.incrFetchAsync(e.getKey(), e.getValue(), 1000));
    }

    @Override
    public void calcIncrAllHit() {
        List<String> periods = dltIcaiMapper.getIncrUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(dltICaiDomainAbility::calcIncrHit);
    }

    @Override
    public void incrCalcMasterRate() {
        List<String> periods = dltIcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(dltICaiDomainAbility::calcIncrMasterRate);
    }

}
