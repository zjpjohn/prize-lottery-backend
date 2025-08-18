package com.prize.lottery.domain.fc3d.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.executor.Fc3dComRecommendCalcExe;
import com.prize.lottery.domain.DomainEventPublisher;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.fc3d.assembler.Fc3dICaiAssembler;
import com.prize.lottery.domain.fc3d.model.Fc3dCensusDo;
import com.prize.lottery.domain.glad.event.GladExtractEvent;
import com.prize.lottery.dto.ChannelRate;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.fc3d.*;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.ForecastValue;
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
public class Fc3dICaiDomainAbility implements ICaiDomainAbility {

    private final Fc3dIcaiMapper          fc3dIcaiMapper;
    private final Fc3dMasterMapper        fc3dMasterMapper;
    private final LotteryInfoMapper       lotteryInfoMapper;
    private final Fc3dICaiAssembler       fc3dIcaiAssembler;
    private final Fc3dComRecommendCalcExe fc3dComRecommendCalcExe;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.FC3D;
    }

    @Override
    public void calcForecastHit(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(fc3dIcaiMapper.latestFc3dIcaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        //本期开奖数据
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);
        //本期预测数据
        List<Fc3dIcaiPo> datas = fc3dIcaiMapper.getAllUnCalcDatas(period);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);
        List<String> balls = Lists.newArrayList(lottery.getRed().split("\\s+"));
        //开奖计算预测命中信息
        datas.forEach(v -> v.calcHit(balls));
        fc3dIcaiMapper.editFc3dIcaiList(datas);
    }

    @Override
    public void calcIncrHit(String period) {
        //指定期开奖数据
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), period);
        if (lottery != null) {
            List<Fc3dIcaiPo> datas = fc3dIcaiMapper.getAllUnCalcDatas(period);
            if (!CollectionUtils.isEmpty(datas)) {
                List<String> balls = Lists.newArrayList(lottery.getRed().split("\\s+"));
                datas.forEach(v -> v.calcHit(balls));
                fc3dIcaiMapper.editFc3dIcaiList(datas);
            }
        }
    }

    @Override
    public void calcIncrMasterRate(String period) {
        List<String> masters = fc3dIcaiMapper.getIncrUnCalcRateMasters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        List<Fc3dMasterRatePo> rates = masters.parallelStream()
                                              .map(id -> calcRate(id, period))
                                              .filter(Objects::nonNull)
                                              .collect(Collectors.toList());
        fc3dMasterMapper.addFc3dMasterRates(rates);
    }

    @Override
    public void calcMasterRate(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(fc3dIcaiMapper.latestFc3dIcaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        final String current   = period;
        List<String> masterIds = fc3dIcaiMapper.getFc3dMasterIdsByPeriod(current);
        if (!CollectionUtils.isEmpty(masterIds)) {
            List<Fc3dMasterRatePo> rates = masterIds.parallelStream()
                                                    .map(id -> calcRate(id, current))
                                                    .filter(Objects::nonNull)
                                                    .collect(Collectors.toList());
            fc3dMasterMapper.addFc3dMasterRates(rates);
        }
    }

    private Fc3dMasterRatePo calcRate(String masterId, String period) {
        List<Fc3dIcaiPo> list = fc3dIcaiMapper.getFc3dICaiHitsBefore(masterId, period, 30);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return new Fc3dMasterRatePo().calcMasterRate(list);
    }

    @Override
    public void calcMasterRank(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(fc3dMasterMapper.latestFc3dRatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(fc3dMasterMapper.hasExistRankPeriod(period) == 0, ResponseHandler.HAS_RANKED_DATA);
        List<Fc3dMasterRatePo> rates = fc3dMasterMapper.getFc3dMasterRates(period);
        if (CollectionUtils.isEmpty(rates)) {
            return;
        }
        //计算排名权重得分
        List<Fc3dMasterRankPo> rankList = rates.parallelStream()
                                               .map(rate -> new Fc3dMasterRankPo().calc(rate))
                                               .collect(Collectors.toList());
        //综合排名计算
        List<RankValue> rankValues = rankList.stream().map(Fc3dMasterRankPo::getRank).collect(Collectors.toList());
        ICaiConstants.sortRank(rankValues);
        //单项排名计算
        Arrays.stream(Fc3dChannel.values())
              .map(channel -> rankList.stream().map(channel::rankValue).collect(Collectors.toList()))
              .forEach(ICaiConstants::sortRank);
        fc3dMasterMapper.addFc3dMasterRanks(rankList);

    }

    @Override
    public void calcHomeMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(fc3dMasterMapper.latestFc3dRankPeriod(), ResponseHandler.NO_RANKED_DATA);
        }
        Assert.state(fc3dMasterMapper.hasExtractHomeMaster(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        final String current = period;
        List<Fc3dHomeMasterPo> homeMasters = Arrays.stream(Fc3dChannel.values())
                                                   .filter(Fc3dChannel::isVipChannel)
                                                   .flatMap(channel -> {
                                                       List<Fc3dMasterRatePo> items = fc3dMasterMapper.getFc3dRankedMasterRates(current, channel.getChannel(), 6);
                                                       return IntStream.range(0, 6).mapToObj(index -> {
                                                           Fc3dMasterRatePo rate   = items.get(index);
                                                           Fc3dHomeMasterPo master = new Fc3dHomeMasterPo();
                                                           master.setRank(index + 1);
                                                           master.setType(channel);
                                                           master.setPeriod(rate.getPeriod());
                                                           master.setMasterId(rate.getMasterId());
                                                           master.setRate(channel.rateValue(rate));
                                                           return master;
                                                       });
                                                   })
                                                   .collect(Collectors.toList());
        fc3dMasterMapper.addFc3dHomeMasters(homeMasters);
    }

    @Override
    @Transactional
    public void calcVipMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(fc3dMasterMapper.latestFc3dRankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(fc3dMasterMapper.hasExtractVipPeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);

        List<String> types = Arrays.stream(Fc3dChannel.values())
                                   .filter(Fc3dChannel::isVipChannel)
                                   .map(Fc3dChannel::getChannel)
                                   .collect(Collectors.toList());
        List<Fc3dMasterRankPo> masterRanks = fc3dMasterMapper.extractVipMasters(period, types, 50, 30);
        if (CollectionUtils.isEmpty(masterRanks)) {
            return;
        }
        List<String> masterIds = masterRanks.stream()
                                            .map(Fc3dMasterRankPo::getMasterId)
                                            .distinct()
                                            .collect(Collectors.toList());
        //设置专家为vip
        fc3dMasterMapper.editFc3dVipMasters(period, masterIds);
        //热门专家计算
        this.calcHotMaster(period);
    }

    @Override
    public void calcHotMaster(String period) {
        //热门专家计算
        String       last       = PeriodCalculator.fc3dPeriod(period, 1);
        String       start      = PeriodCalculator.fc3dPeriod(period, 10);
        List<String> hotMasters = fc3dIcaiMapper.getFc3dHotMasters(period, last, start, 4);
        if (!CollectionUtils.isEmpty(hotMasters)) {
            fc3dIcaiMapper.extractFc3dHotMasters(period, hotMasters);
        }
    }

    /**
     * 计算指定期的全量统计图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcAllCensusChart(String period) {
        final String current    = getCensusCalcPeriod(period);
        String       lastPeriod = PeriodCalculator.fc3dPeriod(current, 1);
        List<Fc3dCensusDo> censusDos = Arrays.stream(Fc3dChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = fc3dIcaiMapper.getAllFc3dRankedDatas(channel.getChannel(), current, lastPeriod);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return Fc3dCensusDo.calcFull(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<Fc3dLottoCensusPo> censusPoList = fc3dIcaiAssembler.toPos(censusDos);
        fc3dIcaiMapper.addFc3dCensuses(censusPoList);
    }

    @Override
    public void calcItemCensusChart(String period) {
        final String current    = getCensusCalcPeriod(period);
        String       lastPeriod = PeriodCalculator.fc3dPeriod(current, 1);

        List<Fc3dCensusDo> censusDos = Arrays.stream(Fc3dChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = fc3dIcaiMapper.getLimitFc3dRankedDatas(channel.getChannel(), current, lastPeriod, 200);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return Fc3dCensusDo.calcItem(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<Fc3dLottoCensusPo> censusPoList = fc3dIcaiAssembler.toPos(censusDos);
        fc3dIcaiMapper.addFc3dCensuses(censusPoList);
    }

    /**
     * 计算高命中率专家统计图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcRateMasterChart(String period) {
        final String current    = getCensusCalcPeriod(period);
        String       lastPeriod = PeriodCalculator.fc3dPeriod(current, 1);

        List<Fc3dChannel> channels = Arrays.stream(Fc3dChannel.values())
                                           .filter(v -> v.rateThrottle() < 1.0)
                                           .collect(Collectors.toList());
        List<ChannelRate> rates = channels.stream()
                                          .map(v -> new ChannelRate(v.getChannel(), v.rateThrottle()))
                                          .collect(Collectors.toList());
        List<Fc3dIcaiPo> datas = fc3dIcaiMapper.getAllHighRateFc3dDatas(period, lastPeriod, rates);

        List<Fc3dCensusDo> censusDos = Arrays.stream(Fc3dChannel.values())
                                             .filter(Fc3dChannel::isSingle)
                                             .map(channel -> {
                                                 List<String> values = datas.stream()
                                                                            .map(channel::forecastValue)
                                                                            .map(ForecastValue::getData)
                                                                            .collect(Collectors.toList());
                                                 return new Fc3dCensusDo().create(period, channel, ChartType.RATE_CHART)
                                                                          .calcCensus(values);
                                             })
                                             .collect(Collectors.toList());

        List<Fc3dLottoCensusPo> censusPos = fc3dIcaiAssembler.toPos(censusDos);
        fc3dIcaiMapper.addFc3dCensuses(censusPos);

    }

    /**
     * 计算热门专家统计图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcHotMasterChart(String period) {
        final String     current = getCensusCalcPeriod(period);
        String           last    = PeriodCalculator.fc3dPeriod(current, 1);
        List<Fc3dIcaiPo> datas   = fc3dIcaiMapper.getFc3dHotMasterDatas(current, last);
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        List<Fc3dCensusDo> censusDos = Arrays.stream(Fc3dChannel.values())
                                             .filter(Fc3dChannel::isHotChannel)
                                             .map(channel -> {
                                                 List<String> recs = datas.stream()
                                                                          .map(v -> channel.forecastValue(v).getData())
                                                                          .collect(Collectors.toList());
                                                 return new Fc3dCensusDo().create(current, channel, ChartType.HOT_CHART)
                                                                          .calcCensus(recs);
                                             })
                                             .collect(Collectors.toList());

        List<Fc3dLottoCensusPo> censusPos = fc3dIcaiAssembler.toPos(censusDos);
        fc3dIcaiMapper.addFc3dCensuses(censusPos);
    }

    /**
     * 计算预警推荐命中
     */
    @Override
    public void calcComRecommend(String period) {
        fc3dComRecommendCalcExe.execute(period);
    }

    /**
     * 提取最新期的专家中奖喜讯
     */
    @Override
    public void extractMasterGlad() {
        GladExtractEvent extractEvent = new GladExtractEvent(LotteryEnum.FC3D);
        DomainEventPublisher.publish(extractEvent);
    }

    private List<String> channelVipData(Fc3dChannel channel, String current, String lastPeriod) {
        List<Fc3dIcaiPo> datas = fc3dIcaiMapper.getFc3dVipItemDatas(channel.getChannel(), current, lastPeriod);
        return datas.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());
    }

    /**
     * 计算vip专家统计图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcVipMasterChart(String period) {
        //获取可统计的期号
        final String current    = getCensusCalcPeriod(period);
        String       lastPeriod = PeriodCalculator.fc3dPeriod(current, 1);
        List<Fc3dCensusDo> censusDos = Arrays.stream(Fc3dChannel.values())
                                             .filter(Fc3dChannel::isVipChannel)
                                             .flatMap(channel -> {
                                                 List<String> vipData = channelVipData(channel, current, lastPeriod);
                                                 return Fc3dCensusDo.calcVip(current, channel, vipData).stream();
                                             })
                                             .collect(Collectors.toList());

        List<Fc3dLottoCensusPo> censusPos = fc3dIcaiAssembler.toPos(censusDos);
        fc3dIcaiMapper.addFc3dCensuses(censusPos);
    }

    private String getCensusCalcPeriod(String period) {
        if (StringUtils.isNotBlank(period)) {
            Assert.state(fc3dIcaiMapper.hasFc3dICaiData(period) > 0, ResponseHandler.NO_FORECAST_DATA);
            return period;
        }
        Period iPeriod = Assert.notNull(fc3dIcaiMapper.latestFc3dIcaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
        return iPeriod.getPeriod();
    }

}

