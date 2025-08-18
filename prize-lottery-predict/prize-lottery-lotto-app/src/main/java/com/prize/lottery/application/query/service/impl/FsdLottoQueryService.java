package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.executor.Fc3dItemAnalyzeExe;
import com.prize.lottery.application.query.executor.FsdMasterDetailQueryExe;
import com.prize.lottery.application.query.service.IFsdLottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.utils.Num3Dan3FilterUtils;
import com.prize.lottery.mapper.*;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.fc3d.*;
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
public class FsdLottoQueryService implements IFsdLottoQueryService {

    private final MasterBattleMapper                                 battleMapper;
    private final Fc3dIcaiMapper                                     fc3dIcaiMapper;
    private final Fc3dPivotMapper                                    fc3dPivotMapper;
    private final Fc3dMasterMapper                                   fc3dMasterMapper;
    private final MasterInfoMapper                                   masterInfoMapper;
    private final Fc3dItemAnalyzeExe                                 fc3dItemAnalyzeExe;
    private final Fc3dComRecommendMapper                             fc3dEarlyWarningMapper;
    private final FsdMasterDetailQueryExe                            fsdMasterDetailQueryExe;
    private final IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> fsdForecastRepository;

    @Override
    public Period latestPeriod() {
        return fsdForecastRepository.latestICaiPeriod();
    }

    @Override
    public List<String> lastPeriods(Integer size) {
        Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return LotteryEnum.FC3D.lastPeriods(period.getPeriod(), size);
    }

    @Override
    public String latestRank() {
        return fc3dMasterMapper.latestFc3dRankPeriod();
    }

    @Override
    public List<Fc3dIcaiDataVo> getItemRankedDatas(Fc3dChannel channel, String period) {
        if (StringUtils.isBlank(period)) {
            period = Optional.ofNullable(fc3dIcaiMapper.latestFc3dIcaiPeriod()).map(Period::getPeriod).orElse(null);
        }
        Assert.state(StringUtils.isNotBlank(period), ResponseHandler.FORECAST_NONE);
        String last = LotteryEnum.FC3D.lastPeriod(period);
        return fc3dIcaiMapper.getFc3dRankedDataList(channel.getChannel(), period, last, 15);
    }

    @Override
    public List<Fc3dIcaiDataVo> getMulRankedDatas(String period) {
        if (StringUtils.isBlank(period)) {
            period = Optional.ofNullable(fc3dIcaiMapper.latestFc3dIcaiPeriod()).map(Period::getPeriod).orElse(null);
        }
        Assert.state(StringUtils.isNotBlank(period), ResponseHandler.FORECAST_NONE);
        String last = LotteryEnum.FC3D.lastPeriod(period);
        return fc3dIcaiMapper.getMulRankDataList(period, last, 15);
    }

    @Override
    public List<Fc3dIcaiDataVo> getBestDanForecasts(N3BestQuery query) {
        return fc3dIcaiMapper.getBestDanForecastList(query.convert(LotteryEnum.FC3D::lastPeriod));
    }

    @Override
    public List<Fc3dIcaiDataVo> getDanFilterForecasts(Num3DanFilterQuery query) {
        String last = LotteryEnum.FC3D.lastPeriod(query.getPeriod());
        return fc3dIcaiMapper.getDanFilterForecastList(query.getPeriod(), last, query.getD1(), query.getD2());
    }

    @Override
    public List<ForecastValue> getDan3FilterList(Dan3FilterQuery query) {
        String              last   = LotteryEnum.FC3D.lastPeriod(query.getPeriod());
        List<ForecastValue> values = fc3dIcaiMapper.getFc3dDan3Filter(query.getPeriod(), last, query.getDans(), query.getKills());
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return Num3Dan3FilterUtils.dan3Filter(values, query);
    }

    @Override
    public Page<ICaiRankedDataVo> getFsdRankedDataList(FsdAdmRankQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period.getPeriod());
        }
        return query.from().count(fc3dIcaiMapper::countFc3dRankedDatas).query(fc3dIcaiMapper::getFc3dRankedDatas);
    }

    @Override
    public Page<Fc3dICaiGladVo> getFc3dGladList(PageQuery query) {
        String period = fc3dMasterMapper.latestFc3dRatePeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .count(fc3dMasterMapper::countFc3dGladMasters)
                    .query(fc3dMasterMapper::getFc3dGladMasters);
    }

    @Override
    public Page<Fc3dMasterMulRankVo> getFc3dMasterMulRanks(PageQuery query) {
        String period = fc3dMasterMapper.latestFc3dRankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        String predictPeriod = LotteryEnum.FC3D.nextPeriod(period);
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.FC3D.lastPeriod(period))
                    .count(fc3dMasterMapper::countFc3dMasterMulRanks)
                    .query(fc3dMasterMapper::getFc3dMasterMulRankList)
                    .ifPresent(ranks -> rankMasterMark(ranks, predictPeriod));
    }

    private <T extends BaseMasterRank> void rankMasterMark(List<T> ranks, String period) {
        List<String>            masterIds = CollectionUtils.toList(ranks, e -> e.getMaster().getMasterId());
        List<Fc3dIcaiPo>        markers   = fc3dIcaiMapper.getMasterDanMarkers(period, masterIds);
        Map<String, Fc3dIcaiPo> markMap   = CollectionUtils.toMap(markers, Fc3dIcaiPo::getMasterId);
        ranks.forEach(r -> {
            Fc3dIcaiPo data = markMap.get(r.getMaster().getMasterId());
            Optional.ofNullable(data).map(Fc3dIcaiPo::getMark).ifPresent(r::setMark);
        });
    }

    @Override
    public List<Fc3dMasterMulRankVo> getFc3dRandomMasters(Integer size, Integer limitRank) {
        String period = fc3dMasterMapper.latestFc3dRankPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        Random random = new Random();
        List<Integer> ranks = IntStream.range(0, size)
                                       .map(i -> random.nextInt(limitRank))
                                       .distinct()
                                       .boxed()
                                       .collect(Collectors.toList());
        String lastPeriod = LotteryEnum.FC3D.lastPeriod(period);
        return fc3dMasterMapper.getFc3dRandomMasters(period, lastPeriod, ranks);
    }

    @Override
    public Page<Fc3dMasterRankVo> getFc3dRankMasters(FsdRankQuery query) {
        String period = fc3dMasterMapper.latestFc3dRankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        String predictPeriod = LotteryEnum.FC3D.nextPeriod(period);
        return query.from(period)
                    .count(fc3dMasterMapper::countFc3dMasterRanks)
                    .query(fc3dMasterMapper::getFc3dMasterRankList)
                    .ifPresent(ranks -> rankMasterMark(ranks, predictPeriod));
    }

    @Override
    public Page<LotteryMasterVo> getFc3dLotteryMasters(FsdLottoMasterQuery query) {
        String period = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            period = fc3dMasterMapper.latestFc3dRankPeriod();
            if (StringUtils.isBlank(period)) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period);
        }
        return query.from()
                    .setParam("last", LotteryEnum.FC3D.lastPeriod(period))
                    .count(fc3dMasterMapper::countFc3dLottoMasters)
                    .query(fc3dMasterMapper::getFc3dLottoMasterList);
    }

    @Override
    public Fc3dMasterDetail getFsdMasterDetail(String masterId, Long userId) {
        return fsdMasterDetailQueryExe.execute(masterId, userId);
    }

    @Override
    public List<Fc3dIcaiHistoryVo> getFsdMasterHistories(String masterId) {
        int exist = masterInfoMapper.existMasterLottery(masterId, LotteryEnum.FC3D.getType());
        Assert.state(exist == 1, ResponseHandler.MASTER_NONE);
        String period = fc3dMasterMapper.latestFc3dRatePeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        return fc3dIcaiMapper.getHistoryFc3dForecasts(masterId, period, 15);
    }

    @Override
    public Map<String, List<HomeMasterVo>> getFsdHomedMasters() {
        String period = fc3dMasterMapper.latestFc3dHomeMasterPeriod();
        if (period == null) {
            return Collections.emptyMap();
        }
        List<HomeMasterVo> masters = fc3dMasterMapper.getFc3dHomeMasters(period);
        return masters.stream().collect(Collectors.groupingBy(HomeMasterVo::getType));
    }

    @Override
    public Page<Fc3dMasterSubscribeVo> getFsdSubscribeMasters(SubscribeQuery query) {
        String period = fc3dMasterMapper.latestFc3dRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(fc3dMasterMapper::countFc3dMasterSubscribes)
                    .query(fc3dMasterMapper::getFc3dMasterSubscribeList);

    }

    private Period ofNullable(String period) {
        Period iPeriod = Optional.ofNullable(period)
                                 .filter(StringUtils::isNotBlank)
                                 .map(Period::new)
                                 .orElseGet(fc3dIcaiMapper::latestFc3dIcaiPeriod);
        return Assert.notNull(iPeriod, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public SyntheticItemCensusVo itemChart(Fc3dChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = fsdForecastRepository.getForecastFullOrVipChart(ChartType.ITEM_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    public SyntheticFullCensusVo fullChart(Fc3dChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = fsdForecastRepository.getForecastFullOrVipChart(ChartType.ALL_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    public SyntheticVipCensusVo vipChart(Fc3dChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = fsdForecastRepository.getForecastFullOrVipChart(ChartType.VIP_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    public NumberThreeCensusVo rateChart(String period) {
        Period                  iPeriod  = this.ofNullable(period);
        List<Fc3dLottoCensusPo> censuses = fsdForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.RATE_CHART);
        return LottoChartAssembler.assembleFsdChart(censuses);
    }

    @Override
    public NumberThreeCensusVo hotChart(String period) {
        Period                  iPeriod  = this.ofNullable(period);
        List<Fc3dLottoCensusPo> censuses = fsdForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.HOT_CHART);
        return LottoChartAssembler.assembleFsdChart(censuses);
    }

    @Override
    public List<Fc3dMasterSchemaVo> fc3dSchemaMasters(Integer limit) {
        String period = fc3dMasterMapper.latestFc3dRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return fc3dMasterMapper.getFc3dSchemaMasters(period, limit);
    }

    @Override
    public Page<Fc3dComRecommendPo> getFc3dComRecommends(ComRecommendQuery query) {
        return query.from()
                    .count(fc3dEarlyWarningMapper::countFc3dComRecommends)
                    .query(fc3dEarlyWarningMapper::getFc3dComRecommends);
    }

    @Override
    public Fc3dComRecommendPo getComRecommend(String period) {
        return fc3dEarlyWarningMapper.getFc3dComRecommend(period);
    }

    @Override
    public List<SchemaRenewMasterVo> schemaRenewMasters() {
        //每日开奖时间段不推荐提醒更新
        if (LotteryEnum.FC3D.issueLimit(LocalDateTime.now())) {
            return Collections.emptyList();
        }
        final Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        if (period == null || period.getCalculated() == 1) {
            return Collections.emptyList();
        }
        final String                    renewPeriod = period.getPeriod();
        final String                    lastPeriod  = PeriodCalculator.fc3dPeriod(renewPeriod, 1);
        final List<SchemaRenewMasterVo> masters     = fc3dIcaiMapper.getFc3dSchemaRenewMasters(renewPeriod, lastPeriod, 5);
        masters.forEach(master -> master.setType(LotteryEnum.FC3D));
        return masters;
    }

    @Override
    public List<MasterBattleVo> getFc3dMasterBattles(Long userId) {
        Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        List<MasterBattleVo> battles = battleMapper.getUserMasterBattles(userId, LotteryEnum.FC3D.getType(), period.getPeriod());
        if (CollectionUtils.isEmpty(battles)) {
            return Collections.emptyList();
        }
        List<String> masterIds = battles.stream()
                                        .map(battle -> battle.getMaster().getMasterId())
                                        .collect(Collectors.toList());
        List<Fc3dIcaiPo>        forecasts   = fc3dIcaiMapper.getFc3dICaiListByMasters(period.getPeriod(), masterIds);
        Map<String, Fc3dIcaiPo> forecastMap = Maps.uniqueIndex(forecasts, Fc3dIcaiPo::getMasterId);
        battles.forEach(battle -> {
            Fc3dIcaiPo forecast = forecastMap.get(battle.getMaster().getMasterId());
            battle.setForecast(forecast);
        });
        return battles;
    }

    @Override
    public Page<MasterBattleRankVo<Fc3dMasterMulRankVo>> getFc3dBattleMasterRanks(MasterBattleRankQuery query) {
        String period = fc3dMasterMapper.latestFc3dRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        Period               latest    = fc3dIcaiMapper.latestFc3dIcaiPeriod();
        List<MasterBrowsePo> browses   = masterInfoMapper.getUserForecastBrowses(query.getUserId(), latest.getPeriod(), LotteryEnum.FC3D.getType());
        List<String>         masterIds = browses.stream().map(MasterBrowsePo::getSourceId).toList();
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.FC3D.lastPeriod(period))
                    .count(fc3dMasterMapper::countFc3dMasterMulRanks)
                    .query(fc3dMasterMapper::getFc3dMasterMulRankList)
                    .map(rank -> {
                        boolean contained = masterIds.contains(rank.getMaster().getMasterId());
                        return new MasterBattleRankVo<>(contained ? 1 : 0, rank);
                    });
    }

    @Override
    public Fc3dMasterRateVo getFc3dMasterRate(String masterId) {
        String period = fc3dMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return fc3dMasterMapper.getFc3dMasterRateVo(period, masterId);
    }

    @Override
    public List<Fc3dICaiHitVo> getFc3dMasterBeforeHits(String masterId) {
        String period = fc3dMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return fc3dIcaiMapper.getFc3dLast10HitList(period, masterId);
    }

    @Override
    public N3ItemCensusVo itemCensus(String period) {
        if (StringUtils.isBlank(period)) {
            Period latest = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            period = Assert.notNull(latest, ResponseHandler.FORECAST_NONE).getPeriod();
        }
        return fc3dItemAnalyzeExe.execute(period);
    }

    @Override
    public N3ItemBestTableVo itemTable(String period, Integer limit) {
        if (StringUtils.isBlank(period)) {
            Period latest = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            period = Assert.notNull(latest, ResponseHandler.FORECAST_NONE).getPeriod();
        }
        return fc3dItemAnalyzeExe.query(period, limit);
    }

    @Override
    public Page<Fc3dPivotPo> getPivotList(PivotListQuery query) {
        return query.from().count(fc3dPivotMapper::countFc3dPivots).query(fc3dPivotMapper::getFc3dPivotList);
    }

    @Override
    public Fc3dPivotPo getPivotInfo(Long id) {
        return fc3dPivotMapper.getFc3dPivotById(id).orElseThrow(Assert.supply(ResponseHandler.PIVOT_NONE));
    }

    @Override
    public List<String> warningPeriods() {
        return fc3dEarlyWarningMapper.fc3dRecommendPeriods();
    }

    @Override
    public List<String> pivotPeriods() {
        return fc3dPivotMapper.fc3dPivotPeriods();
    }

    @Override
    public List<N3Dan3MetricVo> dan3Filter(N3DanFilterQuery query) {
        return Num3Dan3FilterUtils.filter(query, fc3dIcaiMapper::getN3Dan3Metrics);
    }

    @Override
    public List<Num3MasterCountVo> getWholeBestForecasts(N3WholeBestQuery query) {
        Fc3dChannel channel = Fc3dChannel.findOf(query.getType());
        String      period  = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            Period latest = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            Assert.notNull(latest, ResponseHandler.FORECAST_NONE);
            period = latest.getPeriod();
        }
        List<Num3MasterCountVo> masters = fc3dIcaiMapper.getHitMasterCounts(period, channel.getChannel(), channel.qThrottle(), query.getLimit());
        List<String> masterIds = masters.stream().map(Num3MasterCountVo::getMasterId).collect(Collectors.toList());
        List<Fc3dIcaiPo> forecasts = fc3dIcaiMapper.getMasterForecasts(period, masterIds);
        Map<String, Fc3dIcaiPo> dataMap = forecasts.stream()
                                                   .collect(Collectors.toMap(Fc3dIcaiPo::getMasterId, Function.identity()));
        masters.forEach(master -> {
            Fc3dIcaiPo data = dataMap.get(master.getMasterId());
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
