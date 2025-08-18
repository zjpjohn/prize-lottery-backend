package com.prize.lottery.adapter.adm;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.ITransferRuleCommandService;
import com.prize.lottery.application.command.dto.TransRuleCreateCmd;
import com.prize.lottery.application.command.dto.TransRuleModifyCmd;
import com.prize.lottery.application.query.ITransferRuleQueryService;
import com.prize.lottery.infrast.persist.po.TransferRulePo;
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
@RequestMapping("/adm/audit/rule")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class AuditRuleAdmController {

    private final ITransferRuleQueryService   transferSceneQueryService;
    private final ITransferRuleCommandService transferSceneCommandService;

    @PostMapping("/")
    public void createRule(@Validated TransRuleCreateCmd command) {
        transferSceneCommandService.createRule(command);
    }

    @PutMapping("/")
    public void modifyRule(@Validated TransRuleModifyCmd command) {
        transferSceneCommandService.modifyRule(command);
    }

    @GetMapping("/")
    public TransferRulePo transferRule(@NotNull(message = "提现规则标识为空") Long id) {
        return transferSceneQueryService.getTransferSceneRule(id);
    }

    @GetMapping("/list")
    public List<TransferRulePo> transferRules() {
        return transferSceneQueryService.getTransferRules();
    }

}
