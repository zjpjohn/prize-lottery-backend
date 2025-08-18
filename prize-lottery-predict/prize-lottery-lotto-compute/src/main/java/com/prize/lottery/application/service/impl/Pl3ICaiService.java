package com.prize.lottery.application.service.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.N3TodayPivotCmd;
import com.prize.lottery.application.cmd.Pl3ComSelectedCmd;
import com.prize.lottery.application.executor.Pl3ComRecommendAnaExe;
import com.prize.lottery.application.executor.Pl3ComRecommendCalcExe;
import com.prize.lottery.application.executor.Pl3DifferAnalyzeExe;
import com.prize.lottery.application.executor.Pl3ItemAnalyzeExe;
import com.prize.lottery.application.service.IPl3ICaiService;
import com.prize.lottery.application.vo.DanKillCalcResult;
import com.prize.lottery.application.vo.N3DifferAnalyzeVo;
import com.prize.lottery.application.vo.Pl3ComRecommendVo;
import com.prize.lottery.application.vo.Pl3DkRecommendVo;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.n3item.domain.N3ItemCensusDo;
import com.prize.lottery.domain.pl3.model.Pl3PivotDo;
import com.prize.lottery.domain.pl3.repository.IPl3PivotRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.DanMarker;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.pl3.Pl3IcaiDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pl3ICaiService implements IPl3ICaiService {

    private static final Integer PL3_RANGE = 3000;

    private final Pl3IcaiMapper          pl3IcaiMapper;
    private final Pl3MasterMapper        pl3MasterMapper;
    private final LotteryInfoMapper      lotteryInfoMapper;
    private final ICaiDomainAbility      pl3ICaiDomainAbility;
    @Qualifier(OpenForecastConfigurer.PL3_FETCHER)
    private final OpenForecastFetcher    pleForecastFetcher;
    private final Pl3ComRecommendAnaExe  pl3ComRecommendAnaExe;
    private final Pl3ComRecommendCalcExe pl3ComRecommendCalcExe;
    private final Pl3ItemAnalyzeExe      pl3ItemAnalyzeExe;
    private final Pl3DifferAnalyzeExe    pl3DifferAnalyzeExe;
    private final IPl3PivotRepository    pl3PivotRepository;

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    @Override
    public void fetchForecast(String period) {
        int existed = pl3MasterMapper.hasExistRatePeriod(period);
        Assert.state(existed == 0, String.format("%s期数据已存在", period));
        pleForecastFetcher.fetchDelay(period, PL3_RANGE);
    }

    @Override
    public void fetchHistoryForecast() {
        //最新开奖数据，一定要先抓取开奖数据
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.PL3.getType());
        Assert.notNull(lottoPeriod, ResponseHandler.NO_OPEN_LOTTERY);
        //最新预测数据
        Period latest = pl3IcaiMapper.latestPl3ICaiPeriod();
        //开始抓取期号
        String begin = Optional.ofNullable(latest)
                               .map(period -> PeriodCalculator.pl3AddPeriod(period.getPeriod(), 1))
                               .orElseGet(() -> PeriodCalculator.pl3Period(lottoPeriod, 29));
        //开始抓取期号不小于最新开奖期号
        Assert.state(lottoPeriod.compareTo(begin) >= 0, ResponseHandler.NO_FETCH_HISTORY);
        //每次请求最多抓取9期，1小时抓取1期全部数据
        final long current = System.currentTimeMillis();
        IntStream.range(0, 11)
                 .mapToObj(i -> Pair.of(PeriodCalculator.pl3AddPeriod(begin, i), current + i * PL3_RANGE * 1000L))
                 .filter(e -> e.getKey().compareTo(lottoPeriod) <= 0)
                 .forEach(e -> pleForecastFetcher.fetchAsync(e.getKey(), e.getValue(), PL3_RANGE));
    }

    @Override
    public void fetchIncrHistory() {
        //最新预测数据
        Period latest = pl3IcaiMapper.latestPl3ICaiPeriod();
        Assert.notNull(latest, ResponseHandler.NO_FORECAST_DATA);
        //开始抓取期号
        String     begin   = PeriodCalculator.fc3dPeriod(latest.getPeriod(), 30);
        final long current = System.currentTimeMillis();
        IntStream.range(0, 31)
                 .mapToObj(i -> Pair.of(PeriodCalculator.pl3AddPeriod(begin, i), current + i * 1200 * 1000L))
                 .forEach(e -> pleForecastFetcher.incrFetchAsync(e.getKey(), e.getValue(), 1200));
    }

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    @Override
    public void fetchLastForecast(String before, Integer size) {
        Period latest = pl3IcaiMapper.latestPl3ICaiPeriod();
        int    newest = Optional.ofNullable(latest).map(v -> Integer.valueOf(v.getPeriod())).orElse(0);
        Assert.state(newest < Integer.parseInt(before), "历史数据已存在");
        long millis = System.currentTimeMillis();
        IntStream.range(0, size)
                 .mapToObj(i -> Pair.of(PeriodCalculator.pl3Period(before, i), millis + i * 1200L * 1000))
                 .filter(e -> Integer.parseInt(e.getLeft()) > newest)
                 .forEach(e -> pleForecastFetcher.fetchAsync(e.getLeft(), e.getRight(), 1200));
    }

    /**
     * 初始化计算历史数据命中
     */
    @Override
    @Transactional
    public void batchCalcPl3Hit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.PL3.getType());
        Assert.state(StringUtils.isNotBlank(latest), ResponseHandler.NO_OPEN_LOTTERY);
        List<String> periods = pl3IcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(pl3ICaiDomainAbility::calcForecastHit);
    }

    @Override
    @Transactional
    public void calcIncrAllHit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.PL3.getType());
        Assert.state(StringUtils.isNotBlank(latest), ResponseHandler.NO_OPEN_LOTTERY);
        List<String> periods = pl3IcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(pl3ICaiDomainAbility::calcIncrHit);
    }

    /**
     * 初始化计算专家历史命中率
     */
    @Override
    @Transactional
    public void initCalcMasterRate() {
        List<String> periods = pl3IcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(pl3ICaiDomainAbility::calcMasterRate);
    }

    @Override
    public void initIncrCalcRate() {
        List<String> periods = pl3IcaiMapper.getIncrUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(pl3ICaiDomainAbility::calcIncrMasterRate);
    }

    /**
     * 初始化计算历史专家排名
     */
    @Override
    @Transactional
    public void initCalcMasterRank() {
        List<String> periods = pl3MasterMapper.getUnRankedMasterPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), "没有未计算数据");
        periods.forEach(pl3ICaiDomainAbility::calcMasterRank);
    }

    /**
     * 组合推荐分析计算
     */
    @Override
    public Pl3ComRecommendVo comRecommendAna(Pl3ComSelectedCmd command) {
        return pl3ComRecommendAnaExe.execute(command);
    }

    @Override
    public Pl3DkRecommendVo dkRecommend(DkRecommendCmd cmd) {
        return pl3ComRecommendAnaExe.execute(cmd);
    }

    @Override
    public N3ItemCensusDo calcItemCensus(String period) {
        return pl3ItemAnalyzeExe.execute(period);
    }

    /**
     * 组合推荐命中计算
     *
     * @param period 推荐期号
     */
    @Override
    public void comRecommendCalc(String period) {
        pl3ComRecommendCalcExe.execute(period);
    }

    @Override
    public DanKillCalcResult calculateDanKill(String period, ChartType type) {
        return pl3ComRecommendAnaExe.calculate(period, type);
    }

    @Override
    public N3DifferAnalyzeVo differAnalyze(String period) {
        return pl3DifferAnalyzeExe.execute(period);
    }

    @Override
    public void addPl3Pivot(N3TodayPivotCmd cmd) {
        List<Pl3IcaiDataVo> forecastList = pl3IcaiMapper.getBestDanForecastList(cmd.convert(LotteryEnum.PL3::lastPeriod));
        Assert.state(!CollectionUtils.isEmpty(forecastList), ResponseHandler.NO_LOTTERY_MASTER);
        List<String> masterIds = forecastList.stream()
                                             .map(Pl3IcaiDataVo::getMasterId)
                                             .limit(10)
                                             .collect(Collectors.toList());

        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), cmd.getPeriod());
        List<String> balls = Optional.ofNullable(lottery)
                                     .map(LotteryInfoPo::redBalls)
                                     .orElseGet(Collections::emptyList);

        pl3PivotRepository.ofPeriod(cmd.getPeriod())
                          .map(agg -> agg.peek(pivot -> pivot.editPivot(cmd.getBest(), cmd.getSecond(), cmd.quality(), masterIds, balls)))
                          .orElseGet(() -> AggregateFactory.create(new Pl3PivotDo(cmd.getPeriod(), cmd.getBest(), cmd.getSecond(), cmd.quality(), masterIds, balls)))
                          .save(pl3PivotRepository::save);
    }

    @Override
    public void calcPivotHit(String period) {
        Optional<Aggregate<Long, Pl3PivotDo>> aggregate = pl3PivotRepository.ofPeriod(period);
        if (aggregate.isEmpty()) {
            return;
        }
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), period);
        List<String> balls = Optional.ofNullable(lottery)
                                     .map(LotteryInfoPo::redBalls)
                                     .orElseGet(Collections::emptyList);
        if (!CollectionUtils.isEmpty(balls)) {
            aggregate.map(agg -> agg.peek(root -> root.calcPivotHit(balls))).ifPresent(pl3PivotRepository::save);
        }
    }

    @Override
    public void calcForecastMark(String period) {
        String          lastPeriod  = LotteryEnum.PL3.lastPeriod(period);
        LotteryInfoPo   lotteryInfo = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), lastPeriod);
        DanMarker       marker      = new DanMarker(lotteryInfo);
        List<Pl3IcaiPo> datas       = pl3IcaiMapper.getAllPl3ICaiDatas(period);
        List<Pl3IcaiPo> poList = datas.stream().map(e -> {
            Pl3IcaiPo data = new Pl3IcaiPo();
            data.setId(e.getId());
            data.setMark(marker.mark(e.getDan2().getData()));
            return data;
        }).collect(Collectors.toList());
        pl3IcaiMapper.editForecastMarker(poList);
    }
}
