package com.prize.lottery.domain;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.enums.LotteryEnum;

public interface ICaiDomainAbility extends Executor<LotteryEnum> {

    /**
     * 计算指定期的预测数据命中信息
     */
    void calcForecastHit(String period);

    /**
     * 计算指定期的增量数据命中信息
     */
    default void calcIncrHit(String period) {
    }

    /**
     * 计算专家预测数据命中率
     */
    void calcMasterRate(String period);

    /**
     * 增量计算专辑爱预测命中率
     */
    default void calcIncrMasterRate(String period) {
    }

    /**
     * 计算专家排名
     */
    void calcMasterRank(String period);

    /**
     * 计算上首页专家
     */
    void calcHomeMaster(String period);

    /**
     * 计算vip专家
     */
    void calcVipMaster(String period);

    /**
     * 计算热门专家
     */
    void calcHotMaster(String period);

    /**
     * 分类排名统计计算
     */
    void calcItemCensusChart(String period);

    /**
     * 全量统计计算
     */
    void calcAllCensusChart(String period);

    /**
     * 高命中专家统计计算
     */
    void calcRateMasterChart(String period);

    /**
     * 热门专家统计计算
     */
    void calcHotMasterChart(String period);

    /**
     * vip专家统计计算
     */
    void calcVipMasterChart(String period);

    /**
     * 计算预警推荐命中
     */
    default void calcComRecommend(String period) {
    }

    /**
     * 提取最新期的专家中奖喜讯
     */
    default void extractMasterGlad() {

    }

}
