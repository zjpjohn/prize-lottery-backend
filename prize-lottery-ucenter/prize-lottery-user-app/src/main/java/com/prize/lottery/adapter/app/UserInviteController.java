package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IAgentRuleQueryService;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.dto.UserInviteQuery;
import com.prize.lottery.application.query.vo.UserAgentRuleVo;
import com.prize.lottery.application.query.vo.UserInviteVo;
import com.prize.lottery.infrast.persist.po.AgentRulePo;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/invite")
@Permission(domain = LotteryAuth.USER)
public class UserInviteController {

    private final IUserInfoQueryService  userQueryService;
    private final IAgentRuleQueryService agentRuleQueryService;

    @GetMapping("/rule")
    public UserAgentRuleVo agentRule(@NotNull(message = "用户标识为空") Long userId) {
        return agentRuleQueryService.getUserAgentRule(userId);
    }

    @GetMapping("/rule/using")
    public List<AgentRulePo> usingRules() {
        return agentRuleQueryService.getAllUsingAgentRules();
    }

    @GetMapping("/")
    public UserInviteVo userInvite(@NotNull(message = "用户标识为空") Long userId) {
        return userQueryService.getUserInviteInfo(userId);
    }

    @GetMapping("/users")
    public Page<UserInfoPo> inviteUsers(@Validated UserInviteQuery query) {
        return userQueryService.getInvitedUserList(query);
    }

}
