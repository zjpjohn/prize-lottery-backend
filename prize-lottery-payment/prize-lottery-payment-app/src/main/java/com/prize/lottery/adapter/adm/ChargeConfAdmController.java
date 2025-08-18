package com.prize.lottery.adapter.adm;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IChargeConfCommandService;
import com.prize.lottery.application.command.dto.ChargeConfCreateCmd;
import com.prize.lottery.application.command.dto.ChargeConfEditCmd;
import com.prize.lottery.application.query.IChargeConfQueryService;
import com.prize.lottery.infrast.persist.po.ChargeConfPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/charge/conf")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class ChargeConfAdmController {

    private final IChargeConfCommandService commandService;
    private final IChargeConfQueryService   queryService;

    @PostMapping("/")
    public void createConf(@Validated ChargeConfCreateCmd command) {
        commandService.createConf(command);
    }

    @PutMapping("/")
    public void editConf(@Validated ChargeConfEditCmd command) {
        commandService.editConf(command);
    }

    @DeleteMapping("/")
    public void removeConf() {
        commandService.removeConf();
    }

    @GetMapping("/list")
    public List<ChargeConfPo> queryList() {
        return queryService.queryConfList();
    }

}
