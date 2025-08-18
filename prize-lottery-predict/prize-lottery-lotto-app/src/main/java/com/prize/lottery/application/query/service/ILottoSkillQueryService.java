package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.lottery.LotterySkillPo;

public interface ILottoSkillQueryService {

    Page<LotterySkillPo> getLotterySkillList(PageCondition condition);

    LotterySkillPo getLotterySkillDetail(String seq);
}
