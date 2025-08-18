package com.prize.lottery.application.service;

public interface IDltICaiService {

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    void fetchForecast(String period);

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    void fetchLastForecast(String before, Integer size);

    /**
     * 抓取最近30期的历史预测数据
     */
    void fetchHistoryForecast();

    /**
     * 增量抓取30期历史预测数据
     */
    void fetchIncrHistory();

    /**
     * 初始化计算历史数据命中
     */
    void batchCalcDltHit();

    /**
     * 计算增量历史数据命中
     */
    void calcIncrAllHit();

    /**
     * 初始化计算专家历史命中率
     */
    void initCalcMasterRate();

    /**
     * 计算增量专家预测命中率
     */
    void incrCalcMasterRate();

    /**
     * 初始化计算历史专家排名
     */
    void initCalcMasterRank();

}
