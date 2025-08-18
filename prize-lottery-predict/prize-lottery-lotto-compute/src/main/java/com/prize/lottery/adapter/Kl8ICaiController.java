package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.IKl8ICaiService;
import com.prize.lottery.domain.ICaiDomainAbility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/kl8")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class Kl8ICaiController {

    private final IKl8ICaiService   kl8ICaiService;
    private final ICaiDomainAbility kl8ICaiDomainAbility;

    @PostMapping("/hit/init")
    public void initCalcHit() {
        kl8ICaiService.initCalcKl8Hit();
    }

    @PostMapping("/hit")
    public void calcHit(String period) {
        kl8ICaiDomainAbility.calcForecastHit(period);
    }

    @PostMapping("/rate/init")
    public void initCalcRate() {
        kl8ICaiService.initCalcMasterRate();
    }

    @PostMapping("/rate")
    public void calcRate(String period) {
        kl8ICaiDomainAbility.calcMasterRate(period);
    }

    @PostMapping("/rank/init")
    public void initCalcRank() {
        kl8ICaiService.initCalcMasterRank();
    }

    @PostMapping("/rank")
    public void calcRank(String period) {
        kl8ICaiDomainAbility.calcMasterRank(period);
    }

    @PostMapping("/chart-full")
    public void fullChart(String period) {
        kl8ICaiDomainAbility.calcAllCensusChart(period);
    }

}
