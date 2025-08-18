package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IWithdrawCommandService;
import com.prize.lottery.application.command.dto.WithdrawCreateCmd;
import com.prize.lottery.application.query.IAgentAcctQueryService;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.dto.AgentIncomeQuery;
import com.prize.lottery.application.query.dto.AgentMetricsQuery;
import com.prize.lottery.application.query.dto.AppWithdrawQuery;
import com.prize.lottery.application.query.vo.AgentAccountVo;
import com.prize.lottery.application.query.vo.AgentIncomeVo;
import com.prize.lottery.application.query.vo.AgentMetricsVo;
import com.prize.lottery.application.query.vo.AppWithdrawVo;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/agent")
@Permission(domain = LotteryAuth.USER)
public class AgentAcctController {

    private final IWithdrawQueryService   withdrawQueryService;
    private final IWithdrawCommandService withdrawCommandService;
    private final IAgentAcctQueryService  agentAcctQueryService;

    @GetMapping("/acct")
    public AgentAccountVo agentAccount(@NotNull(message = "用户标识为空") Long userId) {
        return agentAcctQueryService.agentAccount(userId);
    }

    @GetMapping("/metrics")
    public List<AgentMetricsVo> metricsList(@Validated AgentMetricsQuery query) {
        return agentAcctQueryService.agentMetricsList(query);
    }

    @GetMapping("/income/list")
    public Page<AgentIncomeVo> incomeList(@Validated AgentIncomeQuery query) {
        return agentAcctQueryService.agentIncomeList(query);
    }

    @PostMapping("/withdraw")
    public void agentWithdraw(@Validated WithdrawCreateCmd command) {
        withdrawCommandService.payWithdraw(command, TransferScene.USER_AGENT_TRANS);
    }

    @GetMapping("/withdraw/list")
    public Page<AppWithdrawVo> withdrawList(@Validated AppWithdrawQuery query) {
        return withdrawQueryService.getAppAgentWithdrawList(query);
    }

}
