package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAgentApplyCommandService;
import com.prize.lottery.application.command.dto.AgentApplyConfirmCmd;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.IUserInviteQueryService;
import com.prize.lottery.application.query.dto.AgentApplyQuery;
import com.prize.lottery.application.query.dto.AgentInvitedQuery;
import com.prize.lottery.application.query.dto.AgentUserQuery;
import com.prize.lottery.infrast.persist.vo.AgentInvitedUserVo;
import com.prize.lottery.infrast.persist.vo.AgentUserInviteVo;
import com.prize.lottery.infrast.persist.vo.UserAgentApplyVo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/agent")
@Permission(domain = LotteryAuth.MANAGER)
public class UserAgentAdmController {

    private final IUserInfoQueryService     userQueryService;
    private final IUserInviteQueryService   inviteQueryService;
    private final IAgentApplyCommandService agentCommandService;

    @GetMapping("/apply/list")
    public Page<UserAgentApplyVo> applyList(@Validated AgentApplyQuery query) {
        return userQueryService.getUserAgentApplyList(query);
    }

    @PutMapping("/apply")
    public void confirmApply(@Validated AgentApplyConfirmCmd cmd) {
        agentCommandService.confirmApply(cmd);
    }

    @GetMapping("/top")
    public List<AgentUserInviteVo> topAgentInvite(
            @Positive(message = "必须为正数") @Max(value = 50, message = "最大值不超过50") Integer limit) {
        Integer topN = Optional.ofNullable(limit).orElse(7);
        return inviteQueryService.topUserInvite(topN);
    }

    @GetMapping("/list")
    public Page<AgentUserInviteVo> agentUsers(@Validated AgentUserQuery query) {
        return inviteQueryService.getAgentUserList(query);
    }

    @GetMapping("/detail")
    public AgentUserInviteVo agentUser(@NotNull(message = "用户标识为空") Long userId) {
        return inviteQueryService.getAgentUserDetail(userId);
    }

    @GetMapping("/invited/list")
    public Page<AgentInvitedUserVo> invitedList(@Validated AgentInvitedQuery query) {
        return inviteQueryService.getAgentInvitedList(query);
    }

}
