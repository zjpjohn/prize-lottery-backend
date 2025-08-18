package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.executor.QlcMasterDetailQueryExe;
import com.prize.lottery.application.query.service.IQlcLottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.MasterBattleMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.mapper.QlcMasterMapper;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.po.qlc.QlcLottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.qlc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class QlcLottoQueryService implements IQlcLottoQueryService {

    private final QlcIcaiMapper                                    qlcIcaiMapper;
    private final MasterBattleMapper                               battleMapper;
    private final QlcMasterMapper                                  qlcMasterMapper;
    private final MasterInfoMapper                                 masterInfoMapper;
    private final QlcMasterDetailQueryExe                          qlcMasterDetailQueryExe;
    private final IBrowseForecastRepository<QlcIcaiPo, QlcChannel> qlcForecastRepository;

    @Override
    public Period latestPeriod() {
        return qlcForecastRepository.latestICaiPeriod();
    }

    @Override
    public List<String> lastPeriods(Integer size) {
        Period period = qlcIcaiMapper.latestQlcICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return LotteryEnum.PL3.lastPeriods(period.getPeriod(), size);
    }

    @Override
    public String latestRank() {
        return qlcMasterMapper.latestQlcRankPeriod();
    }

    @Override
    public Page<ICaiRankedDataVo> getQlcRankedDataList(QlcAdmRankQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            Period period = qlcIcaiMapper.latestQlcICaiPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period.getPeriod());
        }
        return query.from().count(qlcIcaiMapper::countQlcRankedDatas).query(qlcIcaiMapper::getQlcRankedDatas);

    }

    @Override
    public Page<QlcICaiGladVo> getQlcGladList(PageQuery query) {
        String period = qlcMasterMapper.latestQlcRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .count(qlcMasterMapper::countQlcGladMasters)
                    .query(qlcMasterMapper::getQlcGladMasters);

    }

    @Override
    public Page<QlcMasterMulRankVo> getQlcMasterMulRankList(PageQuery query) {
        String period = qlcMasterMapper.latestQlcRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.QLC.lastPeriod(period))
                    .count(qlcMasterMapper::countQlcMasterMulRanks)
                    .query(qlcMasterMapper::getQlcMasterMulRankList);
    }

    @Override
    public List<QlcMasterMulRankVo> getQlcRandomMasters(Integer size, Integer limitRank) {
        String period = qlcMasterMapper.latestQlcRankPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        Random random = new Random();
        List<Integer> ranks = IntStream.range(0, size)
                                       .map(i -> random.nextInt(limitRank))
                                       .distinct()
                                       .boxed()
                                       .collect(Collectors.toList());
        String lastPeriod = LotteryEnum.QLC.lastPeriod(period);
        return qlcMasterMapper.getQlcRandomMasters(period, lastPeriod, ranks);
    }

    @Override
    public Page<QlcMasterRankVo> getQlcRankMasters(QlcRankQuery query) {
        String period = qlcMasterMapper.latestQlcRankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(qlcMasterMapper::countQlcMasterRankList)
                    .query(qlcMasterMapper::getQlcMasterRankList);

    }

    @Override
    public QlcMasterDetail getQlcMasterDetail(String masterId, Long userId) {
        return qlcMasterDetailQueryExe.execute(masterId, userId);
    }

    @Override
    public List<QlcIcaiHistoryVo> getQlcMasterHistories(String masterId) {
        int exist = masterInfoMapper.existMasterLottery(masterId, LotteryEnum.QLC.getType());
        Assert.state(exist == 1, ResponseHandler.MASTER_NONE);
        String period = qlcMasterMapper.latestQlcRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return qlcIcaiMapper.getHistoryQlcForecasts(masterId, period, 15);
    }

    @Override
    public Map<String, List<HomeMasterVo>> getQlcHomedMasters() {
        String period = qlcMasterMapper.latestHomedMasterPeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyMap();
        }
        List<HomeMasterVo> masters = qlcMasterMapper.getQlcHomeMasters(period);
        return masters.stream().collect(Collectors.groupingBy(HomeMasterVo::getType));
    }

    @Override
    public Page<QlcMasterSubscribeVo> getQlcMasterSubscribeList(SubscribeQuery query) {
        String period = qlcMasterMapper.latestQlcRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(qlcMasterMapper::countQlcMasterSubscribeList)
                    .query(qlcMasterMapper::getQlcMasterSubscribeList);

    }

    @Override
    public Page<LotteryMasterVo> getQlcLotteryMasters(QlcLottoMasterQuery query) {
        String period = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            period = qlcMasterMapper.latestQlcRankPeriod();
            if (StringUtils.isBlank(period)) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period);
        }
        return query.from()
                    .setParam("last", LotteryEnum.QLC.lastPeriod(period))
                    .count(qlcMasterMapper::countQlcLottoMasters)
                    .query(qlcMasterMapper::getQlcLottoMasterList);
    }

    private Period ofNullable(String period) {
        Period iPeriod = Optional.ofNullable(period)
                                 .filter(StringUtils::isNotBlank)
                                 .map(Period::new)
                                 .orElseGet(qlcIcaiMapper::latestQlcICaiPeriod);
        return Assert.notNull(iPeriod, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public SyntheticItemCensusVo itemChart(QlcChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = qlcForecastRepository.getForecastFullOrVipChart(ChartType.ITEM_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    public SyntheticFullCensusVo fullChart(QlcChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = qlcForecastRepository.getForecastFullOrVipChart(ChartType.ALL_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    public SyntheticVipCensusVo vipChart(QlcChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = qlcForecastRepository.getForecastFullOrVipChart(ChartType.VIP_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    public QlcChartCensusVo rateChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<QlcLottoCensusPo> censuses = qlcForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.RATE_CHART);
        return LottoChartAssembler.assembleQlcChart(censuses);
    }

    @Override
    public QlcChartCensusVo hotChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<QlcLottoCensusPo> censuses = qlcForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.HOT_CHART);
        return LottoChartAssembler.assembleQlcChart(censuses);
    }

    @Override
    public List<QlcMasterSchemaVo> getQlcSchemaMasters(Integer limit) {
        String period = qlcMasterMapper.latestQlcRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return qlcMasterMapper.getQlcSchemaMasters(period, limit);
    }

    @Override
    public List<SchemaRenewMasterVo> schemaRenewMasters() {
        if (LotteryEnum.QLC.issueLimit(LocalDateTime.now())) {
            return Collections.emptyList();
        }
        Period period = qlcIcaiMapper.latestQlcICaiPeriod();
        if (period == null || period.getCalculated() == 1) {
            return Collections.emptyList();
        }
        String                    renewPeriod = period.getPeriod();
        String                    lastPeriod  = PeriodCalculator.qlcPeriod(renewPeriod, 1);
        List<SchemaRenewMasterVo> masters     = qlcIcaiMapper.getQlcSchemaRenewMasters(renewPeriod, lastPeriod, 5);
        if (CollectionUtils.isEmpty(masters)) {
            return Collections.emptyList();
        }
        masters.forEach(maser -> maser.setType(LotteryEnum.QLC));
        return masters;
    }

    @Override
    public List<MasterBattleVo> getQlcMasterBattles(Long userId) {
        Period period = qlcIcaiMapper.latestQlcICaiPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        List<MasterBattleVo> battles = battleMapper.getUserMasterBattles(userId, LotteryEnum.QLC.getType(), period.getPeriod());
        if (CollectionUtils.isEmpty(battles)) {
            return Collections.emptyList();
        }
        List<String> masterIds = battles.stream()
                                        .map(battle -> battle.getMaster().getMasterId())
                                        .collect(Collectors.toList());
        List<QlcIcaiPo>                 forecasts   = qlcIcaiMapper.getQlcICaiListByMasters(period.getPeriod(), masterIds);
        ImmutableMap<String, QlcIcaiPo> forecastMap = Maps.uniqueIndex(forecasts, QlcIcaiPo::getMasterId);
        battles.forEach(battle -> {
            QlcIcaiPo forecast = forecastMap.get(battle.getMaster().getMasterId());
            battle.setForecast(forecast);
        });
        return battles;
    }

    @Override
    public Page<MasterBattleRankVo<QlcMasterMulRankVo>> getQlcBattleMasterRanks(MasterBattleRankQuery query) {
        String period = qlcMasterMapper.latestQlcRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        Period               latest    = qlcIcaiMapper.latestQlcICaiPeriod();
        List<MasterBrowsePo> browses   = masterInfoMapper.getUserForecastBrowses(query.getUserId(), latest.getPeriod(), LotteryEnum.QLC.getType());
        List<String>         masterIds = browses.stream().map(MasterBrowsePo::getSourceId).toList();
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.QLC.lastPeriod(period))
                    .count(qlcMasterMapper::countQlcMasterMulRanks)
                    .query(qlcMasterMapper::getQlcMasterMulRankList)
                    .map(rank -> {
                        boolean contained = masterIds.contains(rank.getMaster().getMasterId());
                        return new MasterBattleRankVo<>(contained ? 1 : 0, rank);
                    });
    }

    @Override
    public QlcMasterRateVo getQlcMasterRate(String masterId) {
        String period = qlcMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return qlcMasterMapper.getQlcMasterRateVo(period, masterId);
    }

    @Override
    public List<QlcICaiHitVo> getQlcMasterBeforeHits(String masterId) {
        String period = qlcMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return qlcIcaiMapper.getQlcLast10HitList(period, masterId);
    }

    @Override
    public List<QlcMasterCountVo> getWholeBestForecasts(QlcWholeBestQuery query) {
        QlcChannel channel = QlcChannel.findOf(query.getType());
        String     period  = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            Period latest = qlcIcaiMapper.latestQlcICaiPeriod();
            Assert.notNull(latest, ResponseHandler.FORECAST_NONE);
            period = latest.getPeriod();
        }
        List<QlcMasterCountVo> masters   = qlcIcaiMapper.getHitMasterCounts(period, channel.getChannel(), channel.qThrottle(), query.getLimit());
        List<String>           masterIds = masters.stream()
                                                  .map(QlcMasterCountVo::getMasterId)
                                                  .collect(Collectors.toList());
        List<QlcIcaiPo>        forecasts = qlcIcaiMapper.getMasterForecasts(period, masterIds);
        Map<String, QlcIcaiPo> dataMap = forecasts.stream()
                                                  .collect(Collectors.toMap(QlcIcaiPo::getMasterId, Function.identity()));
        masters.forEach(master -> {
            QlcIcaiPo data = dataMap.get(master.getMasterId());
            if (data != null) {
                master.setKill3(data.getKill3());
                master.setKill6(data.getKill6());
                master.setPeriod(data.getPeriod());
                master.setData(channel.forecastValue(data));
            }
        });
        return masters;
    }
}
