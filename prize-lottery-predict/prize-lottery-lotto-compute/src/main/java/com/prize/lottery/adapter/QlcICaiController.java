package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.IQlcICaiService;
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
@RequestMapping("/qlc")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class QlcICaiController {

    private final IQlcICaiService   qlcICaiService;
    private final ICaiDomainAbility qlcICaiDomainAbility;

    @PostMapping("/fetch/history")
    public void fetchHistory() {
        qlcICaiService.fetchHistoryForecast();
    }

    @PostMapping("/fetch/incr/history")
    public void fetchIncrHistory() {
        qlcICaiService.fetchIncrHistory();
    }

    @PostMapping("/fetch/last")
    public void fetchLast(@NotBlank(message = "期号为空") String before,
                          @Min(value = 1, message = "最小抓取期数大于0") @RequestParam(defaultValue = "30")
                          Integer size) {
        qlcICaiService.fetchLastForecast(before, size);
    }

    @PostMapping("/fetch/{period}")
    public void fetchForecast(@PathVariable String period) {
        qlcICaiService.fetchForecast(period);
    }

    @PostMapping("/hit/batch")
    public void batchCalcHit() {
        qlcICaiService.batchCalcQlcHit();
    }

    @PostMapping("/hit/incr")
    public void incrCalcHit() {
        qlcICaiService.calcIncrAllHit();
    }

    @PostMapping("/hit")
    public void calcHit(String period) {
        qlcICaiDomainAbility.calcForecastHit(period);
    }

    @PostMapping("/rate/batch")
    public void batchCalcRate() {
        qlcICaiService.initCalcMasterRate();
    }

    @PostMapping("/rate/incr")
    public void incrCalcRate() {
        qlcICaiService.incrCalcMasterRate();
    }

    @PostMapping("/rate")
    public void calcRate(String period) {
        qlcICaiDomainAbility.calcMasterRate(period);
    }

    @PostMapping("/rank/batch")
    public void batchCalcRank() {
        qlcICaiService.initCalcMasterRank();
    }

    @PostMapping("/rank")
    public void calcRank(String period) {
        qlcICaiDomainAbility.calcMasterRank(period);
    }

    @PostMapping("/vip")
    public void extractVipMaster(String period) {
        qlcICaiDomainAbility.calcVipMaster(period);
    }

    @PostMapping("/home")
    public void extractHomeMaster(String period) {
        qlcICaiDomainAbility.calcHomeMaster(period);
    }

    @PostMapping("/chart/item")
    public void calcItemChart(String period) {
        qlcICaiDomainAbility.calcItemCensusChart(period);
    }

    @PostMapping("/chart/all")
    public void calcAllChart(String period) {
        qlcICaiDomainAbility.calcAllCensusChart(period);
    }

    @PostMapping("/chart/vip")
    public void calcVipChart(String period) {
        qlcICaiDomainAbility.calcVipMasterChart(period);
    }

    @PostMapping("/chart/rate")
    public void calcRateChart(String period) {
        qlcICaiDomainAbility.calcRateMasterChart(period);
    }

    @PostMapping("/chart/hot")
    public void calcHotChart(String period) {
        qlcICaiDomainAbility.calcHotMasterChart(period);
    }

}
