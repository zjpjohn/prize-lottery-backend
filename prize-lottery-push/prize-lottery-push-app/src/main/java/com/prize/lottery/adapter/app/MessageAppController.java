package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IChannelInfoCommandService;
import com.prize.lottery.application.command.dto.MessageClearCmd;
import com.prize.lottery.application.query.IAnnounceInfoQueryService;
import com.prize.lottery.application.query.IChannelInfoQueryService;
import com.prize.lottery.application.query.IRemindInfoQueryService;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.vo.ChannelMessageVo;
import com.prize.lottery.application.query.vo.MessageInfoVo;
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
@RequestMapping("/app/message")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.USER)
public class MessageAppController {

    private final IRemindInfoQueryService    remindInfoQueryService;
    private final IChannelInfoQueryService   channelInfoQueryService;
    private final IAnnounceInfoQueryService  announceInfoQueryService;
    private final IChannelInfoCommandService channelInfoCommandService;

    @GetMapping("/channel")
    public List<ChannelMessageVo> channelMessages(@NotNull(message = "用户标识为空") Long userId) {
        return channelInfoQueryService.messageList(userId);
    }

    @PostMapping(value = "/list", params = "type=announce")
    public Page<MessageInfoVo> announceMessages(@Validated MessageAppQuery query) {
        return announceInfoQueryService.announceAppQuery(query);
    }

    @PostMapping(value = "/list", params = "type=remind")
    public Page<MessageInfoVo> remindMessages(@Validated MessageAppQuery query) {
        return remindInfoQueryService.remindAppQuery(query);
    }

    @PostMapping("/clear")
    public void clearMessages(@RequestBody @Validated MessageClearCmd command) {
        channelInfoCommandService.clearMessage(command);
    }

}
