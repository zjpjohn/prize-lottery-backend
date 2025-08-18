package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.lottery.LotteryNewsPo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LotteryNewsMapper {

    int addLotteryNews(LotteryNewsPo lotteryNews);

    int editLotteryNews(LotteryNewsPo lotteryNews);

    int hasLotteryNews();

    LotteryNewsPo getLotteryNewsById(Long id);

    LotteryNewsPo getLotteryNews(String seq);

    List<LotteryNewsPo> getLotteryNewsList(PageCondition condition);

    int countLotteryNews(PageCondition condition);

    List<LotteryNewsPo> getGroupedTopNewsList();


}
