package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.lottery.LotterySkillPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotterySkillMapper {

    int addLotterySkill(LotterySkillPo skill);

    int editLotterySkill(LotterySkillPo skill);

    int hasLotterySkill();

    LotterySkillPo getLotterySkill(@Param("seq") String seq, @Param("detail") Boolean detail);

    List<LotterySkillPo> getLotterySkills(PageCondition condition);

    int countLotterySkills(PageCondition condition);

    List<LotterySkillPo> getGroupedTopSkillList();

}
