package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.query.dto.Kl8LottoMasterQuery;
import com.prize.lottery.application.query.dto.Kl8RankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.executor.Kl8MasterDetailQueryExe;
import com.prize.lottery.application.query.service.IKl8LottoQueryService;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Kl8IcaiMapper;
import com.prize.lottery.mapper.Kl8MasterMapper;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.po.kl8.Kl8MasterInfoPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;
import com.prize.lottery.vo.kl8.Kl8MasterDetail;
import com.prize.lottery.vo.kl8.Kl8MasterRankVo;
import com.prize.lottery.vo.kl8.Kl8MasterSubscribeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Kl8LottoQueryService implements IKl8LottoQueryService {

    private final Kl8IcaiMapper                                        kl8IcaiMapper;
    private final Kl8MasterMapper                                      kl8MasterMapper;
    private final Kl8MasterDetailQueryExe                              kl8MasterDetailQueryExe;
    private final IBrowseForecastRepository<Kl8IcaiInfoPo, Kl8Channel> kl8ForecastRepository;

    @Override

    public Period latestPeriod() {
        return kl8ForecastRepository.latestICaiPeriod();
    }

    @Override
    public List<String> lastPeriods(Integer size) {
        Period period = kl8IcaiMapper.latestKl8IcaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        return LotteryEnum.KL8.lastPeriods(period.getPeriod(), size);
    }

    /**
     * 最新排名期号
     */
    @Override
    public String latestRank() {
        return kl8MasterMapper.latestKl8RankPeriod();
    }

    @Override
    public Kl8MasterDetail queryMasterDetail(String masterId, Long userId) {
        return kl8MasterDetailQueryExe.execute(masterId, userId);
    }

    /**
     * 分页查询快乐8预测专家
     *
     * @param query 查询条件
     */
    @Override
    public Page<Kl8MasterInfoPo> getKl8MasterList(PageQuery query) {
        return query.from().count(kl8MasterMapper::countKl8MasterList).query(kl8MasterMapper::getKl8MasterList);
    }

    /**
     * 查询排序的预测数据
     *
     * @param query 查询条件
     */
    @Override
    public Page<ICaiRankedDataVo> getKl8RankedDataList(Kl8RankQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            final Period period = kl8IcaiMapper.latestKl8IcaiPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period.getPeriod());
        }
        return query.from().count(kl8IcaiMapper::countKl8RankedDatas).query(kl8IcaiMapper::getKl8RankedDatas);
    }

    /**
     * 分页查询专家排行榜
     *
     * @param query 查询条件
     */
    @Override
    public Page<Kl8MasterRankVo> getKl8MasterRankList(PageQuery query) {
        String period = kl8MasterMapper.latestKl8RankPeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from()
                    .setParam("period", period)
                    .setParam("last", PeriodCalculator.fc3dPeriod(period, 1))
                    .count(kl8MasterMapper::countKl8MasterRanks)
                    .query(kl8MasterMapper::getKl8MasterRankList);
    }

    /**
     * 查询专家预测历史数据
     *
     * @param masterId 专家标识
     */
    @Override
    public List<Kl8IcaiInfoPo> getMasterForecastHistories(String masterId) {
        int exist = kl8MasterMapper.existKl8MasterInfo(masterId);
        Assert.state(exist == 1, ResponseHandler.MASTER_NONE);
        String period = kl8MasterMapper.latestKl8RatePeriod();
        if (period == null) {
            return Collections.emptyList();
        }
        return kl8IcaiMapper.getKl8HistoryForecasts(masterId, period, 10);
    }

    @Override
    public Page<Kl8MasterSubscribeVo> getKl8MasterSubscribeList(SubscribeQuery query) {
        String period = kl8MasterMapper.latestKl8RatePeriod();
        if (period == null) {
            return Page.empty(query.getLimit());
        }
        return query.from(period)
                    .count(kl8MasterMapper::countKl8MasterSubscribes)
                    .query(kl8MasterMapper::getKl8MasterSubscribeList);
    }

    /**
     * 查询全量统计图表
     *
     * @param channel 数据指标字段
     * @param period  预测期号
     */
    @Override
    public Kl8FullCensusVo fullChart(Kl8Channel channel, String period) {
        Period iPeriod = Optional.ofNullable(period)
                                 .filter(StringUtils::isNotBlank)
                                 .map(Period::new)
                                 .orElseGet(() -> Assert.notNull(kl8IcaiMapper.latestKl8IcaiPeriod(), "没有预测数据"));
        List<Kl8LottoCensusPo> censusPos = kl8ForecastRepository.getForecastFullOrVipChart(ChartType.ALL_CHART, iPeriod, channel);
        return LottoChartAssembler.assembleKl8Chart(censusPos);
    }

    /**
     * 分页查询快乐8排名专家
     *
     * @param query 查询条件
     */
    @Override
    public Page<LotteryMasterVo> getKl8LotteryMasters(Kl8LottoMasterQuery query) {
        if (StringUtils.isBlank(query.getPeriod())) {
            String period = kl8MasterMapper.latestKl8RankPeriod();
            if (period == null) {
                return Page.empty(query.getLimit());
            }
            query.setPeriod(period);
        }
        return query.from().count(kl8MasterMapper::countKl8LottoMasters).query(kl8MasterMapper::getKl8LottoMasterList);
    }

}
