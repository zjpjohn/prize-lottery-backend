package com.prize.lottery.domain.kl8.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.kl8.assembler.Kl8IcaiAssembler;
import com.prize.lottery.domain.kl8.model.Kl8CensusDo;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.Kl8IcaiMapper;
import com.prize.lottery.mapper.Kl8MasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.po.kl8.Kl8MasterRankPo;
import com.prize.lottery.po.kl8.Kl8MasterRatePo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.vo.ICaiRankedDataVo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Kl8ICaiDomainAbility implements ICaiDomainAbility {

    @Resource
    private Kl8IcaiMapper     kl8IcaiMapper;
    @Resource
    private Kl8MasterMapper   kl8MasterMapper;
    @Resource
    private LotteryInfoMapper lotteryInfoMapper;
    @Resource
    private Kl8IcaiAssembler  kl8IcaiAssembler;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.KL8;
    }

    /**
     * 计算指定期的预测数据命中信息
     *
     * @param period 预测期号
     */
    @Override
    public void calcForecastHit(String period) {
        if (StringUtils.isBlank(period)) {
            Period iPeriod = Assert.notNull(kl8IcaiMapper.latestKl8IcaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
            period = iPeriod.getPeriod();
        }
        Assert.state(kl8IcaiMapper.hasKl8PeriodOpenCalc(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        //获取开奖数据
        LotteryInfoPo lottery = Assert.notNull(lotteryInfoMapper.getLotteryInfo(LotteryEnum.KL8.getType(), period));
        List<String>  balls   = Lists.newArrayList(lottery.getRed().split("\\s+"));

        //快乐8预测数据
        List<Kl8IcaiInfoPo> datas = kl8IcaiMapper.getAllKl8IcaiDatas(period);
        Assert.state(!CollectionUtils.isEmpty(datas), ResponseHandler.NO_FORECAST_DATA);

        datas.forEach(data -> data.calcHit(balls));
        kl8IcaiMapper.editKl8IcaiList(datas);
    }

    /**
     * 计算专家预测数据命中率
     *
     * @param period 计算期号
     */
    @Override
    public void calcMasterRate(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(kl8MasterMapper.latestKl8RatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        //本期预测专家标识集合
        Assert.state(kl8MasterMapper.hasExistRatePeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        String       current   = period;
        List<String> masterIds = kl8IcaiMapper.getKl8MasterIdsByPeriod(current);
        if (CollectionUtils.isEmpty(masterIds)) {
            return;
        }
        //计算专家命中率
        List<Kl8MasterRatePo> rates = masterIds.parallelStream()
                                               .map(v -> kl8IcaiMapper.getKl8IcaiHitsBefore(v, current, 30))
                                               .filter(datas -> !CollectionUtils.isEmpty(datas))
                                               .map(datas -> new Kl8MasterRatePo().calcRate(datas))
                                               .collect(Collectors.toList());
        kl8MasterMapper.addKl8MasterRates(rates);
    }

    /**
     * 计算专家排名
     *
     * @param period 计算期号
     */
    @Override
    public void calcMasterRank(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(kl8MasterMapper.latestKl8RatePeriod(), ResponseHandler.NO_FORECAST_DATA);
        }
        Assert.state(kl8MasterMapper.hasExistRankPeriod(period) == 0, ResponseHandler.HAS_CALCULATED_DATA);
        List<Kl8MasterRatePo> rates = kl8MasterMapper.getKl8MasterRates(period);
        if (CollectionUtils.isEmpty(rates)) {
            return;
        }

        //计算专家得分权重
        List<Kl8MasterRankPo> ranks = rates.parallelStream()
                                           .map(rate -> new Kl8MasterRankPo().calcRank(rate))
                                           .collect(Collectors.toList());

        //专家综合排名
        List<RankValue> values = ranks.stream().map(Kl8MasterRankPo::getRank).collect(Collectors.toList());
        ICaiConstants.sortRank(values);

        //专家单项排名
        Arrays.asList(Kl8Channel.values())
              .parallelStream()
              .map(channel -> ranks.stream().map(channel::rankValue).collect(Collectors.toList()))
              .forEach(ICaiConstants::sortRank);
        kl8MasterMapper.addKl8MasterRanks(ranks);

    }

    @Override
    public void calcHotMaster(String period) {

    }

    /**
     * 计算上首页专家
     */
    @Override
    public void calcHomeMaster(String period) {
        throw new UnsupportedOperationException("快乐8开奖不支持操作.");
    }

    /**
     * 计算vip专家
     */
    @Override
    public void calcVipMaster(String period) {
        throw new UnsupportedOperationException("快乐8开奖不支持操作.");
    }

    @Override
    public void calcItemCensusChart(String period) {

    }

    /**
     * 全量统计计算
     */
    @Override
    public void calcAllCensusChart(String period) {
        String current    = checkCensusPeriod(period);
        String lastPeriod = PeriodCalculator.fc3dPeriod(current, 1);

        List<Kl8CensusDo> censuses = Arrays.stream(Kl8Channel.values()).flatMap(channel -> {
            List<ICaiRankedDataVo> datas = kl8IcaiMapper.getAllKl8RankedDatas(channel.getChannel(), current, lastPeriod);
            List<String>           recs  = datas.stream().map(v -> v.getData().getData()).collect(Collectors.toList());
            return Kl8CensusDo.calcFull(current, channel, recs).stream();
        }).collect(Collectors.toList());

        List<Kl8LottoCensusPo> censusList = kl8IcaiAssembler.toPos(censuses);
        kl8IcaiMapper.addKl8Censuses(censusList);
    }

    /**
     * 高命中专家统计计算
     */
    @Override
    public void calcRateMasterChart(String period) {

    }

    /**
     * 热门专家统计计算
     */
    @Override
    public void calcHotMasterChart(String period) {

    }

    /**
     * vip专家统计计算
     */
    @Override
    public void calcVipMasterChart(String period) {

    }

    /**
     * 检查期号
     *
     * @param period 计算统计期号
     */
    private String checkCensusPeriod(String period) {
        if (StringUtils.isNotBlank(period)) {
            Assert.state(kl8IcaiMapper.hasKl8IcaiData(period) > 0, ResponseHandler.NO_FORECAST_DATA);
            return period;
        }
        Period iPeriod = Assert.notNull(kl8IcaiMapper.latestKl8IcaiPeriod(), ResponseHandler.NO_FORECAST_DATA);
        return iPeriod.getPeriod();
    }

}
