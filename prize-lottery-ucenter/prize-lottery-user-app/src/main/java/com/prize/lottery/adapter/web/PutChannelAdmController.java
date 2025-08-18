package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IPutChannelCommandService;
import com.prize.lottery.application.command.dto.PutChannelCreateCmd;
import com.prize.lottery.application.command.dto.PutChannelEditCmd;
import com.prize.lottery.application.command.dto.PutRecordCreateCmd;
import com.prize.lottery.application.command.dto.PutRecordEditCmd;
import com.prize.lottery.application.query.IPutChannelQueryService;
import com.prize.lottery.application.query.dto.PutChannelQuery;
import com.prize.lottery.application.query.vo.PutChannelVo;
import com.prize.lottery.infrast.persist.po.PutRecordPo;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/put")
@Permission(domain = LotteryAuth.MANAGER)
public class PutChannelAdmController {

    private final IPutChannelCommandService commandService;
    private final IPutChannelQueryService   queryService;

    @PostMapping("/channel")
    public void createChannel(@Validated PutChannelCreateCmd command) {
        commandService.createChannel(command);
    }

    @PutMapping("/channel")
    public void editChannel(@Validated PutChannelEditCmd command) {
        commandService.editChannel(command);
    }

    @GetMapping("/channel")
    public PutChannelVo putChannel(@NotBlank(message = "渠道标识为空") String bizNo) {
        return queryService.getPutChannel(bizNo);
    }

    @GetMapping("/channel/list")
    public Page<PutChannelVo> putChannelList(@Validated PutChannelQuery query) {
        return queryService.getChannelList(query);
    }

    @PostMapping("/record")
    public void createRecord(@Validated PutRecordCreateCmd command) {
        commandService.createRecord(command);
    }

    @PutMapping("/record")
    public void editRecord(@Validated PutRecordEditCmd command) {
        commandService.editRecord(command);
    }

    @GetMapping("/record/list")
    public List<PutRecordPo> putRecords(@NotBlank(message = "查询渠道") String channel) {
        return queryService.getPutRecords(channel);
    }

}

