package com.prize.lottery.application.service;


import com.prize.lottery.enums.LotteryEnum;

public interface IMasterEvictService {

    /**
     * 提取待淘汰的专家
     */
    void extractEvicts(LotteryEnum type);

    /**
     * 清理淘汰专家预测数据
     */
    void clearForecasts(LotteryEnum type);

    /**
     * 清理专家浏览点赞记录
     */
    void clearMasters(LotteryEnum type);

}
