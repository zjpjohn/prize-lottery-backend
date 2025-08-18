package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAnnounceCommandService;
import com.prize.lottery.application.command.dto.AnnounceCreateCmd;
import com.prize.lottery.application.command.dto.AnnounceModifyCmd;
import com.prize.lottery.application.query.IAnnounceInfoQueryService;
import com.prize.lottery.application.query.IRemindInfoQueryService;
import com.prize.lottery.application.query.dto.AnnounceAdmQuery;
import com.prize.lottery.application.query.dto.RemindAdmQuery;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/message")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class MessageAdmController {

    private final IAnnounceCommandService   announceCommandService;
    private final IAnnounceInfoQueryService announceInfoQueryService;
    private final IRemindInfoQueryService   remindInfoQueryService;

    @PostMapping("/announce")
    public void createAnnounce(@Validated AnnounceCreateCmd command) {
        announceCommandService.createAnnounce(command);
    }

    @PutMapping("/announce")
    public void editAnnounce(@Validated AnnounceModifyCmd command) {
        announceCommandService.editAnnounce(command);
    }

    @GetMapping("/announce/list")
    public Page<AnnounceInfoPo> announceList(@Validated AnnounceAdmQuery query) {
        return announceInfoQueryService.announceAdmQuery(query);
    }

    @GetMapping("/remind/list")
    public Page<RemindInfoPo> remindList(@Validated RemindAdmQuery query) {
        return remindInfoQueryService.remindAdmQuery(query);
    }

}
