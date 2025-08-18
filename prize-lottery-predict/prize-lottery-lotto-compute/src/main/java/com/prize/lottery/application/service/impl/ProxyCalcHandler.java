package com.prize.lottery.application.service.impl;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.service.IProxyCalcHandler;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.index.ability.IndexAbility;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyCalcHandler implements IProxyCalcHandler {

    private final EnumerableExecutorFactory<LotteryEnum, ICaiDomainAbility> abilities;
    private final EnumerableExecutorFactory<LotteryEnum, IndexAbility>      indexAbilities;

    /**
     * 计算指定期的预测数据命中信息
     */
    @Override
    public void calcForecastHit(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcForecastHit(period));
    }

    /**
     * 计算专家预测数据命中率
     */
    @Override
    public void calcMasterRate(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcMasterRate(period));
    }

    /**
     * 计算专家排名
     */
    @Override
    public void calcMasterRank(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcMasterRank(period));
    }

    /**
     * 计算上首页专家
     */
    @Override
    public void calcHomeMaster(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcHomeMaster(period));
    }

    @Override
    public void calcItemCensusChart(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcItemCensusChart(period));
    }

    /**
     * 计算vip专家
     */
    @Override
    public void calcVipMaster(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcVipMaster(period));
    }

    /**
     * 全量统计计算
     */
    @Override
    public void calcAllCensusChart(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcAllCensusChart(period));
    }

    /**
     * 高命中专家统计计算
     */
    @Override
    public void calcRateMasterChart(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcRateMasterChart(period));
    }

    /**
     * 热门专家统计计算
     */
    @Override
    public void calcHotMasterChart(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcHotMasterChart(period));
    }

    /**
     * vip专家统计计算
     */
    @Override
    public void calcVipMasterChart(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcVipMasterChart(period));
    }

    @Override
    public void calcLottoIndex(LotteryEnum type, String period) {
        indexAbilities.ofNullable(type).ifPresent(calc -> calc.calcIndex(period));
    }

    @Override
    public void calcItemIndex(LotteryEnum type, String period) {
        indexAbilities.ofNullable(type).ifPresent(calc -> calc.calcItemIndex(period));
    }

    /**
     * 计算预警分析命中
     */
    @Override
    public void calcWarningHit(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(calc -> calc.calcComRecommend(period));
    }

    /**
     * 提取首页专家中奖喜讯
     */
    @Override
    public void extractMasterGlad(LotteryEnum type, String period) {
        abilities.ofNullable(type).ifPresent(ICaiDomainAbility::extractMasterGlad);
    }

}
