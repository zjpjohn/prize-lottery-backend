package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.service.ISsqICaiService;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.mapper.SsqMasterMapper;
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
public class SsqICaiService implements ISsqICaiService {

    private static final Integer SSQ_RANGE = 3000;

    private final SsqIcaiMapper       ssqIcaiMapper;
    private final SsqMasterMapper     ssqMasterMapper;
    private final LotteryInfoMapper   lotteryInfoMapper;
    private final ICaiDomainAbility   ssqICaiDomainAbility;
    @Qualifier(OpenForecastConfigurer.SSQ_FETCHER)
    private final OpenForecastFetcher ssqForecastFetcher;

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    @Override
    public void fetchForecast(String period) {
        int existed = ssqMasterMapper.hasExistRatePeriod(period);
        Assert.state(existed == 0, String.format("%s期数据已存在", period));
        ssqForecastFetcher.fetchDelay(period, 3000);
    }

    @Override
    public void fetchHistoryForecast() {
        //最新开奖数据，一定要先抓取开奖数据
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.SSQ.getType());
        Assert.notNull(lottoPeriod, ResponseHandler.NO_OPEN_LOTTERY);
        //最新预测数据
        Period latest = ssqIcaiMapper.latestSsqICaiPeriod();
        //计算开始抓取期号
        String begin = Optional.ofNullable(latest)
                               .map(period -> PeriodCalculator.ssqAddPeriod(period.getPeriod(), 1))
                               .orElseGet(() -> PeriodCalculator.ssqPeriod(lottoPeriod, 30));
        //开始抓取期号不小于最新开奖期号
        Assert.state(lottoPeriod.compareTo(begin) >= 0, ResponseHandler.NO_FETCH_HISTORY);
        //每次请求最多抓取9期，1小时抓取1期全部数据
        final long current = System.currentTimeMillis();
        IntStream.range(0, 11)
                 .mapToObj(i -> Pair.of(PeriodCalculator.ssqAddPeriod(begin, i), current + i * SSQ_RANGE * 1000L))
                 .filter(e -> e.getKey().compareTo(lottoPeriod) <= 0)
                 .forEach(e -> ssqForecastFetcher.fetchAsync(e.getKey(), e.getValue(), SSQ_RANGE));
    }

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    @Override
    public void fetchLastForecast(String before, Integer size) {
        Period latest = ssqIcaiMapper.latestSsqICaiPeriod();
        int    newest = Optional.ofNullable(latest).map(v -> Integer.valueOf(v.getPeriod())).orElse(0);
        Assert.state(newest < Integer.parseInt(before), "历史数据已存在");
        long millis = System.currentTimeMillis();
        IntStream.range(0, size)
                 .mapToObj(i -> Pair.of(PeriodCalculator.ssqPeriod(before, i), millis + i * 900L * 1000))
                 .filter(e -> Integer.parseInt(e.getLeft()) > newest)
                 .forEach(e -> ssqForecastFetcher.fetchAsync(e.getLeft(), e.getRight(), 900));
    }

    /**
     * 初始化计算历史数据命中
     */
    @Override
    public void batchCalcSsqHit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.SSQ.getType());
        Assert.state(StringUtils.isNotBlank(latest), "没有开奖数据");
        List<String> periods = ssqIcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(ssqICaiDomainAbility::calcForecastHit);
    }

    /**
     * 初始化计算专家历史命中率
     */
    @Override
    public void initCalcMasterRate() {
        List<String> periods = ssqIcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(ssqICaiDomainAbility::calcMasterRate);
    }

    /**
     * 初始化计算历史专家排名
     */
    @Override
    public void initCalcMasterRank() {
        List<String> periods = ssqMasterMapper.getUnRankedMasterPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(ssqICaiDomainAbility::calcMasterRank);
    }

    @Override
    public void fetchIncrHistory() {
        Period latest = ssqIcaiMapper.latestSsqICaiPeriod();
        Assert.notNull(latest, ResponseHandler.NO_FORECAST_DATA);

        String begin   = PeriodCalculator.ssqPeriod(latest.getPeriod(), 30);
        long   current = System.currentTimeMillis();
        IntStream.range(0, 31)
                 .mapToObj(i -> Pair.of(PeriodCalculator.ssqAddPeriod(begin, i), current + i * 1000 * 1000L))
                 .forEach(e -> ssqForecastFetcher.incrFetchAsync(e.getKey(), e.getValue(), 1000));
    }

    @Override
    public void calcIncrAllHit() {
        List<String> periods = ssqIcaiMapper.getIncrUnCalculatedPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(ssqICaiDomainAbility::calcIncrHit);
    }

    @Override
    public void incrCalcMasterRate() {
        List<String> periods = ssqIcaiMapper.getIncrUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(ssqICaiDomainAbility::calcIncrMasterRate);
    }

}
