package com.prize.lottery.application.service;


import com.prize.lottery.enums.LotteryEnum;

public interface IProxyCalcHandler {

    /**
     * 计算指定期的预测数据命中信息
     */
    void calcForecastHit(LotteryEnum type, String period);

    /**
     * 计算专家预测数据命中率
     */
    void calcMasterRate(LotteryEnum type, String period);

    /**
     * 计算专家排名
     */
    void calcMasterRank(LotteryEnum type, String period);

    /**
     * 计算上首页专家
     */
    void calcHomeMaster(LotteryEnum type, String period);

    /**
     * 计算vip专家
     */
    void calcVipMaster(LotteryEnum type, String period);

    /**
     * 计算分类排行专家统计
     */
    void calcItemCensusChart(LotteryEnum type, String period);

    /**
     * 全量统计计算
     */
    void calcAllCensusChart(LotteryEnum type, String period);

    /**
     * 高命中专家统计计算
     */
    void calcRateMasterChart(LotteryEnum type, String period);

    /**
     * 热门专家统计计算
     */
    void calcHotMasterChart(LotteryEnum type, String period);

    /**
     * vip专家统计计算
     */
    void calcVipMasterChart(LotteryEnum type, String period);

    /**
     * 计算选彩预警指数
     */
    void calcLottoIndex(LotteryEnum type, String period);

    /**
     * 计算分类指数
     */
    void calcItemIndex(LotteryEnum type, String period);

    /**
     * 计算预警分析命中
     */
    void calcWarningHit(LotteryEnum type, String period);

    /**
     * 提取首页专家中奖喜讯
     */
    void extractMasterGlad(LotteryEnum type, String period);
}

