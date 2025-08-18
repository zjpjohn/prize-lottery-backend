package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAgentApplyCommandService;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.infrast.persist.po.AgentApplyPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/agent/apply")
@Permission(domain = LotteryAuth.USER)
public class AgentApplyController {

    private final IUserInfoQueryService     userQueryService;
    private final IAgentApplyCommandService applyCommandService;

    @PostMapping("/")
    public void agentApply(@NotNull(message = "用户标识为空") Long userId) {
        applyCommandService.agentApply(userId);
    }

    @PutMapping("/{id}")
    public void cancelApply(@PathVariable Long id, @NotNull(message = "用户标识为空") Long userId) {
        applyCommandService.cancelAgentApply(id, userId);
    }

    @GetMapping("/list")
    public List<AgentApplyPo> userApplies(@NotNull(message = "用户标识为空") Long userId) {
        return userQueryService.getAgentApplies(userId);
    }

}
