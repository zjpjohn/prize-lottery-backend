package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IChannelInfoCommandService;
import com.prize.lottery.application.command.dto.ChannelCreateCmd;
import com.prize.lottery.application.command.dto.ChannelModifyCmd;
import com.prize.lottery.application.query.IChannelInfoQueryService;
import com.prize.lottery.application.query.dto.ChannelListQuery;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
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
@RequestMapping("/adm/channel")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class ChannelAdmController {

    private final IChannelInfoQueryService   channelInfoQueryService;
    private final IChannelInfoCommandService channelInfoCommandService;

    @PostMapping("/")
    public void createChannel(@Validated ChannelCreateCmd command) {
        channelInfoCommandService.createChannel(command);
    }

    @PutMapping("/")
    public void editChannel(@Validated ChannelModifyCmd command) {
        channelInfoCommandService.editChannel(command);
    }

    @GetMapping("/")
    public ChannelInfoPo channelInfo(@NotBlank(message = "取到值不允许为空") String channel) {
        return channelInfoQueryService.channelDetail(channel);
    }

    @GetMapping("/{type}/list")
    public List<ChannelInfoPo> typedChannels(
            @PathVariable @Enumerable(ranges = {"0", "1"}, message = "类型标识错误") Integer type) {
        return channelInfoQueryService.getUsingTypedChannels(type);
    }

    @GetMapping("/list")
    public Page<ChannelInfoPo> channelList(@Validated ChannelListQuery query) {
        return channelInfoQueryService.channelList(query);
    }

}
