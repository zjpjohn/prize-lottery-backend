package com.prize.lottery.application.command.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.service.ILottoSkillCommandService;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotterySkillMapper;
import com.prize.lottery.po.lottery.LotterySkillPo;
import org.springframework.stereotype.Service;

@Service
public class LottoSkillCommandService implements ILottoSkillCommandService {

    private final LotterySkillMapper lotterySkillMapper;

    public LottoSkillCommandService(LotterySkillMapper lotterySkillMapper) {
        this.lotterySkillMapper = lotterySkillMapper;
    }

    @Override
    public LotterySkillPo browseSkill(String seq, Long userId) {
        LotterySkillPo skill = lotterySkillMapper.getLotterySkill(seq, true);
        Assert.notNull(skill, ResponseHandler.LOTTERY_SKILL_NONE);
        Assert.state(skill.getState() >= 1, ResponseHandler.LOTTERY_SKILL_FORBIDDEN);

        LotterySkillPo skillPo = new LotterySkillPo();
        skillPo.setId(skill.getId());
        skillPo.setBrowse(1);
        lotterySkillMapper.editLotterySkill(skillPo);

        return skill;
    }
}
