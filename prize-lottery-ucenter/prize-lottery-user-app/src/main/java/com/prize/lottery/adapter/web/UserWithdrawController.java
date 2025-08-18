package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.dto.AdmWithdrawQuery;
import com.prize.lottery.application.query.vo.AgentWithdrawVo;
import com.prize.lottery.application.query.vo.ExpertWithdrawVo;
import com.prize.lottery.application.query.vo.UserWithdrawVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/withdraw")
@Permission(domain = LotteryAuth.MANAGER)
public class UserWithdrawController {

    private final IWithdrawQueryService withdrawQueryService;

    @GetMapping("/user/list")
    public Page<UserWithdrawVo> userWithdrawList(@Validated AdmWithdrawQuery query) {
        return withdrawQueryService.getAdmUserWithdrawList(query);
    }

    @GetMapping("/user/")
    public UserWithdrawVo userWithdraw(@NotBlank(message = "提现编号为空") String seqNo) {
        return withdrawQueryService.getUserWithdraw(seqNo);
    }

    @GetMapping("/agent/list")
    public Page<AgentWithdrawVo> agentWithdrawList(@Validated AdmWithdrawQuery query) {
        return withdrawQueryService.getAdmAgentWithdrawList(query);
    }

    @GetMapping("/agent/")
    public AgentWithdrawVo agentWithdraw(@NotBlank(message = "提现编号为空") String seqNo) {
        return withdrawQueryService.getAgentWithdraw(seqNo);
    }

    @GetMapping("/expert/list")
    public Page<ExpertWithdrawVo> expertWithdrawList(@Validated AdmWithdrawQuery query) {
        return withdrawQueryService.getAdmExpertWithdrawList(query);
    }

    @GetMapping("/expert/")
    public ExpertWithdrawVo existWithdraw(@NotBlank(message = "提现编号为空") String seqNo) {
        return withdrawQueryService.getExpertWithdraw(seqNo);
    }

}
