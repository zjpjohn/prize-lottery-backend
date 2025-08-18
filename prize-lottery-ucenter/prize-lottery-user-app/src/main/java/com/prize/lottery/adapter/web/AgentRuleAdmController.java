package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAgentRuleCommandService;
import com.prize.lottery.application.command.dto.AgentRuleCreateCmd;
import com.prize.lottery.application.command.dto.AgentRuleEditCmd;
import com.prize.lottery.application.query.IAgentRuleQueryService;
import com.prize.lottery.application.query.dto.AgentRuleQuery;
import com.prize.lottery.infrast.persist.po.AgentRulePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/agent/rule")
@Permission(domain = LotteryAuth.MANAGER)
public class AgentRuleAdmController {

    private final IAgentRuleQueryService   agentRuleQueryService;
    private final IAgentRuleCommandService agentRuleCommandService;

    @PostMapping("/")
    public void createAgentRule(@Validated AgentRuleCreateCmd cmd) {
        agentRuleCommandService.addAgentRule(cmd);
    }

    @PutMapping("/")
    public void modifyAgentRule(@Validated AgentRuleEditCmd cmd) {
        agentRuleCommandService.editAgentRule(cmd);
    }

    @DeleteMapping("/clear")
    public void clearRules() {
        agentRuleCommandService.clearRules();
    }

    @GetMapping("/list")
    public Page<AgentRulePo> ruleList(@Validated AgentRuleQuery query) {
        return agentRuleQueryService.getAgentRuleList(query);
    }

}
