package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.executor.Pl3ItemAnalyzeExe;
import com.prize.lottery.application.query.executor.PlsMasterDetailQueryExe;
import com.prize.lottery.application.query.service.IPl3LottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.utils.Num3Dan3FilterUtils;
import com.prize.lottery.mapper.*;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.pl3.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pl3LottoQueryService implements IPl3LottoQueryService {

    private final Pl3IcaiMapper                                    pl3IcaiMapper;
    private final MasterBattleMapper                               battleMapper;
    private final Pl3MasterMapper                                  pl3MasterMapper;
    private final Pl3PivotMapper                                   pl3PivotMapper;
    private final MasterInfoMapper                                 masterInfoMapper;
    private final Pl3ItemAnalyzeExe                                pl3ItemAnalyzeExe;
    private final Pl3ComRecommendMapper                            pl3EarlyWarningMapper;
    private final PlsMasterDetailQueryExe                          plsMasterDetailQueryExe;
    private final IBrowseForecastRepository<Pl3IcaiPo, Pl3Channel> plsForecastRepository;

    @Override
    public Period latestPeriod() {
        return plsForecastRepository.latestICaiPeriod();
    }

    @Override
    public List<String> lastPeriods(Integer size) {
        Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return LotteryEnum.PL3.lastPeriods(period.getPeriod(), size);
    }

    @Override
    public String latestRank() {
        return pl3MasterMapper.latestPl3RankPeriod();
    }

    @Override
    public List<Pl3IcaiDataVo> getItemRankedDatas(Pl3Channel channel, String period) {
        if (StringUtils.isBlank(period)) {
            period = Optional.ofNullable(pl3IcaiMapper.latestPl3ICaiPeriod()).map(Period::getPeriod).orElse(null);
        }
        Assert.notNull(StringUtils.isNotBlank(period), ResponseHandler.FORECAST_NONE);
        String last = LotteryEnum.PL3.lastPeriod(period);
        return pl3IcaiMapper.getPl3RankedDataList(channel.getChannel(), period, last, 15);
    }

    @Override
    public List<Pl3IcaiDataVo> getMulRankedDatas(String period) {
        if (StringUtils.isBlank(period)) {
            period = Optional.ofNullable(pl3IcaiMapper.latestPl3ICaiPeriod()).map(Period::getPeriod).orElse(null);
        }
        Assert.notNull(StringUtils.isNotBlank(period), ResponseHandler.FORECAST_NONE);
        String last = LotteryEnum.PL3.lastPeriod(period);
        return pl3IcaiMapper.getMulRankDataList(period, last, 15);
    }

    @Override
    public List<Pl3IcaiDataVo> getBestDanForecasts(N3BestQuery query) {
        return pl3IcaiMapper.getBestDanForecastList(query.convert(LotteryEnum.PL3::lastPeriod));
    }

    @Override
    public List<Pl3IcaiDataVo> getDanFilterForecasts(Num3DanFilterQuery query) {
        String last = LotteryEnum.PL3.lastPeriod(query.getPeriod());
        return pl3IcaiMapper.getDanFilterForecastList(query.getPeriod(), last, query.getD1(), query.getD2());
    }

    @Override
    public List<ForecastValue> getDan3FilterList(Dan3FilterQuery query) {
        String              last   = LotteryEnum.PL3.lastPeriod(query.getPeriod());
        List<ForecastValue> values = pl3IcaiMapper.getPl3Dan3Filter(query.getPeriod(), last, query.getDans(), query.getKills());
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return Num3Dan3FilterUtils.dan3Filter(values, query);
    }

    @Override
    public Page<ICaiRankedDataVo> getPlsRankedDataList(PlsAdmRankQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period.getPeriod());
        }
        return query.from().count(pl3IcaiMapper::countPl3RankedDatas).query(pl3IcaiMapper::getPl3RankedDatas);
    }

    @Override
    public Page<Pl3ICaiGladVo> getPlsGladList(PageQuery query) {
        String period = pl3MasterMapper.latestPl3RatePeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .count(pl3MasterMapper::countPl3GladMasters)
                    .query(pl3MasterMapper::getPl3GladMasters);
    }

    @Override
    public Page<Pl3MasterMulRankVo> getPlsMasterMulRankList(PageQuery query) {
        String period = pl3MasterMapper.latestPl3RankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        String predictPeriod = LotteryEnum.PL3.nextPeriod(period);
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.PL3.lastPeriod(period))
                    .count(pl3MasterMapper::countPl3MasterMulRanks)
                    .query(pl3MasterMapper::getPl3MasterMulRankList)
                    .ifPresent(ranks -> rankMasterMark(ranks, predictPeriod));
    }

    private <T extends BaseMasterRank> void rankMasterMark(List<T> ranks, String period) {
        List<String>           masterIds = CollectionUtils.toList(ranks, e -> e.getMaster().getMasterId());
        List<Pl3IcaiPo>        markers   = pl3IcaiMapper.getMasterDanMarkers(period, masterIds);
        Map<String, Pl3IcaiPo> markMap   = CollectionUtils.toMap(markers, Pl3IcaiPo::getMasterId);
        ranks.forEach(r -> {
            Pl3IcaiPo data = markMap.get(r.getMaster().getMasterId());
            Optional.ofNullable(data).map(Pl3IcaiPo::getMark).ifPresent(r::setMark);
        });
    }

    @Override
    public List<Pl3MasterMulRankVo> getPl3RandomMasters(Integer size, Integer limitRank) {
        String period = pl3MasterMapper.latestPl3RankPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        Random random = new Random();
        List<Integer> ranks = IntStream.range(0, size)
                                       .map(i -> random.nextInt(limitRank))
                                       .distinct()
                                       .boxed()
                                       .collect(Collectors.toList());
        String lastPeriod = LotteryEnum.PL3.lastPeriod(period);
        return pl3MasterMapper.getPl3RandomMasters(period, lastPeriod, ranks);
    }

    @Override
    public Page<Pl3MasterRankVo> getPlsRankMasters(PlsRankQuery query) {
        String period = pl3MasterMapper.latestPl3RankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        String predictPeriod = LotteryEnum.PL3.nextPeriod(period);
        return query.from(period)
                    .count(pl3MasterMapper::countPl3MasterRanks)
                    .query(pl3MasterMapper::getPl3MasterRankList)
                    .ifPresent(ranks -> rankMasterMark(ranks, predictPeriod));

    }

    @Override
    public Pl3MasterDetail getPlsMasterDetail(String masterId, Long userId) {
        return plsMasterDetailQueryExe.execute(masterId, userId);
    }

    @Override
    public List<Pl3IcaiHistoryVo> getPlsMasterHistories(String masterId) {
        int exist = masterInfoMapper.existMasterLottery(masterId, LotteryEnum.PL3.getType());
        Assert.state(exist == 1, ResponseHandler.MASTER_NONE);
        String period = pl3MasterMapper.latestPl3RatePeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        return pl3IcaiMapper.getHistoryPl3Forecasts(masterId, period, 15);
    }

    @Override
    public Map<String, List<HomeMasterVo>> getPlsHomedMasters() {
        String period = pl3MasterMapper.latestHomedMasterPeriod();
        if (period == null) {
            return Collections.emptyMap();
        }
        List<HomeMasterVo> masters = pl3MasterMapper.getPl3HomeMasters(period);
        return masters.stream().collect(Collectors.groupingBy(HomeMasterVo::getType));
    }

    @Override
    public Page<Pl3MasterSubscribeVo> getPlsSubscribeMasters(SubscribeQuery query) {
        String period = pl3MasterMapper.latestPl3RatePeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(pl3MasterMapper::countPl3MasterSubscribes)
                    .query(pl3MasterMapper::getPl3MasterSubscribeList);

    }

    @Override
    public Page<LotteryMasterVo> getPl3LotteryMasters(Pl3LottoMasterQuery query) {
        String period = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            period = pl3MasterMapper.latestPl3RankPeriod();
            if (StringUtils.isBlank(period)) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period);
        }
        return query.from()
                    .setParam("last", LotteryEnum.PL3.lastPeriod(period))
                    .count(pl3MasterMapper::countPl3LottoMasters)
                    .query(pl3MasterMapper::getPl3LottoMasterList);
    }

    private Period ofNullable(String period) {
        Period iPeriod = Optional.ofNullable(period)
                                 .filter(StringUtils::isNotBlank)
                                 .map(Period::new)
                                 .orElseGet(pl3IcaiMapper::latestPl3ICaiPeriod);
        return Assert.notNull(iPeriod, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public SyntheticItemCensusVo itemChart(Pl3Channel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = plsForecastRepository.getForecastFullOrVipChart(ChartType.ITEM_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    public SyntheticFullCensusVo fullChart(Pl3Channel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = plsForecastRepository.getForecastFullOrVipChart(ChartType.ALL_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    public SyntheticVipCensusVo vipChart(Pl3Channel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = plsForecastRepository.getForecastFullOrVipChart(ChartType.VIP_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    public NumberThreeCensusVo rateChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<Pl3LottoCensusPo> censuses = plsForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.RATE_CHART);
        return LottoChartAssembler.assemblePlsChart(censuses);
    }

    @Override
    public NumberThreeCensusVo hotChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<Pl3LottoCensusPo> censuses = plsForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.HOT_CHART);
        return LottoChartAssembler.assemblePlsChart(censuses);
    }

    @Override
    public List<Pl3MasterSchemaVo> getPl3SchemaMasters(Integer limit) {
        String period = pl3MasterMapper.latestPl3RatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return pl3MasterMapper.getPl3SchemaMasters(period, limit);
    }

    @Override
    public Page<Pl3ComRecommendPo> getComRecommendList(ComRecommendQuery query) {
        return query.from()
                    .count(pl3EarlyWarningMapper::countComRecommends)
                    .query(pl3EarlyWarningMapper::getComRecommends);
    }

    @Override
    public Pl3ComRecommendPo getComRecommend(String period) {
        return pl3EarlyWarningMapper.getComRecommend(period);
    }

    @Override
    public List<SchemaRenewMasterVo> schemaRenewMasters() {
        if (LotteryEnum.PL3.issueLimit(LocalDateTime.now())) {
            return Collections.emptyList();
        }
        final Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
        if (period == null || period.getCalculated() == 1) {
            return Collections.emptyList();
        }
        final String                    renewPeriod = period.getPeriod();
        final String                    lastPeriod  = PeriodCalculator.pl3Period(renewPeriod, 1);
        final List<SchemaRenewMasterVo> masters     = pl3IcaiMapper.getPl3SchemaRenewMasters(renewPeriod, lastPeriod, 5);
        if (CollectionUtils.isEmpty(masters)) {
            return Collections.emptyList();
        }
        masters.forEach(master -> master.setType(LotteryEnum.PL3));
        return masters;
    }

    @Override
    public List<MasterBattleVo> getPl3MasterBattles(Long userId) {
        Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        List<MasterBattleVo> battles = battleMapper.getUserMasterBattles(userId, LotteryEnum.PL3.getType(), period.getPeriod());
        if (CollectionUtils.isEmpty(battles)) {
            return Collections.emptyList();
        }
        List<String> masterIds = battles.stream()
                                        .map(battle -> battle.getMaster().getMasterId())
                                        .collect(Collectors.toList());
        List<Pl3IcaiPo>        forecasts   = pl3IcaiMapper.getPl3ICaiListByMasters(period.getPeriod(), masterIds);
        Map<String, Pl3IcaiPo> forecastMap = Maps.uniqueIndex(forecasts, Pl3IcaiPo::getMasterId);

        battles.forEach(battle -> {
            Pl3IcaiPo forecast = forecastMap.get(battle.getMaster().getMasterId());
            battle.setForecast(forecast);
        });
        return battles;
    }

    @Override
    public Page<MasterBattleRankVo<Pl3MasterMulRankVo>> getPl3BattleMasterRanks(MasterBattleRankQuery query) {
        String period = pl3MasterMapper.latestPl3RankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        Period               latest    = pl3IcaiMapper.latestPl3ICaiPeriod();
        List<MasterBrowsePo> browses   = masterInfoMapper.getUserForecastBrowses(query.getUserId(), latest.getPeriod(), LotteryEnum.PL3.getType());
        List<String>         masterIds = browses.stream().map(MasterBrowsePo::getSourceId).toList();

        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.PL3.lastPeriod(period))
                    .count(pl3MasterMapper::countPl3MasterMulRanks)
                    .query(pl3MasterMapper::getPl3MasterMulRankList)
                    .map(rank -> {
                        boolean contained = masterIds.contains(rank.getMaster().getMasterId());
                        return new MasterBattleRankVo<>(contained ? 1 : 0, rank);
                    });
    }

    @Override
    public Pl3MasterRateVo getPl3MasterRate(String masterId) {
        String period = pl3MasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return pl3MasterMapper.getPl3MasterRateVo(period, masterId);
    }

    @Override
    public List<Pl3ICaiHitVo> getPl3MasterBeforeHits(String masterId) {
        String period = pl3MasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return pl3IcaiMapper.getPl3Last10HitList(period, masterId);
    }

    @Override
    public N3ItemCensusVo itemCensus(String period) {
        if (StringUtils.isBlank(period)) {
            Period latest = pl3IcaiMapper.latestPl3ICaiPeriod();
            period = Assert.notNull(latest, ResponseHandler.FORECAST_NONE).getPeriod();
        }
        return pl3ItemAnalyzeExe.execute(period);
    }

    @Override
    public N3ItemBestTableVo itemTable(String period, Integer limit) {
        if (StringUtils.isBlank(period)) {
            Period latest = pl3IcaiMapper.latestPl3ICaiPeriod();
            period = Assert.notNull(latest, ResponseHandler.FORECAST_NONE).getPeriod();
        }
        return pl3ItemAnalyzeExe.query(period, limit);
    }

    @Override
    public Page<Pl3PivotPo> getPivotList(PivotListQuery query) {
        return query.from().count(pl3PivotMapper::countPl3Pivots).query(pl3PivotMapper::getPl3PivotList);
    }

    @Override
    public Pl3PivotPo getPivotInfo(Long id) {
        return pl3PivotMapper.getPl3PivotById(id).orElseThrow(Assert.supply(ResponseHandler.PIVOT_NONE));
    }

    @Override
    public List<String> pivotPeriods() {
        return pl3PivotMapper.pl3PivotPeriods();
    }

    @Override
    public List<String> warningPeriods() {
        return pl3EarlyWarningMapper.latestRecommendPeriods();
    }

    @Override
    public List<N3Dan3MetricVo> dan3Filter(N3DanFilterQuery query) {
        return Num3Dan3FilterUtils.filter(query, pl3IcaiMapper::getN3Dan3Metrics);
    }

    @Override
    public List<Num3MasterCountVo> getWholeBestForecasts(N3WholeBestQuery query) {
        Pl3Channel channel = Pl3Channel.findOf(query.getType());
        String     period  = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            Period latest = pl3IcaiMapper.latestPl3ICaiPeriod();
            Assert.notNull(latest, ResponseHandler.FORECAST_NONE);
            period = latest.getPeriod();
        }
        List<Num3MasterCountVo> masters   = pl3IcaiMapper.getHitMasterCounts(period, channel.getChannel(), channel.qThrottle(), query.getLimit());
        List<String>            masterIds = masters.stream()
                                                   .map(Num3MasterCountVo::getMasterId)
                                                   .collect(Collectors.toList());
        List<Pl3IcaiPo>         forecasts = pl3IcaiMapper.getMasterForecasts(period, masterIds);
        Map<String, Pl3IcaiPo> dataMap = forecasts.stream()
                                                  .collect(Collectors.toMap(Pl3IcaiPo::getMasterId, Function.identity()));
        masters.forEach(master -> {
            Pl3IcaiPo data = dataMap.get(master.getMasterId());
            if (data != null) {
                master.setK1(data.getKill1());
                master.setC7(data.getCom7());
                master.setPeriod(data.getPeriod());
                master.setData(channel.forecastValue(data));
            }
        });
        return masters;
    }
}
