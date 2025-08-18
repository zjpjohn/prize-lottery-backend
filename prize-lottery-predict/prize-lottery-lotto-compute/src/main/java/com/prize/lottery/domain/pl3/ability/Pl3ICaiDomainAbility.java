package com.prize.lottery.domain.pl3.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.executor.Pl3ComRecommendCalcExe;
import com.prize.lottery.domain.DomainEventPublisher;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.glad.event.GladExtractEvent;
import com.prize.lottery.domain.pl3.assembler.Pl3ICaiAssembler;
import com.prize.lottery.domain.pl3.model.Pl3CensusDo;
import com.prize.lottery.dto.ChannelRate;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.pl3.*;
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
public class Pl3ICaiDomainAbility implements ICaiDomainAbility {

    private final Pl3IcaiMapper          pl3IcaiMapper;
    private final Pl3MasterMapper        pl3MasterMapper;
    private final LotteryInfoMapper      lotteryInfoMapper;
    private final Pl3ICaiAssembler       pl3ICaiAssembler;
    private final Pl3ComRecommendCalcExe pl3ComRecommendCalcExe;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.PL3;
    }

    /**
     * 计算指定期的预测数据命中信息
     */
    @Override
    public void calcForecastHit(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(pl3IcaiMapper.latestPl3ICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(pl3IcaiMapper.hasPl3PeriodOpenCalc(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        //指定期开奖数据
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);
        //指定期预测数
        List<Pl3IcaiPo> datas = pl3IcaiMapper.getAllPl3ICaiDatas(period);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);
        //计算预测数据开奖命中信息
        List<String> balls = Lists.newArrayList(lottery.getRed().split("\\s+"));
        datas.forEach(v -> v.calcHit(balls));
        pl3IcaiMapper.editPl3IcaiList(datas);
    }

    @Override
    public void calcIncrHit(String period) {
        //指定期开奖数据
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), period);
        if (lottery != null) {
            List<Pl3IcaiPo> datas = pl3IcaiMapper.getAllUnCalcDatas(period);
            if (!CollectionUtils.isEmpty(datas)) {
                List<String> balls = Lists.newArrayList(lottery.getRed().split("\\s+"));
                datas.forEach(v -> v.calcHit(balls));
                pl3IcaiMapper.editPl3IcaiList(datas);
            }
        }
    }

    @Override
    public void calcIncrMasterRate(String period) {
        //获取增量未计算命中率的专家
        List<String> masters = pl3IcaiMapper.getIncrUnCalcRateMasters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        //并行计算专家命中率
        List<Pl3MasterRatePo> masterRates = masters.parallelStream()
                                                   .map(id -> calcRate(id, period))
                                                   .filter(Objects::nonNull)
                                                   .collect(Collectors.toList());
        pl3MasterMapper.addPl3MasterRates(masterRates);
    }

    /**
     * 计算专家预测数据命中率
     */
    @Override
    public void calcMasterRate(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(pl3IcaiMapper.latestPl3ICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        String       current   = period;
        List<String> masterIds = pl3IcaiMapper.getPl3MasterIdsByPeriod(current);
        if (!CollectionUtils.isEmpty(masterIds)) {
            //并行计算专家命中率信息
            List<Pl3MasterRatePo> rates = masterIds.parallelStream()
                                                   .map(id -> calcRate(id, current))
                                                   .filter(Objects::nonNull)
                                                   .collect(Collectors.toList());
            pl3MasterMapper.addPl3MasterRates(rates);
        }
    }

    private Pl3MasterRatePo calcRate(String masterId, String period) {
        List<Pl3IcaiPo> list = pl3IcaiMapper.getPl3ICaiHitsBefore(masterId, period, 30);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return new Pl3MasterRatePo().calcRate(list);
    }

    /**
     * 计算专家排名
     */
    @Override
    public void calcMasterRank(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(pl3MasterMapper.latestPl3RatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(pl3MasterMapper.hasExistRankPeriod(period) == 0, ResponseHandler.HAS_RANKED_DATA);
        List<Pl3MasterRatePo> rates = pl3MasterMapper.getPl3MasterRates(period);
        if (CollectionUtils.isEmpty(rates)) {
            return;
        }
        //计算权重得分
        List<Pl3MasterRankPo> rankList = rates.parallelStream()
                                              .map(rate -> new Pl3MasterRankPo().calc(rate))
                                              .collect(Collectors.toList());
        //计算综合排名
        List<RankValue> values = rankList.stream().map(Pl3MasterRankPo::getRank).collect(Collectors.toList());
        ICaiConstants.sortRank(values);
        //计算单项排名
        Arrays.stream(Pl3Channel.values())
              .map(channel -> rankList.stream().map(channel::rankValue).collect(Collectors.toList()))
              .forEach(ICaiConstants::sortRank);
        pl3MasterMapper.addPl3MasterRanks(rankList);

    }

    /**
     * 计算上首页专家
     */
    @Override
    public void calcHomeMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(pl3MasterMapper.latestPl3RankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(pl3MasterMapper.hasExtractHomeMaster(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        final String current = period;
        List<Pl3HomeMasterPo> masterPos = Arrays.stream(Pl3Channel.values())
                                                .filter(Pl3Channel::isVipChannel)
                                                .flatMap(channel -> {
                                                    List<Pl3MasterRatePo> items = pl3MasterMapper.getPl3RankedMasterRates(current, channel.getChannel(), 6);
                                                    return IntStream.range(0, 6).mapToObj(index -> {
                                                        Pl3MasterRatePo rate   = items.get(index);
                                                        Pl3HomeMasterPo master = new Pl3HomeMasterPo();
                                                        master.setRank(index + 1);
                                                        master.setType(channel);
                                                        master.setPeriod(rate.getPeriod());
                                                        master.setMasterId(rate.getMasterId());
                                                        master.setRate(channel.rateValue(rate));
                                                        return master;
                                                    });
                                                })
                                                .collect(Collectors.toList());
        pl3MasterMapper.addPl3HomeMasters(masterPos);
    }

    /**
     * 计算vip专家
     */
    @Override
    @Transactional
    public void calcVipMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(pl3MasterMapper.latestPl3RankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(pl3MasterMapper.hasExtractVipPeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);

        //提取vip专家的数据指标渠道
        List<String> types = Arrays.stream(Pl3Channel.values())
                                   .filter(Pl3Channel::isVipChannel)
                                   .map(Pl3Channel::getChannel)
                                   .collect(Collectors.toList());
        List<Pl3MasterRankPo> masterRanks = pl3MasterMapper.extractVipMasters(period, types, 50, 30);
        if (CollectionUtils.isEmpty(masterRanks)) {
            return;
        }

        List<String> masterIds = masterRanks.stream()
                                            .map(Pl3MasterRankPo::getMasterId)
                                            .distinct()
                                            .collect(Collectors.toList());
        //设置专家为vip专家
        pl3MasterMapper.editPl3VipMasters(period, masterIds);

        //计算热门专家
        this.calcHotMaster(period);
    }

    @Override
    public void calcHotMaster(String period) {
        String       last       = PeriodCalculator.pl3Period(period, 1);
        String       start      = PeriodCalculator.pl3Period(period, 10);
        List<String> hotMasters = pl3IcaiMapper.getPl3HotMasters(period, last, start, 4);
        if (!CollectionUtils.isEmpty(hotMasters)) {
            pl3IcaiMapper.extractPl3HotMasters(period, hotMasters);
        }
    }

    /**
     * 计算指定期的全量统计图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcAllCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.pl3Period(current, 1);
        List<Pl3CensusDo> censuses = Arrays.stream(Pl3Channel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = pl3IcaiMapper.getAllPl3RankedDatas(channel.getChannel(), current, lastPeriod);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return Pl3CensusDo.calcFull(current, channel, recs).stream();
        }).collect(Collectors.toList());
        List<Pl3LottoCensusPo> censusPos = pl3ICaiAssembler.toPos(censuses);
        pl3IcaiMapper.addPl3Censuses(censusPos);
    }

    @Override
    public void calcItemCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.pl3Period(current, 1);

        List<Pl3CensusDo> censuses = Arrays.stream(Pl3Channel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = pl3IcaiMapper.getLimitPl3RankedDatas(channel.getChannel(), current, lastPeriod, 200);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return Pl3CensusDo.calcItem(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<Pl3LottoCensusPo> censusPos = pl3ICaiAssembler.toPos(censuses);
        pl3IcaiMapper.addPl3Censuses(censusPos);
    }

    /**
     * 计算热门专家统计 图表
     *
     * @param period 指定期号
     */
    @Override
    public void calcHotMasterChart(String period) {
        String          current = getCensusCalcPeriod(period);
        String          last    = PeriodCalculator.pl3Period(current, 1);
        List<Pl3IcaiPo> datas   = pl3IcaiMapper.getPl3HotMasterDatas(current, last);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);
        List<Pl3CensusDo> censusDos = Arrays.stream(Pl3Channel.values())
                                            .filter(Pl3Channel::isHotChannel)
                                            .map(channel -> {
                                                List<String> recs = datas.stream()
                                                                         .map(v -> channel.forecastValue(v).getData())
                                                                         .collect(Collectors.toList());
                                                return new Pl3CensusDo().create(current, channel, ChartType.HOT_CHART)
                                                                        .calcCensus(recs);
                                            })
                                            .collect(Collectors.toList());
        List<Pl3LottoCensusPo> censusPos = pl3ICaiAssembler.toPos(censusDos);
        pl3IcaiMapper.addPl3Censuses(censusPos);
    }

    private List<String> channelVipData(Pl3Channel channel, String current, String lastPeriod) {
        List<Pl3IcaiPo> datas = pl3IcaiMapper.getPl3VipItemDatas(channel.getChannel(), current, lastPeriod);
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
        String lastPeriod = PeriodCalculator.pl3Period(current, 1);
        List<Pl3CensusDo> censusDos = Arrays.stream(Pl3Channel.values())
                                            .filter(Pl3Channel::isVipChannel)
                                            .flatMap(channel -> {
                                                List<String> vipData = channelVipData(channel, current, lastPeriod);
                                                return Pl3CensusDo.calcVip(current, channel, vipData).stream();
                                            })
                                            .collect(Collectors.toList());
        List<Pl3LottoCensusPo> censusPos = pl3ICaiAssembler.toPos(censusDos);
        pl3IcaiMapper.addPl3Censuses(censusPos);
    }

    /**
     * 计算指定期的高命中率专家统计
     *
     * @param period 指定期号
     */
    @Override
    public void calcRateMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.pl3Period(current, 1);

        List<Pl3Channel> channels = Arrays.stream(Pl3Channel.values()).filter(v -> v.rateThrottle() < 1.0).toList();
        List<ChannelRate> rates = channels.stream()
                                          .map(v -> new ChannelRate(v.getChannel(), v.rateThrottle()))
                                          .collect(Collectors.toList());
        List<Pl3IcaiPo> datas = pl3IcaiMapper.getAllPl3HighRateDatas(period, lastPeriod, rates);

        List<Pl3CensusDo> censusDos = Arrays.stream(Pl3Channel.values()).filter(Pl3Channel::isSingle).map(channel -> {
            List<String> values = datas.stream()
                                       .map(channel::forecastValue)
                                       .map(ForecastValue::getData)
                                       .collect(Collectors.toList());
            return new Pl3CensusDo().create(period, channel, ChartType.RATE_CHART).calcCensus(values);
        }).collect(Collectors.toList());

        List<Pl3LottoCensusPo> censusPos = pl3ICaiAssembler.toPos(censusDos);
        pl3IcaiMapper.addPl3Censuses(censusPos);
    }

    /**
     * 计算预警推荐命中
     */
    @Override
    public void calcComRecommend(String period) {
        pl3ComRecommendCalcExe.execute(period);
    }

    /**
     * 提取最新期的专家中奖喜讯
     */
    @Override
    public void extractMasterGlad() {
        GladExtractEvent extractEvent = new GladExtractEvent(LotteryEnum.PL3);
        DomainEventPublisher.publish(extractEvent);
    }

    private String getCensusCalcPeriod(String period) {
        if (StringUtils.isNotBlank(period)) {
            Assert.state(pl3IcaiMapper.hasPl3ICaiData(period) > 0, ResponseHandler.NO_FORECAST_DATA);
            return period;
        }
        Period iPeriod = Assert.notNull(pl3IcaiMapper.latestPl3ICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
        return iPeriod.getPeriod();
    }

}
