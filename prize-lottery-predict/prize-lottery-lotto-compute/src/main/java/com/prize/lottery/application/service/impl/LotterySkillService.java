package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.service.ILotterySkillService;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.skill.LotterySkillSpider;
import com.prize.lottery.mapper.LotterySkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotterySkillService implements ILotterySkillService {

    private final LotterySkillSpider lotterySkillSpider;
    private final LotterySkillMapper lotterySkillMapper;

    /**
     * 初始化抓取使用技巧
     */
    @Override
    public void initialFetchSkill() {
        Assert.state(lotterySkillMapper.hasLotterySkill() == 0, ResponseHandler.HAS_INIT_DATA);
        lotterySkillSpider.batchLottoFetch(1200, 90);
    }

    /**
     * 抓取最新实用技巧
     */
    @Override
    public void fetchSkill() {
        if (lotterySkillMapper.hasLotterySkill() > 0) {
            lotterySkillSpider.batchLottoFetch(600, 30);
            return;
        }
        lotterySkillSpider.batchLottoFetch(1200, 90);
    }

}
