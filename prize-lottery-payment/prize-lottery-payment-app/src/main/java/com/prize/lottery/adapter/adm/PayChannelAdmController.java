package com.prize.lottery.adapter.adm;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IPayChannelCommandService;
import com.prize.lottery.application.command.dto.PayChannelCreateCmd;
import com.prize.lottery.application.command.dto.PayChannelModifyCmd;
import com.prize.lottery.application.query.IPayChannelQueryService;
import com.prize.lottery.infrast.persist.po.PayChannelPo;
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
@RequestMapping("/adm/channel")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class PayChannelAdmController {

    private final IPayChannelCommandService payChannelCommandService;
    private final IPayChannelQueryService   payChannelQueryService;

    @PostMapping("/")
    public void createChannel(@Validated PayChannelCreateCmd command) {
        payChannelCommandService.createChannel(command);
    }

    @PutMapping("/")
    public void modifyChannel(@Validated PayChannelModifyCmd command) {
        payChannelCommandService.modifyChannel(command);
    }

    @GetMapping("/")
    public PayChannelPo channel(@NotNull(message = "唯一标识为空") Long id) {
        return payChannelQueryService.getPayChannel(id);
    }

    @GetMapping("/list")
    public List<PayChannelPo> channels() {
        return payChannelQueryService.getAllPayChannels();
    }

}
