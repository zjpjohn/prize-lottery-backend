package com.prize.lottery.application.command.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.service.INewsCommandService;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryNewsMapper;
import com.prize.lottery.po.lottery.LotteryNewsPo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class NewsCommandService implements INewsCommandService {

    @Resource
    private LotteryNewsMapper lotteryNewsMapper;

    @Transactional
    public LotteryNewsPo browseNews(String seq, Long userId) {
        LotteryNewsPo news = lotteryNewsMapper.getLotteryNews(seq);
        Assert.notNull(news, ResponseHandler.LOTTERY_NEWS_NONE);
        Assert.state(news.getState() >= 1, ResponseHandler.LOTTERY_NEWS_FORBIDDEN);

        LotteryNewsPo newsPo = new LotteryNewsPo();
        newsPo.setId(news.getId());
        newsPo.setBrowse(1);
        lotteryNewsMapper.editLotteryNews(newsPo);

        return news;
    }
}
