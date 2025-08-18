package com.prize.lottery.application.service.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.Fc3dComSelectCmd;
import com.prize.lottery.application.cmd.N3TodayPivotCmd;
import com.prize.lottery.application.executor.*;
import com.prize.lottery.application.service.IFc3dICaiService;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.fc3d.model.Fc3dPivotDo;
import com.prize.lottery.domain.fc3d.repository.IFc3dPivotRepository;
import com.prize.lottery.domain.n3item.domain.N3ItemCensusDo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.DanMarker;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.fc3d.Fc3dIcaiDataVo;
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
public class Fc3dICaiService implements IFc3dICaiService {

    private final static Integer FC3D_RANGE = 4000;

    private final Fc3dIcaiMapper          fc3dIcaiMapper;
    private final Fc3dMasterMapper        fc3dMasterMapper;
    private final LotteryInfoMapper       lotteryInfoMapper;
    private final ICaiDomainAbility       fc3dICaiDomainAbility;
    @Qualifier(OpenForecastConfigurer.FC3D_FETCHER)
    private final OpenForecastFetcher     fc3dForecastFetcher;
    private final Fc3dDifferAnalyzeExe    fc3dDifferAnalyzeExe;
    private final Fc3dCombineAnalyzeExe   fc3dCombineAnalyzeExe;
    private final Fc3dReverseAnalyzeExe   fc3dReverseAnalyzeExe;
    private final Fc3dComRecommendAnaExe  fc3dComRecommendAnaExe;
    private final Fc3dComRecommendCalcExe fc3dComRecommendCalcExe;
    private final Fc3dItemAnalyzeExe      fc3dItemAnalyzeExe;
    private final IFc3dPivotRepository    fc3dPivotRepository;

    /**
     * 拉取指定期的预测数据
     *
     * @param period 指定期号
     */
    @Override
    public void fetchForecast(String period) {
        int exist = fc3dMasterMapper.hasExistRatePeriod(period);
        Assert.state(exist == 0, String.format("%s期数据已存在", period));
        fc3dForecastFetcher.fetchDelay(period, FC3D_RANGE);
    }

    @Override
    public void fetchHistoryForecast() {
        //最新开奖数据，一定要先抓取开奖数据
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.FC3D.getType());
        Assert.notNull(lottoPeriod, ResponseHandler.NO_OPEN_LOTTERY);
        //最新预测数据
        Period latest = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        //开始抓取期号
        String begin = Optional.ofNullable(latest)
                               .map(period -> PeriodCalculator.fc3dAddPeriod(period.getPeriod(), 1))
                               .orElseGet(() -> PeriodCalculator.fc3dPeriod(lottoPeriod, 29));
        //开始抓取期号不小于最新开奖期号
        Assert.state(lottoPeriod.compareTo(begin) >= 0, ResponseHandler.NO_FETCH_HISTORY);
        //每次请求最多抓取12期，1小时抓取1期全部数据
        final long current = System.currentTimeMillis();
        IntStream.range(0, 11)
                 .mapToObj(i -> Pair.of(PeriodCalculator.fc3dAddPeriod(begin, i), current + i * FC3D_RANGE * 1000L))
                 .filter(e -> e.getKey().compareTo(lottoPeriod) <= 0)
                 .forEach(e -> fc3dForecastFetcher.fetchAsync(e.getKey(), e.getValue(), FC3D_RANGE));
    }

    @Override
    public void fetchIncrHistory() {
        //最新预测数据
        Period latest = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        Assert.notNull(latest, ResponseHandler.NO_FORECAST_DATA);
        //开始抓取期号
        String     begin   = PeriodCalculator.fc3dPeriod(latest.getPeriod(), 30);
        final long current = System.currentTimeMillis();
        IntStream.range(0, 31)
                 .mapToObj(i -> Pair.of(PeriodCalculator.fc3dAddPeriod(begin, i), current + i * 1200 * 1000L))
                 .forEach(e -> fc3dForecastFetcher.incrFetchAsync(e.getKey(), e.getValue(), 1200));
    }

    /**
     * 拉取指定期以前的历史预测数据
     */
    @Override
    public void fetchLastForecast(String before, Integer size) {
        Period latest = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        int    newest = Optional.ofNullable(latest).map(v -> Integer.valueOf(v.getPeriod())).orElse(0);
        Assert.state(newest < Integer.parseInt(before), ResponseHandler.HAS_EXIST_HISTORY);
        long millis = System.currentTimeMillis();
        IntStream.range(0, size)
                 .mapToObj(i -> Pair.of(PeriodCalculator.fc3dPeriod(before, i), millis + i * 900L * 1000))
                 .filter(e -> Integer.parseInt(e.getLeft()) > newest)
                 .forEach(e -> fc3dForecastFetcher.fetchAsync(e.getLeft(), e.getRight(), 900));
    }

    /**
     * 初始化计算历史数据命中
     */
    @Override
    @Transactional
    public void batchCalcFc3dHit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.FC3D.getType());
        Assert.state(StringUtils.isNotBlank(latest), ResponseHandler.NO_OPEN_LOTTERY);
        List<String> periods = fc3dIcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(fc3dICaiDomainAbility::calcForecastHit);
    }

    @Override
    @Transactional
    public void calcIncrAllHit() {
        List<String> periods = fc3dIcaiMapper.getIncrUnCalculatedPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(fc3dICaiDomainAbility::calcIncrHit);
    }

    /**
     * 初始化计算专家历史命中率
     */
    @Override
    @Transactional
    public void initCalcMasterRate() {
        List<String> periods = fc3dIcaiMapper.getUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(fc3dICaiDomainAbility::calcMasterRate);
    }

    @Override
    public void incrCalcMasterRate() {
        List<String> periods = fc3dIcaiMapper.getIncrUnCalcRatePeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(fc3dICaiDomainAbility::calcIncrMasterRate);
    }

    /**
     * 初始化计算历史专家排名
     */
    @Override
    @Transactional
    public void initCalcMasterRank() {
        List<String> periods = fc3dMasterMapper.getUnRankedMasterPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(fc3dICaiDomainAbility::calcMasterRank);
    }

    /**
     * 增量分析
     *
     * @param period 期号
     */
    @Override
    public N3DifferAnalyzeVo differAnalyze(String period) {
        return fc3dDifferAnalyzeExe.execute(period);
    }

    /**
     * 组合计算分析
     *
     * @param period 计算期号
     */
    @Override
    public Fc3dCombineAnalyzeVo combineAnalyze(String period) {
        return fc3dCombineAnalyzeExe.execute(period);
    }

    /**
     * 取反分析
     *
     * @param period 计算期号
     */
    @Override
    public Fc3dReverseAnalyzeVo reverseAnalyze(String period) {
        return fc3dReverseAnalyzeExe.execute(period);
    }

    /**
     * 组合号码筛选
     */
    @Override
    public Fc3dComRecommendVo comRecommendAnalyze(Fc3dComSelectCmd command) {
        return fc3dComRecommendAnaExe.execute(command);
    }

    @Override
    public Fc3dDkRecommendVo dkRecommend(DkRecommendCmd cmd) {
        return fc3dComRecommendAnaExe.execute(cmd);
    }

    @Override
    public DanKillCalcResult danKillCalc(String period, ChartType type) {
        return fc3dComRecommendAnaExe.calculate(period, type);
    }

    @Override
    public N3ItemCensusDo calcItemCensus(String period) {
        return fc3dItemAnalyzeExe.execute(period);
    }

    /**
     * 计算组合推荐命中
     */
    @Override
    public void calcComRecommend(String period) {
        fc3dComRecommendCalcExe.execute(period);
    }

    @Override
    public void addFc3dPivot(N3TodayPivotCmd cmd) {
        List<Fc3dIcaiDataVo> forecastList = fc3dIcaiMapper.getBestDanForecastList(cmd.convert(LotteryEnum.FC3D::lastPeriod));
        Assert.state(!CollectionUtils.isEmpty(forecastList), ResponseHandler.NO_LOTTERY_MASTER);
        List<String> masterIds = forecastList.stream()
                                             .map(Fc3dIcaiDataVo::getMasterId)
                                             .limit(10)
                                             .collect(Collectors.toList());
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), cmd.getPeriod());
        List<String> balls = Optional.ofNullable(lottery)
                                     .map(LotteryInfoPo::redBalls)
                                     .orElseGet(Collections::emptyList);
        fc3dPivotRepository.ofPeriod(cmd.getPeriod())
                           .map(agg -> agg.peek(root -> root.editPivot(cmd.getBest(), cmd.getSecond(), cmd.quality(), masterIds, balls)))
                           .orElseGet(() -> AggregateFactory.create(new Fc3dPivotDo(cmd.getPeriod(), cmd.getBest(), cmd.getSecond(), cmd.quality(), masterIds, balls)))
                           .save(fc3dPivotRepository::save);
    }

    @Override
    public void calcPivotHit(String period) {
        Optional<Aggregate<Long, Fc3dPivotDo>> aggregate = fc3dPivotRepository.ofPeriod(period);
        if (aggregate.isEmpty()) {
            return;
        }
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), period);
        List<String> balls = Optional.ofNullable(lottery)
                                     .map(LotteryInfoPo::redBalls)
                                     .orElseGet(Collections::emptyList);
        if (!CollectionUtils.isEmpty(balls)) {
            aggregate.map(agg -> agg.peek(root -> root.calcPivotHit(balls))).ifPresent(fc3dPivotRepository::save);
        }
    }

    @Override
    public void calcForecastMark(String period) {
        String           lastPeriod  = LotteryEnum.FC3D.lastPeriod(period);
        LotteryInfoPo    lotteryInfo = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), lastPeriod);
        DanMarker        marker      = new DanMarker(lotteryInfo);
        List<Fc3dIcaiPo> datas       = fc3dIcaiMapper.getAllFc3dIcaiDatas(period);
        List<Fc3dIcaiPo> poList = datas.stream().map(e -> {
            Fc3dIcaiPo data = new Fc3dIcaiPo();
            data.setId(e.getId());
            data.setMark(marker.mark(e.getDan2().getData()));
            return data;
        }).collect(Collectors.toList());
        fc3dIcaiMapper.editForecastMarker(poList);
    }
}
