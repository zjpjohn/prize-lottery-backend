package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppConfCommandService;
import com.prize.lottery.application.command.dto.ConfCreateCmd;
import com.prize.lottery.application.command.dto.ConfModifyCmd;
import com.prize.lottery.application.query.IAppConfQueryService;
import com.prize.lottery.application.query.dto.AppConfQuery;
import com.prize.lottery.infrast.persist.po.AppConfPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/conf")
@Permission(domain = LotteryAuth.MANAGER)
public class AppConfAdmController {

    private final IAppConfCommandService appConfCommandService;
    private final IAppConfQueryService   appConfQueryService;

    @PostMapping
    public void createAppConf(@Validated ConfCreateCmd command) {
        appConfCommandService.createAppConf(command);
    }

    @PutMapping
    public void modifyAppConf(@Validated ConfModifyCmd command) {
        appConfCommandService.modifyAppConf(command);
    }

    @GetMapping("/list")
    public Page<AppConfPo> appConfList(@Validated AppConfQuery query) {
        return appConfQueryService.getAppConfList(query);
    }

}
