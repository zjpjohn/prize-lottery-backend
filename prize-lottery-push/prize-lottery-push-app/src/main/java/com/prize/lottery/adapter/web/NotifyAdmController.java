package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.INotifyInfoCommandService;
import com.prize.lottery.application.command.dto.NotifyInfoCreateCmd;
import com.prize.lottery.application.command.dto.NotifyInfoModifyCmd;
import com.prize.lottery.application.query.INotifyInfoQueryService;
import com.prize.lottery.application.query.dto.NotifyInfoQuery;
import com.prize.lottery.infrast.persist.po.NotifyInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/notify")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class NotifyAdmController {

    private final INotifyInfoQueryService   notifyInfoQueryService;
    private final INotifyInfoCommandService notifyInfoCommandService;

    @PostMapping("/")
    public void createNotify(@Validated NotifyInfoCreateCmd command) {
        notifyInfoCommandService.createNotifyInfo(command);
    }

    @PutMapping("/")
    public void modifyNotify(@Validated NotifyInfoModifyCmd command) {
        notifyInfoCommandService.modifyNotifyInfo(command);
    }

    @GetMapping("/{id}")
    public NotifyInfoPo notifyInfo(@PathVariable Long id) {
        return notifyInfoQueryService.getNotifyInfo(id);
    }

    @GetMapping("/list")
    public Page<NotifyInfoPo> notifyList(@Validated NotifyInfoQuery query) {
        return notifyInfoQueryService.getNotifyInfoList(query);
    }

}
