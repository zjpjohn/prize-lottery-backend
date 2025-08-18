package com.prize.lottery.application.service;

public interface ILotterySkillService {

    /**
     * 初始化抓取使用技巧
     */
    void initialFetchSkill();

    /**
     * 抓取最新实用技巧
     */
    void fetchSkill();
}
