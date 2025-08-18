package com.prize.lottery.application.service;

public interface IKl8ICaiService {

    /**
     * 初始化计算历史预测数据命中
     */
    void initCalcKl8Hit();

    /**
     * 初始化计算专家历史命中率
     */
    void initCalcMasterRate();

    /**
     * 初始化计算历史专家排名
     */
    void initCalcMasterRank();

}
