package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.N3TodayPivotCmd;
import com.prize.lottery.application.cmd.Pl3ComSelectedCmd;
import com.prize.lottery.application.service.ILotteryIndexService;
import com.prize.lottery.application.service.IPl3ICaiService;
import com.prize.lottery.application.vo.DanKillCalcResult;
import com.prize.lottery.application.vo.N3DifferAnalyzeVo;
import com.prize.lottery.application.vo.Pl3ComRecommendVo;
import com.prize.lottery.application.vo.Pl3DkRecommendVo;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.n3item.domain.N3ItemCensusDo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.value.BallIndex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/pl3")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class Pl3ICaiController {

    private final IPl3ICaiService      pl3ICaiService;
    private final ICaiDomainAbility    pl3ICaiDomainAbility;
    private final ILotteryIndexService lotteryIndexService;

    @PostMapping("/full/index")
    public List<BallIndex> fullIndex(@NotBlank(message = "期号不允许为空") String period) {
        return lotteryIndexService.pl3FullIndex(period);
    }

    @PostMapping("/vip/index")
    public List<BallIndex> vipIndex(@NotBlank(message = "期号不允许为空") String period) {
        return lotteryIndexService.pl3VipIndex(period);
    }

    @PostMapping("/index")
    public void lottoIndex(@NotBlank(message = "期号不允许为空") String period) {
        lotteryIndexService.pl3LottoIndex(period);
    }

    @PostMapping("/fetch/history")
    public void fetchHistory() {
        pl3ICaiService.fetchHistoryForecast();
    }

    @PostMapping("/fetch/incr/history")
    public void fetchIncrHistory() {
        pl3ICaiService.fetchIncrHistory();
    }

    @PostMapping("/fetch/last")
    public void fetchLast(@NotBlank(message = "期号为空") String before,
                          @Min(value = 1, message = "最小抓取期数大于0") @RequestParam(defaultValue = "35")
                          Integer size) {
        pl3ICaiService.fetchLastForecast(before, size);
    }

    @PostMapping("/fetch/{period}")
    public void fetchForecast(@PathVariable @NotBlank(message = "预测期号为空") String period) {
        pl3ICaiService.fetchForecast(period);
    }

    @PostMapping("/hit/batch")
    public void batchCalcHit() {
        pl3ICaiService.batchCalcPl3Hit();
    }

    @PostMapping("/hit/incr")
    public void incrCalcHit() {
        pl3ICaiService.calcIncrAllHit();
    }

    @PostMapping("/hit")
    public void calcHit(String period) {
        pl3ICaiDomainAbility.calcForecastHit(period);
    }

    @PostMapping("/rate/batch")
    public void batchCalcRate() {
        pl3ICaiService.initCalcMasterRate();
    }

    @PostMapping("/rate/incr")
    public void incrCalcRate() {
        pl3ICaiService.initIncrCalcRate();
    }

    @PostMapping("/rate")
    public void calcRate(String period) {
        pl3ICaiDomainAbility.calcMasterRate(period);
    }

    @PostMapping("/rank/batch")
    public void batchCalcRank() {
        pl3ICaiService.initCalcMasterRank();
    }

    @PostMapping("/rank")
    public void calcRank(String period) {
        pl3ICaiDomainAbility.calcMasterRank(period);
    }

    @PostMapping("/vip")
    public void extractVipMaster(String period) {
        pl3ICaiDomainAbility.calcVipMaster(period);
    }

    @PostMapping("/home")
    public void extractHomeMaster(String period) {
        pl3ICaiDomainAbility.calcHomeMaster(period);
    }

    @PostMapping("/chart/item")
    public void calcItemChart(String period) {
        pl3ICaiDomainAbility.calcItemCensusChart(period);
    }

    @PostMapping("/chart/all")
    public void calcAllChart(String period) {
        pl3ICaiDomainAbility.calcAllCensusChart(period);
    }

    @PostMapping("/chart/vip")
    public void calcVipChart(String period) {
        pl3ICaiDomainAbility.calcVipMasterChart(period);
    }

    @PostMapping("/chart/rate")
    public void calcRateChart(String period) {
        pl3ICaiDomainAbility.calcRateMasterChart(period);
    }

    @PostMapping("/chart/hot")
    public void calcHotChart(String period) {
        pl3ICaiDomainAbility.calcHotMasterChart(period);
    }

    @PostMapping("/comRec/analyze")
    public Pl3ComRecommendVo comRecommendAna(@RequestBody @Validated Pl3ComSelectedCmd cmd) {
        return pl3ICaiService.comRecommendAna(cmd);
    }

    @PostMapping("/dk/recommend")
    public Pl3DkRecommendVo dkRecommend(@Validated @RequestBody DkRecommendCmd cmd) {
        return pl3ICaiService.dkRecommend(cmd);
    }

    @PutMapping("/comRec/calc/{period}")
    public void comRecommendCalc(@PathVariable String period) {
        pl3ICaiService.comRecommendCalc(period);
    }

    @PutMapping("/danKill")
    public DanKillCalcResult calculateDanKill(@NotBlank(message = "计算期号为空") String period,
                                              @NotNull(message = "图标类型为空") ChartType type) {
        return pl3ICaiService.calculateDanKill(period, type);
    }

    @GetMapping("/item/census")
    public N3ItemCensusDo calcItemCensus(@NotBlank(message = "预测数据期号为空") String period) {
        return pl3ICaiService.calcItemCensus(period);
    }

    @GetMapping("/differ/analyze")
    public N3DifferAnalyzeVo differAnalyze(String period) {
        return pl3ICaiService.differAnalyze(period);
    }

    @PostMapping("/pivot")
    public void addPl3Pivot(@Validated @RequestBody N3TodayPivotCmd command) {
        pl3ICaiService.addPl3Pivot(command);
    }

    @PutMapping("/pivot")
    public void calcTodayPivot(@NotBlank(message = "计算期号为空") String period) {
        pl3ICaiService.calcPivotHit(period);
    }

    @PutMapping("/mark")
    public void forecastMark(@NotBlank(message = "预测期号为空") String period) {
        pl3ICaiService.calcForecastMark(period);
    }

}
