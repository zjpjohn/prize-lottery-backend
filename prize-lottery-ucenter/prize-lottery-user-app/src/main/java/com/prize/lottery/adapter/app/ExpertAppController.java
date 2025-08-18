package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IExpertCommandService;
import com.prize.lottery.application.command.IWithdrawCommandService;
import com.prize.lottery.application.command.dto.ExpertCreateCmd;
import com.prize.lottery.application.command.dto.ExpertResetPwdCmd;
import com.prize.lottery.application.command.dto.WithdrawCreateCmd;
import com.prize.lottery.application.command.vo.ExpertAccountVo;
import com.prize.lottery.application.query.IUserExpertQueryService;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.dto.AppWithdrawQuery;
import com.prize.lottery.application.query.dto.IncomeListQuery;
import com.prize.lottery.application.query.vo.AppWithdrawVo;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.po.ExpertIncomePo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/expert")
@Permission(domain = LotteryAuth.USER)
public class ExpertAppController {

    private final IUserExpertQueryService expertQueryService;
    private final IExpertCommandService   expertCommandService;
    private final IWithdrawCommandService withdrawCommandService;
    private final IWithdrawQueryService   withdrawQueryService;

    @PostMapping("/")
    public void createExpert(@Validated ExpertCreateCmd command) {
        expertCommandService.createExpertAcct(command);
    }

    @PutMapping("/reset")
    public void resetAcctPwd(@Validated ExpertResetPwdCmd password) {
        expertCommandService.resetExpertAcctPwd(password);
    }

    @GetMapping("/")
    public ExpertAccountVo acctDetail(@NotNull(message = "用户标识为空") Long userId) {
        return expertCommandService.getExpertAcctDetail(userId);
    }

    @GetMapping("/income")
    public Page<ExpertIncomePo> incomeLogList(@Validated IncomeListQuery query) {
        return expertQueryService.getExpertIncomeList(query);
    }

    @PostMapping("/withdraw")
    public void expertWithdraw(@Validated WithdrawCreateCmd command) {
        withdrawCommandService.payWithdraw(command, TransferScene.USER_EXPERT_TRANS);
    }

    @GetMapping("/withdraw/list")
    public Page<AppWithdrawVo> withdrawList(@Validated AppWithdrawQuery query) {
        return withdrawQueryService.getAppExpertWithdrawList(query);
    }

}
