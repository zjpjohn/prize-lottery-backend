package com.prize.lottery.domain.dlt.ability;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.DomainEventPublisher;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.dlt.assembler.DltICaiAssembler;
import com.prize.lottery.domain.dlt.model.DltCensusDo;
import com.prize.lottery.domain.glad.event.GladExtractEvent;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.dlt.*;
import com.prize.lottery.po.lottery.LotteryInfoPo;
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
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DltICaiDomainAbility implements ICaiDomainAbility {

    private final DltIcaiMapper     dltIcaiMapper;
    private final DltMasterMapper   dltMasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final DltICaiAssembler  dltICaiAssembler;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.DLT;
    }

    /**
     * 计算指定期的预测数据命中信息
     */
    @Override
    public void calcForecastHit(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(dltIcaiMapper.latestDltICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(dltIcaiMapper.hasDltPeriodOpenCalc(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        //大乐透指定期开奖号码
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.DLT.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        //大乐透预测数据
        List<DltIcaiPo> datas = dltIcaiMapper.getAllDltICaiDatas(period);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);

        //计算预测数据命中信息
        this.calcDataHit(datas, lottery);
    }

    @Override
    public void calcIncrHit(String period) {
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.DLT.getType(), period);
        if (lottery != null) {
            List<DltIcaiPo> datas = dltIcaiMapper.getAllUnCalcDatas(period);
            if (!CollectionUtils.isEmpty(datas)) {
                //计算预测数据命中信息
                this.calcDataHit(datas, lottery);
            }
        }
    }

    private void calcDataHit(List<DltIcaiPo> dataList, LotteryInfoPo lottery) {
        List<String> reds  = lottery.redBalls();
        List<String> blues = lottery.blueBalls();
        dataList.forEach(data -> data.calcHit(reds, blues));
        dltIcaiMapper.editDltICaiList(dataList);
    }

    @Override
    public void calcIncrMasterRate(String period) {
        List<String> masters = dltIcaiMapper.getIncrUnCalcRateMasters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        List<DltMasterRatePo> rates = masters.parallelStream()
                                             .map(id -> calcRate(id, period))
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());
        dltMasterMapper.addDltMasterRates(rates);
    }

    /**
     * 计算专家预测数据命中率
     */
    @Override
    public void calcMasterRate(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(dltIcaiMapper.latestDltICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(dltMasterMapper.hasExistRatePeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        //查询本期预测专家标识
        String       current   = period;
        List<String> masterIds = dltIcaiMapper.getDltMasterIdsByPeriod(current);
        if (CollectionUtils.isEmpty(masterIds)) {
            return;
        }
        //计算专家命中率
        List<DltMasterRatePo> rates = masterIds.parallelStream()
                                               .map(id -> calcRate(id, current))
                                               .filter(Objects::nonNull)
                                               .collect(Collectors.toList());
        dltMasterMapper.addDltMasterRates(rates);
    }

    private DltMasterRatePo calcRate(String masterId, String period) {
        List<DltIcaiPo> list = dltIcaiMapper.getDltICaiHitsBefore(masterId, period, 30);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return new DltMasterRatePo().calcRate(list);
    }

    /**
     * 计算专家排名
     */
    @Override
    public void calcMasterRank(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(dltMasterMapper.latestDltRatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(dltMasterMapper.hasExistRankPeriod(period) == 0, ResponseHandler.HAS_RANKED_DATA);

        //专家预测命中率集合
        List<DltMasterRatePo> rates = dltMasterMapper.getDltMasterRates(period);
        if (CollectionUtils.isEmpty(rates)) {
            return;
        }

        //计算专家得分 权重
        List<DltMasterRankPo> ranks = rates.parallelStream()
                                           .map(rate -> new DltMasterRankPo().calcRank(rate))
                                           .collect(Collectors.toList());

        //计算专家综合排名
        List<RankValue> values = ranks.stream().map(DltMasterRankPo::getRank).collect(Collectors.toList());
        ICaiConstants.sortRank(values);

        //计算单项排名
        Arrays.stream(DltChannel.values())
              .map(channel -> ranks.stream().map(channel::rankValue).collect(Collectors.toList()))
              .forEach(ICaiConstants::sortRank);

        dltMasterMapper.addDltMasterRanks(ranks);

    }

    /**
     * 计算上首页专家
     */
    @Override
    public void calcHomeMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(dltMasterMapper.latestDltRankPeriod(), ResponseHandler.NO_RANKED_DATA);
        }
        Assert.state(dltMasterMapper.hasExtractHomeMaster(period) == 0, ResponseHandler.NO_FORECAST_DATA);
        String current = period;
        List<DltHomeMasterPo> masterPos = Arrays.stream(DltChannel.values())
                                                .filter(DltChannel::isVipChannel)
                                                .flatMap(channel -> channelHomeMaster(current, channel))
                                                .collect(Collectors.toList());
        dltMasterMapper.addDltHomeMasters(masterPos);
    }

    private Stream<DltHomeMasterPo> channelHomeMaster(String period, DltChannel channel) {
        List<DltMasterRatePo> rates = dltMasterMapper.getDltRankedMasterRates(period, channel.getChannel(), 6);
        return IntStream.range(0, 6).mapToObj(index -> {
            DltMasterRatePo rate   = rates.get(index);
            DltHomeMasterPo master = new DltHomeMasterPo();
            master.setType(channel);
            master.setRank(index + 1);
            master.setPeriod(rate.getPeriod());
            master.setMasterId(rate.getMasterId());
            master.setRate(channel.rateValue(rate));
            return master;
        });
    }

    /**
     * 计算vip专家
     */
    @Override
    @Transactional
    public void calcVipMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(dltMasterMapper.latestDltRankPeriod(), ResponseHandler.NO_RANKED_DATA);
        }
        Assert.state(dltMasterMapper.hasExtractVipMaster(period) == 0, ResponseHandler.NO_FORECAST_DATA);

        List<String> types = Arrays.stream(DltChannel.values())
                                   .filter(DltChannel::isVipChannel)
                                   .map(DltChannel::getChannel)
                                   .collect(Collectors.toList());
        List<DltMasterRankPo> masterRanks = dltMasterMapper.extractVipMasters(period, types, 50, 30);
        if (CollectionUtils.isEmpty(masterRanks)) {
            return;
        }

        //提取vip专家信息
        List<String> masterIds = masterRanks.stream()
                                            .map(DltMasterRankPo::getMasterId)
                                            .distinct()
                                            .collect(Collectors.toList());

        dltMasterMapper.editDltVipMasters(period, masterIds);
        //计算热门专家
        this.calcHotMaster(period);
    }

    @Override
    public void calcHotMaster(String period) {
        String       last       = PeriodCalculator.dltPeriod(period, 1);
        String       start      = PeriodCalculator.dltPeriod(period, 10);
        List<String> hotMasters = dltIcaiMapper.getDltHotMasters(period, last, start, 4);
        if (!CollectionUtils.isEmpty(hotMasters)) {
            dltIcaiMapper.extractDltHotMasters(period, hotMasters);
        }
    }

    /**
     * 全量计算数据统计
     *
     * @param period 指定期号
     */
    public void calcAllCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.dltPeriod(current, 1);

        //计算预测数据统计
        List<DltCensusDo> censuses = Arrays.stream(DltChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = dltIcaiMapper.getAllDltRankedDatas(channel.getChannel(), current, lastPeriod);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return DltCensusDo.calcFull(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<DltLottoCensusPo> censusPos = dltICaiAssembler.toPos(censuses);
        dltIcaiMapper.addDltCensuses(censusPos);
    }

    @Override
    public void calcItemCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.dltPeriod(current, 1);

        //计算预测数据统计
        List<DltCensusDo> censuses = Arrays.stream(DltChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = dltIcaiMapper.getLimitDltRankedDatas(channel.getChannel(), current, lastPeriod, 200);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return DltCensusDo.calcItem(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<DltLottoCensusPo> censusPos = dltICaiAssembler.toPos(censuses);
        dltIcaiMapper.addDltCensuses(censusPos);
    }

    /**
     * 计算热门专家统计数据
     *
     * @param period 指定期号
     */
    public void calcHotMasterChart(String period) {
        String current = getCensusCalcPeriod(period);
        String last    = PeriodCalculator.dltPeriod(current, 1);

        List<DltIcaiPo> datas = dltIcaiMapper.getDltHotMasterDatas(current, last);
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        List<DltCensusDo> censusDos = Arrays.stream(DltChannel.values()).map(channel -> {
            List<String> recs = datas.stream()
                                     .map(v -> channel.forecastValue(v).getData())
                                     .collect(Collectors.toList());
            return new DltCensusDo().create(current, channel, ChartType.HOT_CHART).calcCensus(recs);
        }).collect(Collectors.toList());
        List<DltLottoCensusPo> censusPos = dltICaiAssembler.toPos(censusDos);

        dltIcaiMapper.addDltCensuses(censusPos);
    }

    private List<String> channelVipData(DltChannel channel, String current, String lastPeriod) {
        List<DltIcaiPo> dataList = dltIcaiMapper.getDltVipItemDatas(channel.getChannel(), current, lastPeriod);
        return dataList.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());
    }

    /**
     * 计算vip专家统计图表
     *
     * @param period 指定期号
     */
    public void calcVipMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.dltPeriod(current, 1);

        List<DltCensusDo> censusDos = Arrays.stream(DltChannel.values())
                                            .filter(DltChannel::isVipChannel)
                                            .flatMap(channel -> {
                                                List<String> vipData = channelVipData(channel, current, lastPeriod);
                                                return DltCensusDo.calcVip(current, channel, vipData).stream();
                                            })
                                            .collect(Collectors.toList());

        List<DltLottoCensusPo> censusPos = dltICaiAssembler.toPos(censusDos);
        dltIcaiMapper.addDltCensuses(censusPos);
    }

    private List<String> highRateData(DltChannel channel, String current, String lastPeriod) {
        List<DltIcaiPo> dataList = dltIcaiMapper.getDltHighRateDatas(channel.getChannel(), current, lastPeriod, channel.rateThrottle());
        return dataList.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());
    }

    /**
     * 计算高命中率专家统计图表
     *
     * @param period 指定期号
     */
    public void calcRateMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.dltPeriod(current, 1);

        List<DltCensusDo> censusDos = Arrays.stream(DltChannel.values())
                                            .filter(channel -> channel.rateThrottle() < 1)
                                            .map(channel -> {
                                                List<String> highRateData = highRateData(channel, current, lastPeriod);
                                                return new DltCensusDo().create(current, channel, ChartType.RATE_CHART)
                                                                        .calcCensus(highRateData);
                                            })
                                            .collect(Collectors.toList());

        List<DltLottoCensusPo> censusPos = dltICaiAssembler.toPos(censusDos);
        dltIcaiMapper.addDltCensuses(censusPos);
    }

    /**
     * 提取最新期的专家中奖喜讯
     */
    @Override
    public void extractMasterGlad() {
        GladExtractEvent extractEvent = new GladExtractEvent(LotteryEnum.DLT);
        DomainEventPublisher.publish(extractEvent);
    }

    public String getCensusCalcPeriod(String period) {
        if (StringUtils.isNotBlank(period)) {
            Assert.state(dltIcaiMapper.hasDltICaiData(period) > 0, ResponseHandler.NO_FORECAST_DATA);
            return period;
        }
        Period iPeriod = Assert.notNull(dltIcaiMapper.latestDltICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
        return iPeriod.getPeriod();
    }

}
