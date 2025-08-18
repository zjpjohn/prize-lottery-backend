package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AdmWithdrawQuery;
import com.prize.lottery.application.query.dto.AppWithdrawQuery;
import com.prize.lottery.application.query.dto.LevelListQuery;
import com.prize.lottery.application.query.vo.*;
import com.prize.lottery.infrast.persist.po.WithdrawLevelPo;
import com.prize.lottery.infrast.persist.po.WithdrawRulePo;

import java.util.List;
import java.util.Map;

public interface IWithdrawQueryService {

    List<Map<String, Object>> transferScenes();

    WithdrawRulePo getWithdrawRule(Long id);

    List<WithdrawRulePo> getWithdrawRules();

    WithdrawRuleVo sceneUsingRule(String scene);

    Page<AppWithdrawVo> getAppUserWithdrawList(AppWithdrawQuery query);

    Page<AppWithdrawVo> getAppAgentWithdrawList(AppWithdrawQuery query);

    Page<AppWithdrawVo> getAppExpertWithdrawList(AppWithdrawQuery query);

    Page<UserWithdrawVo> getAdmUserWithdrawList(AdmWithdrawQuery query);

    Page<AgentWithdrawVo> getAdmAgentWithdrawList(AdmWithdrawQuery query);

    Page<ExpertWithdrawVo> getAdmExpertWithdrawList(AdmWithdrawQuery query);

    UserWithdrawVo getUserWithdraw(String seqNo);

    AgentWithdrawVo getAgentWithdraw(String seqNo);

    ExpertWithdrawVo getExpertWithdraw(String seqNo);

    Page<WithdrawLevelPo> withdrawLevelList(LevelListQuery query);

    WithdrawLevelPo withdrawLevel(Long id);

    WithdrawLevelPo sceneUsingLevel(String scene);

}
