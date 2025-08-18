package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.prize.lottery.application.query.service.ILottoSkillQueryService;
import com.prize.lottery.mapper.LotterySkillMapper;
import com.prize.lottery.po.lottery.LotterySkillPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LottoSkillQueryService implements ILottoSkillQueryService {

    private final LotterySkillMapper lotterySkillMapper;

    public LottoSkillQueryService(LotterySkillMapper lotterySkillMapper) {
        this.lotterySkillMapper = lotterySkillMapper;
    }

    @Override
    public Page<LotterySkillPo> getLotterySkillList(PageCondition condition) {
        return condition.count(lotterySkillMapper::countLotterySkills).query(lotterySkillMapper::getLotterySkills);
    }

    @Override
    public LotterySkillPo getLotterySkillDetail(String seq) {
        return lotterySkillMapper.getLotterySkill(seq, true);
    }
}
