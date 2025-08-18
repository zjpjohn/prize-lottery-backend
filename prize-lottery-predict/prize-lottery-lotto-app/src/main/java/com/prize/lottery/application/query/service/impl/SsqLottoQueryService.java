package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.executor.SsqMasterDetailQueryExe;
import com.prize.lottery.application.query.service.ISsqLottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.MasterBattleMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.mapper.SsqMasterMapper;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.po.ssq.SsqLottoCensusPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.ssq.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsqLottoQueryService implements ISsqLottoQueryService {

    private static final Random random = new Random();

    private final SsqIcaiMapper                                    ssqIcaiMapper;
    private final MasterBattleMapper                               battleMapper;
    private final SsqMasterMapper                                  ssqMasterMapper;
    private final MasterInfoMapper                                 masterInfoMapper;
    private final SsqMasterDetailQueryExe                          ssqMasterDetailQueryExe;
    private final IBrowseForecastRepository<SsqIcaiPo, SsqChannel> ssqForecastRepository;

    @Override
    public Period latestPeriod() {
        return ssqForecastRepository.latestICaiPeriod();
    }

    @Override
    public List<String> lastPeriods(Integer size) {
        Period period = ssqIcaiMapper.latestSsqICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return LotteryEnum.SSQ.lastPeriods(period.getPeriod(), size);
    }

    @Override
    public String latestRank() {
        return ssqMasterMapper.latestSsqRankPeriod();
    }

    @Override
    public Page<ICaiRankedDataVo> getSsqRankedDataList(SsqAdmRankQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            Period period = ssqIcaiMapper.latestSsqICaiPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period.getPeriod());
        }
        return query.from().count(ssqIcaiMapper::countSsqRankedDatas).query(ssqIcaiMapper::getSsqRankedDatas);

    }

    @Override
    public Page<SsqMasterRankVo> getSsqRankedMasters(SsqRankQuery query) {
        String period = ssqMasterMapper.latestSsqRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(ssqMasterMapper::countSsqMasterRankList)
                    .query(ssqMasterMapper::getSsqMasterRankList);

    }

    @Override
    public Page<SsqICaiGladVo> getSsqGladList(PageQuery query) {
        String period = ssqMasterMapper.latestSsqRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .count(ssqMasterMapper::countSsqGladMasters)
                    .query(ssqMasterMapper::getSsqGladMasters);

    }

    @Override
    public Page<SsqMasterMulRankVo> getSsqMasterMulRankList(MulRankQuery query) {
        String period = ssqMasterMapper.latestSsqRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.SSQ.lastPeriod(period))
                    .count(ssqMasterMapper::countSsqMasterMulRanks)
                    .query(ssqMasterMapper::getSsqMasterMulRankList);
    }

    @Override
    public List<SsqMasterMulRankVo> getSsqRandomMasters(Integer size, Integer limitRank) {
        String period = ssqMasterMapper.latestSsqRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<Integer> ranks = IntStream.range(0, size)
                                       .map(i -> random.nextInt(limitRank))
                                       .distinct()
                                       .boxed()
                                       .collect(Collectors.toList());
        return ssqMasterMapper.getSsqRandomMasters(period, LotteryEnum.SSQ.lastPeriod(period), ranks);
    }

    @Override
    public SsqMasterDetail getSsqMasterDetail(String masterId, Long userId) {
        return ssqMasterDetailQueryExe.execute(masterId, userId);
    }

    @Override
    public List<SsqIcaiHistoryVo> geSsqMasterHistories(String masterId) {
        int exist = masterInfoMapper.existMasterLottery(masterId, LotteryEnum.SSQ.getType());
        Assert.state(exist == 1, ResponseHandler.MASTER_NONE);
        String period = ssqMasterMapper.latestSsqRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return ssqIcaiMapper.getHistorySsqForecasts(masterId, period, 15);
    }

    @Override
    public Map<String, List<HomeMasterVo>> getSsqHomedMasters() {
        String period = ssqMasterMapper.latestHomedMasterPeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyMap();
        }
        List<HomeMasterVo> masters = ssqMasterMapper.getSsqHomeMasters(period);
        return masters.stream().collect(Collectors.groupingBy(HomeMasterVo::getType));
    }

    @Override
    public Page<SsqMasterSubscribeVo> getSsqSubscribeMasters(SubscribeQuery query) {
        String period = ssqMasterMapper.latestSsqRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(ssqMasterMapper::countSsqMasterSubscribes)
                    .query(ssqMasterMapper::getSsqMasterSubScribeList);

    }

    @Override
    public Page<LotteryMasterVo> getSsqLotteryMasters(SsqLottoMasterQuery query) {
        String period = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            period = ssqMasterMapper.latestSsqRankPeriod();
            if (StringUtils.isBlank(period)) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period);
        }
        return query.from()
                    .setParam("last", LotteryEnum.SSQ.lastPeriod(period))
                    .count(ssqMasterMapper::countSsqLottoMasters)
                    .query(ssqMasterMapper::getSsqLottoMasterList);
    }

    private Period ofNullable(String period) {
        Period iPeriod = Optional.ofNullable(period)
                                 .filter(StringUtils::isNotBlank)
                                 .map(Period::new)
                                 .orElseGet(ssqIcaiMapper::latestSsqICaiPeriod);
        return Assert.notNull(iPeriod, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public SyntheticItemCensusVo itemChart(SsqChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = ssqForecastRepository.getForecastFullOrVipChart(ChartType.ITEM_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    public SyntheticFullCensusVo fullChart(SsqChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = ssqForecastRepository.getForecastFullOrVipChart(ChartType.ALL_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    public SyntheticVipCensusVo vipChart(SsqChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = ssqForecastRepository.getForecastFullOrVipChart(ChartType.VIP_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    public SsqChartCensusVo rateChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<SsqLottoCensusPo> censuses = ssqForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.RATE_CHART);
        return LottoChartAssembler.assembleSsqChart(censuses);
    }

    @Override
    public SsqChartCensusVo hotChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<SsqLottoCensusPo> censuses = ssqForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.HOT_CHART);
        return LottoChartAssembler.assembleSsqChart(censuses);
    }

    @Override
    public List<SsqMasterSchemaVo> getSsqSchemaMasters(Integer limit) {
        String period = ssqMasterMapper.latestSsqRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return ssqMasterMapper.getSsqSchemaMasters(period, limit);
    }

    @Override
    public List<SchemaRenewMasterVo> schemaRenewMasters() {
        if (LotteryEnum.SSQ.issueLimit(LocalDateTime.now())) {
            return Collections.emptyList();
        }
        final Period period = ssqIcaiMapper.latestSsqICaiPeriod();
        if (period == null || period.getCalculated() == 1) {
            return Collections.emptyList();
        }
        final String                    renewPeriod = period.getPeriod();
        final String                    lastPeriod  = PeriodCalculator.ssqPeriod(renewPeriod, 1);
        final List<SchemaRenewMasterVo> masters     = ssqIcaiMapper.getSsqSchemaRenewMasters(renewPeriod, lastPeriod, 5);
        if (CollectionUtils.isEmpty(masters)) {
            return Collections.emptyList();
        }
        masters.forEach(master -> master.setType(LotteryEnum.SSQ));
        return masters;
    }

    @Override
    public List<MasterBattleVo> getSsqMasterBattles(Long userId) {
        Period period = ssqIcaiMapper.latestSsqICaiPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        List<MasterBattleVo> battles = battleMapper.getUserMasterBattles(userId, LotteryEnum.SSQ.getType(), period.getPeriod());
        if (CollectionUtils.isEmpty(battles)) {
            return Collections.emptyList();
        }
        List<String> masterIds = battles.stream()
                                        .map(battle -> battle.getMaster().getMasterId())
                                        .collect(Collectors.toList());
        List<SsqIcaiPo>        forecasts   = ssqIcaiMapper.getSsqICaiListByMasters(period.getPeriod(), masterIds);
        Map<String, SsqIcaiPo> forecastMap = Maps.uniqueIndex(forecasts, SsqIcaiPo::getMasterId);
        battles.forEach(battle -> {
            SsqIcaiPo forecast = forecastMap.get(battle.getMaster().getMasterId());
            battle.setForecast(forecast);
        });
        return battles;
    }

    @Override
    public Page<MasterBattleRankVo<SsqMasterMulRankVo>> getSsqBattleMastersRanks(MasterBattleRankQuery query) {
        String period = ssqMasterMapper.latestSsqRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        Period               latest    = ssqIcaiMapper.latestSsqICaiPeriod();
        List<MasterBrowsePo> browses   = masterInfoMapper.getUserForecastBrowses(query.getUserId(), latest.getPeriod(), LotteryEnum.SSQ.getType());
        List<String>         masterIds = browses.stream().map(MasterBrowsePo::getSourceId).toList();
        return query.from()
                    .setParam("type", 0)
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.SSQ.lastPeriod(period))
                    .count(ssqMasterMapper::countSsqMasterMulRanks)
                    .query(ssqMasterMapper::getSsqMasterMulRankList)
                    .map(rank -> {
                        boolean contained = masterIds.contains(rank.getMaster().getMasterId());
                        return new MasterBattleRankVo<>(contained ? 1 : 0, rank);
                    });
    }

    @Override
    public SsqMasterRateVo getSsqMasterRate(String masterId) {
        String period = ssqMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return ssqMasterMapper.getSsqMasterRateVo(period, masterId);
    }

    @Override
    public List<SsqICaiHitVo> getSsqMasterBeforeHits(String masterId) {
        String period = ssqMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return ssqIcaiMapper.getSsqLast10HitList(period, masterId);
    }

}
