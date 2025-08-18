package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.INotifyAppCommandService;
import com.prize.lottery.application.command.dto.NotifyAppCreateCmd;
import com.prize.lottery.application.command.dto.NotifyAppModifyCmd;
import com.prize.lottery.application.query.INotifyAppQueryService;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/app")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class AppAdmController {

    private final INotifyAppCommandService notifyAppCommandService;
    private final INotifyAppQueryService   notifyAppQueryService;

    @PostMapping("/")
    public void createNotifyApp(@Validated NotifyAppCreateCmd command) {
        notifyAppCommandService.createNotifyApp(command);
    }

    @PutMapping("/")
    public void modifyNotifyApp(@Validated NotifyAppModifyCmd command) {
        notifyAppCommandService.modifyNotifyApp(command);
    }

    @GetMapping("/list")
    public List<NotifyAppPo> notifyAppList() {
        return notifyAppQueryService.getAppList();
    }

}
