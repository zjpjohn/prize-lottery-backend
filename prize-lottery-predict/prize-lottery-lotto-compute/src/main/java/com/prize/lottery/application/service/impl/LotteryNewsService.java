package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.service.ILotteryNewsService;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.news.SinaLotteryNewsSpider;
import com.prize.lottery.mapper.LotteryNewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryNewsService implements ILotteryNewsService {

    private final SinaLotteryNewsSpider lotteryNewsSpider;
    private final LotteryNewsMapper     lotteryNewsMapper;

    /**
     * 初始化抓取新闻资讯
     */
    @Override
    public void initialFetchNews() {
        int result = lotteryNewsMapper.hasLotteryNews();
        Assert.state(result == 0, ResponseHandler.HAS_INIT_DATA);
        long millis = System.currentTimeMillis();
        //分布时间范围
        int range = 300;
        IntStream.range(1, 7).forEach(page -> {
            lotteryNewsSpider.fetchNewsList(page, millis + (page - 1) * range * 1000L, range);
        });
    }

    /**
     * 抓取最新的资讯
     */
    @Override
    public void fetchNews() {
        if (lotteryNewsMapper.hasLotteryNews() > 0) {
            lotteryNewsSpider.fetchNewsList(1, System.currentTimeMillis(), 300);
            return;
        }
        long millis = System.currentTimeMillis();
        IntStream.range(1, 7).forEach(page -> {
            lotteryNewsSpider.fetchNewsList(page, millis + (page - 1) * 300 * 1000L, 300);
        });
    }

}
