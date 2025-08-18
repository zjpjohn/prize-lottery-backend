package com.prize.lottery.application.service;


public interface ILotteryNewsService {

    /**
     * 初始化抓取新闻资讯
     */
    void initialFetchNews();

    /**
     * 抓取最新的资讯
     */
    void fetchNews();

}
