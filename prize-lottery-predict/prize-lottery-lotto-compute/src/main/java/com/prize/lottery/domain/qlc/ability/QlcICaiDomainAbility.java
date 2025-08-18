package com.prize.lottery.domain.qlc.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.qlc.assembler.QlcICaiAssembler;
import com.prize.lottery.domain.qlc.model.QlcCensusDo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.mapper.QlcMasterMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.qlc.*;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.vo.ICaiRankedDataVo;
import jakarta.annotation.Resource;
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
public class QlcICaiDomainAbility implements ICaiDomainAbility {

    @Resource
    private QlcIcaiMapper     qlcIcaiMapper;
    @Resource
    private QlcMasterMapper   qlcMasterMapper;
    @Resource
    private LotteryInfoMapper lotteryInfoMapper;
    @Resource
    private QlcICaiAssembler  qlcICaiAssembler;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.QLC;
    }

    /**
     * 计算指定期的预测数据命中信息
     *
     * @param period
     */
    @Override
    public void calcForecastHit(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(qlcIcaiMapper.latestQlcICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(qlcIcaiMapper.hasQlcPeriodOpenCalc(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        //指定期的开奖号码
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.QLC.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        //指定期的预测数据
        List<QlcIcaiPo> datas = qlcIcaiMapper.getAllQlcICaiDatas(period);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);

        //计算预测数据命中信息
        List<String> reds = Lists.newArrayList(lottery.getRed().split("\\s+"));
        datas.forEach(v -> v.calcHit(reds));
        qlcIcaiMapper.editQlcICaiList(datas);
    }

    @Override
    public void calcIncrHit(String period) {
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.QLC.getType(), period);
        if (lottery != null) {
            List<QlcIcaiPo> datas = qlcIcaiMapper.getAllUnCalcDatas(period);
            if (!CollectionUtils.isEmpty(datas)) {
                List<String> balls = lottery.redBalls();
                datas.forEach(v -> v.calcHit(balls));
                qlcIcaiMapper.editQlcICaiList(datas);
            }
        }
    }

    @Override
    public void calcIncrMasterRate(String period) {
        List<String> masters = qlcIcaiMapper.getIncrUnCalcRateMasters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        List<QlcMasterRatePo> rates = masters.parallelStream()
                                             .map(id -> calcRate(id, period))
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());
        qlcMasterMapper.addQlcMasterRates(rates);
    }

    /**
     * 计算专家预测数据命中率
     *
     * @param period
     */
    @Override
    public void calcMasterRate(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(qlcIcaiMapper.latestQlcICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(qlcMasterMapper.hasExistRatePeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        String current = period;
        //指定期预测专家集合
        List<String> masterIds = qlcIcaiMapper.getQlcMasterIdsByPeriod(current);
        if (CollectionUtils.isEmpty(masterIds)) {
            return;
        }

        //计算专家预测命中率
        List<QlcMasterRatePo> rates = masterIds.parallelStream()
                                               .map(id -> calcRate(id, current))
                                               .filter(Objects::nonNull)
                                               .collect(Collectors.toList());
        qlcMasterMapper.addQlcMasterRates(rates);
    }

    private QlcMasterRatePo calcRate(String masterId, String period) {
        List<QlcIcaiPo> list = qlcIcaiMapper.getQlcICaiHitsBefore(masterId, period, 30);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return new QlcMasterRatePo().calcRate(list);
    }

    /**
     * 计算专家排名
     *
     * @param period
     */
    @Override
    public void calcMasterRank(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(qlcMasterMapper.latestQlcRatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(qlcMasterMapper.hasExistRankPeriod(period) == 0, ResponseHandler.HAS_RANKED_DATA);
        List<QlcMasterRatePo> rates = qlcMasterMapper.getQlcMasterRates(period);
        if (CollectionUtils.isEmpty(rates)) {
            return;
        }

        //计算得分权重
        List<QlcMasterRankPo> ranks = rates.parallelStream()
                                           .map(rate -> new QlcMasterRankPo().calcRank(rate))
                                           .collect(Collectors.toList());

        //计算综合排名
        List<RankValue> values = ranks.stream().map(QlcMasterRankPo::getRank).collect(Collectors.toList());
        ICaiConstants.sortRank(values);

        //计算单项排名
        Arrays.stream(QlcChannel.values())
              .map(channel -> ranks.stream().map(channel::rankValue).collect(Collectors.toList()))
              .forEach(ICaiConstants::sortRank);
        qlcMasterMapper.addQlcMasterRanks(ranks);

    }

    /**
     * 计算上首页专家
     *
     * @param period
     */
    @Override
    public void calcHomeMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(qlcMasterMapper.latestQlcRankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(qlcMasterMapper.hasExtractHomeMaster(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        String current = period;
        List<QlcHomeMasterPo> masterPos = Arrays.stream(QlcChannel.values())
                                                .filter(QlcChannel::isVipChannel)
                                                .flatMap(channel -> {
                                                    List<QlcMasterRatePo> rates = qlcMasterMapper.getQlcRankedMasterRates(current, channel.getChannel(), 6);
                                                    return IntStream.range(0, 6).mapToObj(index -> {
                                                        QlcMasterRatePo rate   = rates.get(index);
                                                        QlcHomeMasterPo master = new QlcHomeMasterPo();
                                                        master.setRank(index + 1);
                                                        master.setType(channel);
                                                        master.setPeriod(rate.getPeriod());
                                                        master.setMasterId(rate.getMasterId());
                                                        master.setRate(channel.rateValue(rate));
                                                        return master;
                                                    });
                                                })
                                                .collect(Collectors.toList());
        qlcMasterMapper.addQlcHomeMasters(masterPos);
    }

    /**
     * 计算vip专家
     *
     * @param period
     */
    @Override
    @Transactional
    public void calcVipMaster(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(qlcMasterMapper.latestQlcRankPeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(qlcMasterMapper.hasExtractVipMaster(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);

        //提取vip专家标识集合
        List<String> types = Arrays.stream(QlcChannel.values())
                                   .filter(QlcChannel::isVipChannel)
                                   .map(QlcChannel::getChannel)
                                   .collect(Collectors.toList());
        List<QlcMasterRankPo> masterRanks = qlcMasterMapper.extractVipMasters(period, types, 50, 30);
        if (CollectionUtils.isEmpty(masterRanks)) {
            return;
        }

        List<String> masterIds = masterRanks.stream()
                                            .map(QlcMasterRankPo::getMasterId)
                                            .distinct()
                                            .collect(Collectors.toList());
        //计算vip专家
        qlcMasterMapper.editQlcVipMasters(period, masterIds);

        //热门专家计算
        this.calcHotMaster(period);
    }

    @Override
    public void calcHotMaster(String period) {
        String       last       = PeriodCalculator.qlcPeriod(period, 1);
        String       start      = PeriodCalculator.qlcPeriod(period, 10);
        List<String> hotMasters = qlcIcaiMapper.getQlcHotMasters(period, last, start, 4);
        if (!CollectionUtils.isEmpty(hotMasters)) {
            qlcIcaiMapper.extractQlcHotMasters(period, hotMasters);
        }
    }

    /**
     * 全量计算数据统计
     *
     * @param period 指定期号
     */
    public void calcAllCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.qlcPeriod(current, 1);

        //计算全量统计数据
        List<QlcCensusDo> censuses = Arrays.stream(QlcChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = qlcIcaiMapper.getAllQlcRankedDatas(current, lastPeriod, channel.getChannel());
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return QlcCensusDo.calcFull(current, channel, recs).stream();
        }).collect(Collectors.toList());

        //持久化统计数据
        List<QlcLottoCensusPo> censusPos = qlcICaiAssembler.toPos(censuses);
        qlcIcaiMapper.addQlcCensuses(censusPos);
    }

    @Override
    public void calcItemCensusChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.qlcPeriod(current, 1);

        //计算全量统计数据
        List<QlcCensusDo> censuses = Arrays.stream(QlcChannel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = qlcIcaiMapper.getLimitQlcRankedDatas(current, lastPeriod, channel.getChannel(), 200);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return QlcCensusDo.calcItem(current, channel, recs).stream();
        }).collect(Collectors.toList());

        //持久化统计数据
        List<QlcLottoCensusPo> censusPos = qlcICaiAssembler.toPos(censuses);
        qlcIcaiMapper.addQlcCensuses(censusPos);
    }

    /**
     * 计算热门专家统计数据
     *
     * @param period 指定期号
     */
    public void calcHotMasterChart(String period) {
        String current = getCensusCalcPeriod(period);
        String last    = PeriodCalculator.qlcPeriod(current, 1);

        //计算热门专家统计数据时
        List<QlcIcaiPo> datas = qlcIcaiMapper.getQlcHotMasterDatas(current, last);
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        List<QlcCensusDo> censusDos = Arrays.stream(QlcChannel.values())
                                            .filter(QlcChannel::isHotChannel)
                                            .map(channel -> {
                                                List<String> recs = datas.stream()
                                                                         .map(v -> channel.forecastValue(v).getData())
                                                                         .collect(Collectors.toList());
                                                return new QlcCensusDo().create(current, channel, ChartType.HOT_CHART)
                                                                        .calcCensus(recs);
                                            })
                                            .collect(Collectors.toList());

        //持久换热门专家统计数据
        List<QlcLottoCensusPo> censusPos = qlcICaiAssembler.toPos(censusDos);
        qlcIcaiMapper.addQlcCensuses(censusPos);
    }

    private List<String> channelVipData(QlcChannel channel, String current, String lastPeriod) {
        List<QlcIcaiPo> datas = qlcIcaiMapper.getQlcVipItemsDatas(channel.getChannel(), current, lastPeriod);
        return datas.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());
    }

    /**
     * 计算vip专家统计图表
     *
     * @param period 指定期号
     */
    public void calcVipMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.qlcPeriod(current, 1);

        //计算vip专家统计数据
        List<QlcCensusDo> censusDos = Arrays.stream(QlcChannel.values())
                                            .filter(QlcChannel::isVipChannel)
                                            .flatMap(channel -> {
                                                List<String> vipData = channelVipData(channel, current, lastPeriod);
                                                return QlcCensusDo.calcVip(current, channel, vipData).stream();
                                            })
                                            .collect(Collectors.toList());

        //持久化vip统计数据
        List<QlcLottoCensusPo> censusPos = qlcICaiAssembler.toPos(censusDos);
        qlcIcaiMapper.addQlcCensuses(censusPos);
    }

    private List<String> highRateData(QlcChannel channel, String current, String lastPeriod) {
        List<QlcIcaiPo> datas = qlcIcaiMapper.getQlcHighRateDatas(current, lastPeriod, channel.getChannel(), channel.rateThrottle());
        return datas.stream().map(v -> channel.forecastValue(v).getData()).collect(Collectors.toList());
    }

    /**
     * 计算高命中率专家统计
     *
     * @param period 指定期号
     */
    public void calcRateMasterChart(String period) {
        String current    = getCensusCalcPeriod(period);
        String lastPeriod = PeriodCalculator.qlcPeriod(current, 1);

        //计算高命中率专家统计数据
        List<QlcCensusDo> censusDos = Arrays.stream(QlcChannel.values())
                                            .filter(channel -> channel.rateThrottle() < 1)
                                            .map(channel -> {
                                                List<String> highRateData = highRateData(channel, current, lastPeriod);
                                                return new QlcCensusDo().create(current, channel, ChartType.RATE_CHART)
                                                                        .calcCensus(highRateData);
                                            })
                                            .collect(Collectors.toList());

        //持久化高命中率统计数据
        List<QlcLottoCensusPo> censusPos = qlcICaiAssembler.toPos(censusDos);
        qlcIcaiMapper.addQlcCensuses(censusPos);
    }

    private String getCensusCalcPeriod(String period) {
        if (StringUtils.isNotBlank(period)) {
            Assert.state(qlcIcaiMapper.hasQlcICaiData(period) > 0, ResponseHandler.NO_FORECAST_DATA);
            return period;
        }
        Period iPeriod = Assert.notNull(qlcIcaiMapper.latestQlcICaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
        return iPeriod.getPeriod();
    }

}
