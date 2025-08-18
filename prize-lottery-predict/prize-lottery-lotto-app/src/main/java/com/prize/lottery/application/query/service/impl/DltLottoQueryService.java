package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.executor.DltMasterDetailQueryExe;
import com.prize.lottery.application.query.service.IDltLottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.mapper.MasterBattleMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.dlt.*;
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
public class DltLottoQueryService implements IDltLottoQueryService {

    private final DltIcaiMapper                                    dltIcaiMapper;
    private final MasterBattleMapper                               battleMapper;
    private final DltMasterMapper                                  dltMasterMapper;
    private final MasterInfoMapper                                 masterInfoMapper;
    private final DltMasterDetailQueryExe                          dltMasterDetailQueryExe;
    private final IBrowseForecastRepository<DltIcaiPo, DltChannel> dltForecastRepository;

    @Override
    public Period latestPeriod() {
        return dltForecastRepository.latestICaiPeriod();
    }

    @Override
    public List<String> lastPeriods(Integer size) {
        Period period = dltIcaiMapper.latestDltICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return LotteryEnum.DLT.lastPeriods(period.getPeriod(), size);
    }

    @Override
    public String latestRank() {
        return dltMasterMapper.latestDltRankPeriod();
    }

    @Override
    public Page<ICaiRankedDataVo> getDltRankedDataList(DltAdmRankQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            Period period = dltIcaiMapper.latestDltICaiPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period.getPeriod());
        }
        return query.from().count(dltIcaiMapper::countDltRankedDatas).query(dltIcaiMapper::getDltRankedDatas);
    }

    @Override
    public Page<DltICaiGladVo> getDltGladList(PageQuery query) {
        String period = dltMasterMapper.latestDltRatePeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .count(dltMasterMapper::countDltGladMasterList)
                    .query(dltMasterMapper::getDltGladMasterList);
    }

    @Override
    public Page<DltMasterMulRankVo> getDltMasterMulRankList(MulRankQuery query) {
        String period = dltMasterMapper.latestDltRankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.DLT.lastPeriod(period))
                    .count(dltMasterMapper::countDltMasterMulRanks)
                    .query(dltMasterMapper::getDltMasterMulRankList);
    }

    @Override
    public List<DltMasterMulRankVo> getDltRandomMasters(Integer size, Integer limitRank) {
        String period = dltMasterMapper.latestDltRankPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        Random random = new Random();
        List<Integer> ranks = IntStream.range(0, size)
                                       .map(i -> random.nextInt(limitRank))
                                       .distinct()
                                       .boxed()
                                       .collect(Collectors.toList());
        String lastPeriod = LotteryEnum.DLT.lastPeriod(period);
        return dltMasterMapper.getDltRandomMasters(period, lastPeriod, ranks);
    }

    @Override
    public Page<DltMasterRankVo> getDltRankedMasters(DltRankQuery query) {
        String period = dltMasterMapper.latestDltRankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(dltMasterMapper::countDltMasterRankList)
                    .query(dltMasterMapper::getDltMasterRankList);
    }

    @Override
    public List<DltIcaiHistoryVo> getDltMasterHistories(String masterId) {
        int exist = masterInfoMapper.existMasterLottery(masterId, LotteryEnum.DLT.getType());
        Assert.state(exist == 1, ResponseHandler.MASTER_NONE);
        String period = dltMasterMapper.latestDltRatePeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        return dltIcaiMapper.getHistoryDltForecasts(masterId, period, 15);
    }

    @Override
    public DltMasterDetail getDltMasterDetail(String masterId, Long userId) {
        return dltMasterDetailQueryExe.execute(masterId, userId);
    }

    @Override
    public Map<String, List<HomeMasterVo>> getDltHomedMasters() {
        String period = dltMasterMapper.latestHomeMasterPeriod();
        if (period == null) {
            return Collections.emptyMap();
        }
        List<HomeMasterVo> masters = dltMasterMapper.getDltHomeMasters(period);
        return masters.stream().collect(Collectors.groupingBy(HomeMasterVo::getType));
    }

    @Override
    public Page<DltMasterSubscribeVo> getDltSubscribeMasters(SubscribeQuery query) {
        String period = dltMasterMapper.latestDltRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(dltMasterMapper::countDltMasterSubscribes)
                    .query(dltMasterMapper::getDltMasterSubscribes);
    }

    @Override
    public Page<LotteryMasterVo> getDltLotteryMasters(DltLottoMasterQuery query) {
        String period = query.getPeriod();
        if (StringUtils.isBlank(period)) {
            period = dltMasterMapper.latestDltRankPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period);
        }
        return query.from()
                    .setParam("last", LotteryEnum.DLT.lastPeriod(period))
                    .count(dltMasterMapper::countDltLottoMasters)
                    .query(dltMasterMapper::getDltLottoMasterList);
    }

    private Period ofNullable(String period) {
        Period iPeriod = Optional.ofNullable(period)
                                 .filter(StringUtils::isNotBlank)
                                 .map(Period::new)
                                 .orElseGet(dltIcaiMapper::latestDltICaiPeriod);
        return Assert.notNull(iPeriod, ResponseHandler.FORECAST_NONE);
    }

    @Override
    public SyntheticItemCensusVo itemChart(DltChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = dltForecastRepository.getForecastFullOrVipChart(ChartType.ITEM_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleItem(censuses);
    }

    @Override
    public SyntheticFullCensusVo fullChart(DltChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = dltForecastRepository.getForecastFullOrVipChart(ChartType.ALL_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleFull(censuses);
    }

    @Override
    public SyntheticVipCensusVo vipChart(DltChannel channel, String period) {
        Period                iPeriod  = this.ofNullable(period);
        List<BaseLottoCensus> censuses = dltForecastRepository.getForecastFullOrVipChart(ChartType.VIP_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleVip(censuses);
    }

    @Override
    public DltChartCensusVo rateChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<DltLottoCensusPo> censuses = dltForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.RATE_CHART);
        return LottoChartAssembler.assembleDltChart(censuses);
    }

    @Override
    public DltChartCensusVo hotChart(String period) {
        Period                 iPeriod  = this.ofNullable(period);
        List<DltLottoCensusPo> censuses = dltForecastRepository.getForecastHotOrRateChart(iPeriod, ChartType.HOT_CHART);
        return LottoChartAssembler.assembleDltChart(censuses);
    }

    @Override
    public List<DltMasterSchemaVo> getDltSchemaMasters(Integer limit) {
        String period = dltMasterMapper.latestDltRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        return dltMasterMapper.getDltSchemaMasters(period, limit);
    }

    @Override
    public List<SchemaRenewMasterVo> schemaRenewMasters() {
        if (LotteryEnum.DLT.issueLimit(LocalDateTime.now())) {
            return Collections.emptyList();
        }
        final Period period = dltIcaiMapper.latestDltICaiPeriod();
        if (period == null || period.getCalculated() == 1) {
            return Collections.emptyList();
        }
        String                    renewPeriod = period.getPeriod();
        String                    lastPeriod  = PeriodCalculator.dltPeriod(renewPeriod, 1);
        List<SchemaRenewMasterVo> masters     = dltIcaiMapper.getDltSchemaRenewMasters(renewPeriod, lastPeriod, 5);
        masters.forEach(master -> master.setType(LotteryEnum.DLT));
        return masters;
    }

    @Override
    public List<MasterBattleVo> getDltMasterBattles(Long userId) {
        Period period = dltIcaiMapper.latestDltICaiPeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        List<MasterBattleVo> battles = battleMapper.getUserMasterBattles(userId, LotteryEnum.DLT.getType(), period.getPeriod());
        if (CollectionUtils.isEmpty(battles)) {
            return Collections.emptyList();
        }
        List<String> masterIds = battles.stream()
                                        .map(battle -> battle.getMaster().getMasterId())
                                        .collect(Collectors.toList());
        List<DltIcaiPo>        forecasts   = dltIcaiMapper.getDltICaiListByMasters(period.getPeriod(), masterIds);
        Map<String, DltIcaiPo> forecastMap = Maps.uniqueIndex(forecasts, DltIcaiPo::getMasterId);
        battles.forEach(battle -> {
            DltIcaiPo forecast = forecastMap.get(battle.getMaster().getMasterId());
            battle.setForecast(forecast);
        });
        return battles;
    }

    @Override
    public Page<MasterBattleRankVo<DltMasterMulRankVo>> getDltBattleMasterRanks(MasterBattleRankQuery query) {
        String period = dltMasterMapper.latestDltRankPeriod();
        if (StringUtils.isBlank(period)) {
            return Page.empty(query.getLimit());
        }
        Period               latest    = dltIcaiMapper.latestDltICaiPeriod();
        List<MasterBrowsePo> browses   = masterInfoMapper.getUserForecastBrowses(query.getUserId(), latest.getPeriod(), LotteryEnum.DLT.getType());
        List<String>         masterIds = browses.stream().map(MasterBrowsePo::getSourceId).toList();
        return query.from()
                    .setParam("type", 0)
                    .setParam("period", period)
                    .setParam("last", LotteryEnum.DLT.lastPeriod(period))
                    .count(dltMasterMapper::countDltMasterMulRanks)
                    .query(dltMasterMapper::getDltMasterMulRankList)
                    .map(rank -> {
                        boolean contained = masterIds.contains(rank.getMaster().getMasterId());
                        return new MasterBattleRankVo<>(contained ? 1 : 0, rank);
                    });
    }

    @Override
    public DltMasterRateVo getDltMasterRate(String masterId) {
        String period = dltMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return dltMasterMapper.getDltMasterRateVo(period, masterId);
    }

    @Override
    public List<DltICaiHitVo> getDltMasterBeforeHits(String masterId) {
        String period = dltMasterMapper.latestMasterRatePeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return dltIcaiMapper.getDltLast10HitList(period, masterId);
    }

}
