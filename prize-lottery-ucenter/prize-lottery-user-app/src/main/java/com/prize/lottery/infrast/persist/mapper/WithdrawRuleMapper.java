package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.WithdrawLevelPo;
import com.prize.lottery.infrast.persist.po.WithdrawRulePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WithdrawRuleMapper {

    int addWithdrawRule(WithdrawRulePo rule);

    int editWithdrawRule(WithdrawRulePo rule);

    int autoRevokeWithdrawRule(WithdrawRulePo rule);

    WithdrawRulePo getWithdrawRuleById(Long id);

    List<WithdrawRulePo> getWithdrawSceneRules(String scene);

    List<WithdrawRulePo> getAllWithdrawRules();

    WithdrawRulePo getSceneUsingRule(String scene);

    int addWithdrawLevel(WithdrawLevelPo level);

    int editWithdrawLevel(WithdrawLevelPo level);

    int revokeSceneLevel(String scene);

    WithdrawLevelPo getWithdrawLevel(Long id);

    WithdrawLevelPo getSceneUsingLevel(String scene);

    List<WithdrawLevelPo> getWithdrawLevelList(PageCondition condition);

    int countWithdrawLevels(PageCondition condition);

}
