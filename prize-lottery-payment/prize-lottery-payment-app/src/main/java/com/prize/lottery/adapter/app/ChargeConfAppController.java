package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IChargeConfQueryService;
import com.prize.lottery.application.query.vo.ChargeConfVo;
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
@RequestMapping("/app/charge/conf")
@Permission(domain = LotteryAuth.USER)
public class ChargeConfAppController {

    private final IChargeConfQueryService queryService;

    @GetMapping("/list")
    public List<ChargeConfVo> configList() {
        return queryService.usingConfList();
    }

}
