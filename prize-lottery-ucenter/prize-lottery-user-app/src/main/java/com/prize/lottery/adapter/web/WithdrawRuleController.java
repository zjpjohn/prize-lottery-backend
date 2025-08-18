package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IWithdrawCommandService;
import com.prize.lottery.application.command.dto.WithLevelCreateCmd;
import com.prize.lottery.application.command.dto.WithLevelEditCmd;
import com.prize.lottery.application.command.dto.WithRuleCreateCmd;
import com.prize.lottery.application.command.dto.WithRuleEditCmd;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.dto.LevelListQuery;
import com.prize.lottery.infrast.persist.po.WithdrawLevelPo;
import com.prize.lottery.infrast.persist.po.WithdrawRulePo;
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
@RequestMapping("/adm/withdraw")
@Permission(domain = LotteryAuth.MANAGER)
public class WithdrawRuleController {

    private final IWithdrawQueryService   withdrawQueryService;
    private final IWithdrawCommandService withdrawCommandService;

    @PostMapping("/level")
    public void createWithLevel(@RequestBody @Validated WithLevelCreateCmd command) {
        withdrawCommandService.createWithdrawLevel(command);
    }

    @PutMapping("/level")
    public void editWithLevel(@RequestBody @Validated WithLevelEditCmd command) {
        withdrawCommandService.editWithdrawLevel(command);
    }

    @GetMapping("/level/{id}")
    public WithdrawLevelPo withdrawLevel(@PathVariable @NotNull(message = "提现等级标识为空") Long id) {
        return withdrawQueryService.withdrawLevel(id);
    }

    @GetMapping("/level/list")
    public Page<WithdrawLevelPo> levelList(@Validated LevelListQuery query) {
        return withdrawQueryService.withdrawLevelList(query);
    }

    @PostMapping("/rule")
    public void addWithdrawRule(@Validated WithRuleCreateCmd command) {
        withdrawCommandService.createRule(command);
    }

    @PutMapping("/rule")
    public void editWithdrawRule(@Validated WithRuleEditCmd command) {
        withdrawCommandService.modifyRule(command);
    }

    @GetMapping("/rule")
    public WithdrawRulePo withdrawRule(@NotNull(message = "规则标识为空") Long id) {
        return withdrawQueryService.getWithdrawRule(id);
    }

    @GetMapping("/rule/list")
    public List<WithdrawRulePo> withdrawRules() {
        return withdrawQueryService.getWithdrawRules();
    }

}
