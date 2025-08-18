package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.IDltICaiService;
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
@RequestMapping("/dlt")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class DltICaiController {

    private final IDltICaiService   dltICaiService;
    private final ICaiDomainAbility dltICaiDomainAbility;

    @PostMapping("/fetch/history")
    public void fetchHistory() {
        dltICaiService.fetchHistoryForecast();
    }

    @PostMapping("/fetch/incr/history")
    public void fetchIncrHistory() {
        dltICaiService.fetchIncrHistory();
    }

    @PostMapping("/fetch/last")
    public void fetchLast(@NotBlank(message = "期号为空") String before,
                          @Min(value = 1, message = "最小抓取期数大于0") @RequestParam(defaultValue = "30")
                          Integer size) {
        dltICaiService.fetchLastForecast(before, size);
    }

    @PostMapping("/fetch/{period}")
    public void fetchForecast(@PathVariable String period) {
        dltICaiService.fetchForecast(period);
    }

    @PostMapping("/hit/batch")
    public void batchCalcHit() {
        dltICaiService.batchCalcDltHit();
    }

    @PostMapping("/hit/incr")
    public void incrCalcHit() {
        dltICaiService.calcIncrAllHit();
    }

    @PostMapping("/hit")
    public void calcHit(String period) {
        dltICaiDomainAbility.calcForecastHit(period);
    }

    @PostMapping("/rate/batch")
    public void batchCalcRate() {
        dltICaiService.initCalcMasterRate();
    }

    @PostMapping("/rate/incr")
    public void incrCalcRate() {
        dltICaiService.incrCalcMasterRate();
    }

    @PostMapping("/rate")
    public void calcRate(String period) {
        dltICaiDomainAbility.calcMasterRate(period);
    }

    @PostMapping("/rank/batch")
    public void batchCalcRank() {
        dltICaiService.initCalcMasterRank();
    }

    @PostMapping("/rank")
    public void calcRank(String period) {
        dltICaiDomainAbility.calcMasterRank(period);
    }

    @PostMapping("/vip")
    public void extractVipMaster(String period) {
        dltICaiDomainAbility.calcVipMaster(period);
    }

    @PostMapping("/home")
    public void extractHomeMaster(String period) {
        dltICaiDomainAbility.calcHomeMaster(period);
    }

    @PostMapping("/chart/item")
    public void calcItemChart(String period) {
        dltICaiDomainAbility.calcItemCensusChart(period);
    }

    @PostMapping("/chart/all")
    public void calcAllChart(String period) {
        dltICaiDomainAbility.calcAllCensusChart(period);
    }

    @PostMapping("/chart/vip")
    public void calcVipChart(String period) {
        dltICaiDomainAbility.calcVipMasterChart(period);
    }

    @PostMapping("/chart/rate")
    public void calcRateChart(String period) {
        dltICaiDomainAbility.calcRateMasterChart(period);
    }

    @PostMapping("/chart/hot")
    public void calcHotChart(String period) {
        dltICaiDomainAbility.calcHotMasterChart(period);
    }

}
