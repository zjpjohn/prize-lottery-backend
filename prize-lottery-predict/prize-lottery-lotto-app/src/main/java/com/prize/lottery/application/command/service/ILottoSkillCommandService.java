package com.prize.lottery.application.command.service;


import com.prize.lottery.po.lottery.LotterySkillPo;

public interface ILottoSkillCommandService {

    LotterySkillPo browseSkill(String seq, Long userId);
}
