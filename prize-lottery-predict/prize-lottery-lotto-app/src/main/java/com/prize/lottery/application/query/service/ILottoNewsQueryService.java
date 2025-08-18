package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.lottery.LotteryNewsPo;

public interface ILottoNewsQueryService {

    Page<LotteryNewsPo> getLotteryNewsList(PageCondition condition);

    LotteryNewsPo getLotteryNewsDetail(String seq);
}
