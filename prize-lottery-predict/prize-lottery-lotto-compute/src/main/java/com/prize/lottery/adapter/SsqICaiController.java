package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.ISsqICaiService;
import com.prize.lottery.domain.ICaiDomainAbility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/ssq")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class SsqICaiController {

    private final ISsqICaiService   ssqICaiService;
    private final ICaiDomainAbility ssqICaiDomainAbility;

    @PostMapping("/fetch/history")
    public void fetchHistory() {
        ssqICaiService.fetchHistoryForecast();
    }

    @PostMapping("/fetch/incr")
    public void fetchIncrHistory() {
        ssqICaiService.fetchIncrHistory();
    }

    @PostMapping("/fetch/last")
    public void fetchLast(@NotBlank(message = "期号为空") String before,
                          @Min(value = 1, message = "最小抓取期数大于0") @RequestParam(defaultValue = "30")
                          Integer size) {
        ssqICaiService.fetchLastForecast(before, size);
    }

    @PostMapping("/fetch/{period}")
    public void fetchForecast(@PathVariable String period) {
        ssqICaiService.fetchForecast(period);
    }

    @PostMapping("/hit/batch")
    public void batchCalcHit() {
        ssqICaiService.batchCalcSsqHit();
    }

    @PostMapping("/hit/incr")
    public void incrCalcHit() {
        ssqICaiService.calcIncrAllHit();
    }

    @PostMapping("/hit")
    public void calcHit(String period) {
        ssqICaiDomainAbility.calcForecastHit(period);
    }

    @PostMapping("/rate/batch")
    public void batchCalcRate() {
        ssqICaiService.initCalcMasterRate();
    }

    @PostMapping("/rate/incr")
    public void incrCalcRate() {
        ssqICaiService.incrCalcMasterRate();
    }

    @PostMapping("/rate")
    public void calcRate(String period) {
        ssqICaiDomainAbility.calcMasterRate(period);
    }

    @PostMapping("/rank/batch")
    public void batchCalcRank() {
        ssqICaiService.initCalcMasterRank();
    }

    @PostMapping("/rank")
    public void calcRank(String period) {
        ssqICaiDomainAbility.calcMasterRank(period);
    }

    @PostMapping("/vip")
    public void extractVipMaster(String period) {
        ssqICaiDomainAbility.calcVipMaster(period);
    }

    @PostMapping("/home")
    public void extractHomeMaster(String period) {
        ssqICaiDomainAbility.calcHomeMaster(period);
    }

    @PostMapping("/chart/item")
    public void calcItemChart(String period) {
        ssqICaiDomainAbility.calcItemCensusChart(period);
    }

    @PostMapping("/chart/all")
    public void calcAllChart(String period) {
        ssqICaiDomainAbility.calcAllCensusChart(period);
    }

    @PostMapping("/chart/vip")
    public void calcVipChart(String period) {
        ssqICaiDomainAbility.calcVipMasterChart(period);
    }

    @PostMapping("/chart/rate")
    public void calcRateChart(String period) {
        ssqICaiDomainAbility.calcRateMasterChart(period);
    }

    @PostMapping("/chart/hot")
    public void calcHotChart(String period) {
        ssqICaiDomainAbility.calcHotMasterChart(period);
    }

}
