package com.prize.lottery.domain.ssq.ability;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.DomainEventPublisher;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.glad.event.GladExtractEvent;
import com.prize.lottery.domain.ssq.assembler.SsqICaiAssembler;
import com.prize.lottery.domain.ssq.model.SsqCensusDo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.mapper.SsqMasterMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.ssq.*;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsqICaiDomainAbility implements ICaiDomainAbility {

    private final SsqIcaiMapper     ssqIcaiMapper;
    private final SsqMasterMapper   ssqMasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final SsqICaiAssembler  ssqICaiAssembler;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.SSQ;
    }

    /**
     * 计算指定期的预测数据命中信息
     */
    @Override
    public void calcForecastHit(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(ssqIcaiMapper.latestSsqICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(ssqIcaiMapper.hasSsqPeriodOpenCalc(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);

        //双色球指定期的开奖号码
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.SSQ.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);
        //指定期的预测数据
        List<SsqIcaiPo> datas = ssqIcaiMapper.getAllSsqICaiDatas(period);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);
        //计算预测数据命中信息
        List<String> reds = lottery.redBalls();
        String       blue = lottery.getBlue().trim();
        datas.forEach(v -> v.calcHit(reds, blue));
        ssqIcaiMapper.editSsqICaiList(datas);
    }

    @Override
    public void calcIncrHit(String period) {
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.SSQ.getType(), period);
        if (lottery != null) {
            List<SsqIcaiPo> datas = ssqIcaiMapper.getAllUnCalcDatas(period);
            if (!CollectionUtils.isEmpty(datas)) {
                List<String> reds = lottery.redBalls();
                String       blue = lottery.getBlue().trim();
                datas.forEach(v -> v.calcHit(reds, blue));
                ssqIcaiMapper.editSsqICaiList(datas);
            }
        }
    }

    @Override
    public void calcIncrMasterRate(String period) {
        List<String> masters = ssqIcaiMapper.getIncrUnCalcRateMasters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        List<SsqMasterRatePo> rates = masters.parallelStream()
                                             .map(id -> calcRate(id, period))
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());
        ssqMasterMapper.addSsqMasterRates(rates);
    }

    /**
     * 计算专家预测数据命中率
     */
    @Override
    public void calcMasterRate(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(ssqIcaiMapper.latestSsqICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(ssqMasterMapper.hasExistRatePeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        String current = period;

        List<String> masterIds = ssqIcaiMapper.getSsqMasterIdsByPeriod(current);
        if (CollectionUtils.isEmpty(masterIds)) {
            return;
        }
        List<SsqMasterRatePo> rates = masterIds.parallelStream()
                                               .map(id -> calcRate(id, current))
                                               .filter(Objects::nonNull)
                                               .collect(Collectors.toList());
        ssqMasterMapper.addSsqMasterRates(rates);

    }

    private SsqMasterRatePo calcRate(String masterId, String period) {
        List<SsqIcaiPo> list = ssqIcaiMapper.getSsqICaiHitsBefore(masterId, period, 30);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return new SsqMasterRatePo().calcRate(list);
    }

    /**
     * 计算专家排名
     */
    @Override
    public void calcMasterRank(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(ssqMasterMapper.latestSsqRatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(ssqMasterMapper.hasExistRankMaster(period) == 0, ResponseHandler.HAS_RANKED_DATA);

        List<SsqMasterRatePo> rates = ssqMasterMapper.getSsqMasterRates(period);
        if (CollectionUtils.isEmpty(rates)) {
            return;
        }
        //计算得分权重
        List<SsqMasterRankPo> ranks = rates.parallelStream()
                                           .map(rate -> new SsqMasterRankPo().calcRank(rate))
                                           .collect(Collectors.toList());
        //计算综合排名
        List<RankValue> values = ranks.stream().map(SsqMasterRankPo::getRank).collect(Collectors.toList());
        ICaiConstants.sortRank(values);
        //计算单项排名
        Arrays.stream(SsqChannel.values())
              .map(channel -> ranks.stream().map(channel::rankValue).collect(Collectors.toList()))
              .forEach(ICaiConstants::sortRank);
        ssqMasterMapper.addSsqMasterRanks(ranks);

    }

    /**
     * 计算上首页专家
     */
    @Override
    public void calcHomeMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(ssqMasterMapper.latestSsqRankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(ssqMasterMapper.hasExtractHomeMaster(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        String current = period;
        List<SsqHomeMasterPo> masterPos = Arrays.stream(SsqChannel.values())
                                                .filter(SsqChannel::isVipChannel)
                                                .flatMap(channel -> {
                                                    List<SsqMasterRatePo> rates = ssqMasterMapper.getSsqRankedMasterRates(current, channel.getChannel(), 6);
                                                    return IntStream.range(0, 6).mapToObj(index -> {
                                                        SsqMasterRatePo rate   = rates.get(index);
                                                        SsqHomeMasterPo master = new SsqHomeMasterPo();
                                                        master.setRank(index + 1);
                                                        master.setType(channel);
                                                        master.setPeriod(rate.getPeriod());
                                                        master.setMasterId(rate.getMasterId());
                                                        master.setRate(channel.rateValue(rate));
                                                        return master;
                                                    });
                                                })
                                                .collect(Collectors.toList());
        ssqMasterMapper.addSsqHomeMasters(masterPos);
    }

    /**
     * 计算vip专家
     */
    @Override
    @Transactional
    public void calcVipMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(ssqMasterMapper.latestSsqRankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(ssqMasterMapper.hasExtractVipMaster(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        List<String> types = Arrays.stream(SsqChannel.values())
                                   .filter(SsqChannel::isVipChannel)
                                   .map(SsqChannel::getChannel)
                                   .collect(Collectors.toList());
        List<SsqMasterRankPo> masterRanks = ssqMasterMapper.extractVipMasters(period, types, 50, 30);
        if (CollectionUtils.isEmpty(masterRanks)) {
            return;
        }
        List<String> masterIds = masterRanks.stream()
                                            .map(SsqMasterRankPo::getMasterId)
                                            .distinct()
                                            .collect(Collectors.toList());

        //vip专家计算
        ssqMasterMapper.editSsqVipMasters(period, masterIds);

        //热门专家计算
        this.calcHotMaster(period);
    }

    @Override
    public void calcHotMaster(String period) {
        String       last       = PeriodCalculator.ssqPeriod(period, 1);
        String       start      = PeriodCalculator.ssqPeriod(period, 10);
        List<String> hotMasters = ssqIcaiMapper.getSsqHotMasters(period, last, start, 4);
        if (!CollectionUtils.isEmpty(hotMasters)) {
            ssqIcaiMapper.extractSsqHotMasters(period, hotMasters);
        }
    }

    /**
     * 全量计算数据统计
     */
    @Override
    public void calcAllCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.ssqPeriod(current, 1);

        List<SsqCensusDo> censuses = Arrays.stream(SsqChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = ssqIcaiMapper.getAllSsqRankedDatas(channel.getChannel(), current, lastPeriod);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return SsqCensusDo.calcFull(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<SsqLottoCensusPo> censusPos = ssqICaiAssembler.toPos(censuses);
        ssqIcaiMapper.addSsqCensuses(censusPos);
    }

    @Override
    public void calcItemCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.ssqPeriod(current, 1);

        List<SsqCensusDo> censuses = Arrays.stream(SsqChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = ssqIcaiMapper.getLimitSsqRankedDatas(channel.getChannel(), current, lastPeriod, 200);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return SsqCensusDo.calcItem(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<SsqLottoCensusPo> censusPos = ssqICaiAssembler.toPos(censuses);
        ssqIcaiMapper.addSsqCensuses(censusPos);
    }

    /**
     * 计算热门专家统计数据
     *
     * @param period 指定期号
     */
    @Override
    public void calcHotMasterChart(String period) {
        String current = getCensusCalcPeriod(period);
        String last    = PeriodCalculator.ssqPeriod(current, 1);

        List<SsqIcaiPo> datas = ssqIcaiMapper.getSsqHotMasterDatas(current, last);
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        List<SsqCensusDo> censusDos = Arrays.stream(SsqChannel.values())
                                            .filter(SsqChannel::isHotChannel)
                                            .map(channel -> {
                                                List<String> recs = datas.stream()
                                                                         .map(v -> channel.forecastValue(v).getData())
                                                                         .collect(Collectors.toList());
                                                return new SsqCensusDo().create(current, channel, ChartType.HOT_CHART)
                                                                        .calcCensus(recs);
                                            })
                                            .collect(Collectors.toList());

        List<SsqLottoCensusPo> censusPos = ssqICaiAssembler.toPos(censusDos);
        ssqIcaiMapper.addSsqCensuses(censusPos);

    }

    private List<String> channelVipData(SsqChannel channel, String current, String lastPeriod) {
        List<SsqIcaiPo> datas = ssqIcaiMapper.getSsqVipItemDatas(channel.getChannel(), current, lastPeriod);
        return datas.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());

    }

    /**
     * 计算vip专家统计图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcVipMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.ssqPeriod(current, 1);

        List<SsqCensusDo> censusDos = Arrays.stream(SsqChannel.values())
                                            .filter(SsqChannel::isVipChannel)
                                            .flatMap(channel -> {
                                                List<String> vipData = channelVipData(channel, current, lastPeriod);
                                                return SsqCensusDo.calcVip(current, channel, vipData).stream();
                                            })
                                            .collect(Collectors.toList());

        List<SsqLottoCensusPo> censusPos = ssqICaiAssembler.toPos(censusDos);
        ssqIcaiMapper.addSsqCensuses(censusPos);
    }

    private List<String> highRateData(SsqChannel channel, String current, String lastPeriod) {
        List<SsqIcaiPo> datas = ssqIcaiMapper.getSsqHighRateDatas(current, lastPeriod, channel.getChannel(), channel.rateThrottle());
        return datas.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());
    }

    /**
     * 计算高命中率专家统计
     *
     * @param period 指定期号
     */
    @Override
    public void calcRateMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.ssqPeriod(current, 1);

        List<SsqCensusDo> censusDos = Arrays.stream(SsqChannel.values())
                                            .filter(channel -> channel.rateThrottle() < 1)
                                            .map(channel -> {
                                                List<String> highRateData = highRateData(channel, current, lastPeriod);
                                                return new SsqCensusDo().create(current, channel, ChartType.RATE_CHART)
                                                                        .calcCensus(highRateData);
                                            })
                                            .collect(Collectors.toList());

        List<SsqLottoCensusPo> censusPos = ssqICaiAssembler.toPos(censusDos);
        ssqIcaiMapper.addSsqCensuses(censusPos);
    }

    /**
     * 提取最新期的专家中奖喜讯
     */
    @Override
    public void extractMasterGlad() {
        GladExtractEvent extractEvent = new GladExtractEvent(LotteryEnum.SSQ);
        DomainEventPublisher.publish(extractEvent);
    }

    private String getCensusCalcPeriod(String period) {
        if (StringUtils.isNotBlank(period)) {
            Assert.state(ssqIcaiMapper.hasSsqICaiData(period) > 0, ResponseHandler.NO_FORECAST_DATA);
            return period;
        }
        Period iPeriod = Assert.notNull(ssqIcaiMapper.latestSsqICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
        return iPeriod.getPeriod();
    }

}
