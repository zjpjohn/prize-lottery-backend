package com.prize.lottery.application.service;

public interface ISsqICaiService {

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    void fetchForecast(String period);

    /**
     * 抓取最近30期的历史预测数据
     */
    void fetchHistoryForecast();

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    void fetchLastForecast(String before, Integer size);

    /**
     * 初始化计算历史数据命中
     */
    void batchCalcSsqHit();

    /**
     * 初始化计算专家历史命中率
     */
    void initCalcMasterRate();

    /**
     * 初始化计算历史专家排名
     */
    void initCalcMasterRank();

    /**
     * 撞去增量专家历史预测数据
     */
    void fetchIncrHistory();

    /**
     * 计算增量专家历史数据命中
     */
    void calcIncrAllHit();

    /**
     * 计算增量专家命中率
     */
    void incrCalcMasterRate();

}
