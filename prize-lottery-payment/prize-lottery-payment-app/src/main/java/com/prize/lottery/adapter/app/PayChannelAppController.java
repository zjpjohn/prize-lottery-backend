package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IPayChannelQueryService;
import com.prize.lottery.application.query.vo.PayChannelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
@Permission(domain = LotteryAuth.USER)
public class PayChannelAppController {

    private final IPayChannelQueryService payChannelQueryService;

    @GetMapping("/pay")
    public List<PayChannelVo> payChannels() {
        return payChannelQueryService.getPayChannels();
    }

    @GetMapping("/withdraw")
    public List<PayChannelVo> withdrawChannels() {
        return payChannelQueryService.getWithdrawChannels();
    }

}
