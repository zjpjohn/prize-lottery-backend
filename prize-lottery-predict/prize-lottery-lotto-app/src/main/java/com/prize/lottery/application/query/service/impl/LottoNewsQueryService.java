package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.prize.lottery.application.query.service.ILottoNewsQueryService;
import com.prize.lottery.mapper.LotteryNewsMapper;
import com.prize.lottery.po.lottery.LotteryNewsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoNewsQueryService implements ILottoNewsQueryService {

    private final LotteryNewsMapper lotteryNewsMapper;

    @Override
    public Page<LotteryNewsPo> getLotteryNewsList(PageCondition condition) {
        return condition.count(lotteryNewsMapper::countLotteryNews).query(lotteryNewsMapper::getLotteryNewsList);
    }

    @Override
    public LotteryNewsPo getLotteryNewsDetail(String seq) {
        return lotteryNewsMapper.getLotteryNews(seq);
    }
}
