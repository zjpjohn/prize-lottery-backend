package com.prize.lottery.application.service;


import com.prize.lottery.enums.LotteryEnum;

public interface IMasterInfoService {

    /**
     * 导入平台专家
     */
    void importMasters(LotteryEnum type);

    /**
     * 重置专家名称
     */
    void resetMasterNames();

    /**
     * 重置专家头像
     */
    void resetMasterAvatars();

    /**
     * 删除系统头像
     */
    void removeAvatar(Long id);

    /**
     * 导入系统头像
     */
    void importAvatars();

    /**
     * 提取专家预测喜讯
     */
    void extractGlads();

    /**
     * 删除指定天前的喜讯
     */
    void removeGlads(Integer day);

    /**
     * 提取专家信息流
     */
    void extractMasterFeeds();

    /**
     * 清除专家信息流
     *
     * @param type 彩种类型
     * @param rate 清除低于的命中率
     */
    void removeMasterFeed(LotteryEnum type, Double rate);

}
