package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.Fc3dComSelectCmd;
import com.prize.lottery.application.cmd.N3TodayPivotCmd;
import com.prize.lottery.application.service.IFc3dICaiService;
import com.prize.lottery.application.service.ILotteryIndexService;
import com.prize.lottery.application.vo.*;
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
@RequestMapping("/fc3d")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class Fc3dICaiController {

    private final IFc3dICaiService     fc3dICaiService;
    private final ICaiDomainAbility    fc3dICaiDomainAbility;
    private final ILotteryIndexService lotteryIndexService;

    @PostMapping("/full/index")
    public List<BallIndex> fullIndex(@NotBlank(message = "期号为空") String period) {
        return lotteryIndexService.fc3dFullIndex(period);
    }

    @PostMapping("/vip/index")
    public List<BallIndex> vipIndex(@NotBlank(message = "期号为空") String period) {
        return lotteryIndexService.fc3dVipIndex(period);
    }

    @PostMapping("/index")
    public void calcIndex(@NotBlank(message = "期号为空") String period) {
        lotteryIndexService.fc3dLottoIndex(period);
    }

    @PostMapping("/fetch/history")
    public void fetchHistory() {
        fc3dICaiService.fetchHistoryForecast();
    }

    @PostMapping("/fetch/incr/history")
    public void fetchIncrHistory() {
        fc3dICaiService.fetchIncrHistory();
    }

    @PostMapping("/fetch/last")
    public void fetchLast(@NotBlank(message = "期号为空") String before,
                          @Min(value = 1, message = "最小抓取期数大于0") @RequestParam(defaultValue = "30")
                          Integer size) {
        fc3dICaiService.fetchLastForecast(before, size);
    }

    @PostMapping("/fetch/{period}")
    public void fetchForecast(@PathVariable String period) {
        fc3dICaiService.fetchForecast(period);
    }

    @PostMapping("/hit/batch")
    public void batchCalcHit() {
        fc3dICaiService.batchCalcFc3dHit();
    }

    @PostMapping("/hit/incr")
    public void incrCalcHit() {
        fc3dICaiService.calcIncrAllHit();
    }

    @PostMapping("/hit")
    public void calcHit(String period) {
        fc3dICaiDomainAbility.calcForecastHit(period);
    }

    @PostMapping("/rate/batch")
    public void batchCalcRate() {
        fc3dICaiService.initCalcMasterRate();
    }

    @PostMapping("/rate/incr")
    public void incrCalcRate() {
        fc3dICaiService.incrCalcMasterRate();
    }

    @PostMapping("/rate")
    public void calcRate(String period) {
        fc3dICaiDomainAbility.calcMasterRate(period);
    }

    @PostMapping("/rank/batch")
    public void batchCalcRank() {
        fc3dICaiService.initCalcMasterRank();
    }

    @PostMapping("/rank")
    public void calcRank(String period) {
        fc3dICaiDomainAbility.calcMasterRank(period);
    }

    @PostMapping("/vip")
    public void extractVipMaster(String period) {
        fc3dICaiDomainAbility.calcVipMaster(period);
    }

    @PostMapping("/home")
    public void extractHomeMaster(String period) {
        fc3dICaiDomainAbility.calcHomeMaster(period);
    }

    @PostMapping("/chart/item")
    public void calcItemChart(String period) {
        fc3dICaiDomainAbility.calcItemCensusChart(period);
    }

    @PostMapping("/chart/all")
    public void calcAllChart(String period) {
        fc3dICaiDomainAbility.calcAllCensusChart(period);
    }

    @PostMapping("/chart/vip")
    public void calcVipChart(String period) {
        fc3dICaiDomainAbility.calcVipMasterChart(period);
    }

    @PostMapping("/chart/rate")
    public void calcRateChart(String period) {
        fc3dICaiDomainAbility.calcRateMasterChart(period);
    }

    @PostMapping("/chart/hot")
    public void calcHotChart(String period) {
        fc3dICaiDomainAbility.calcHotMasterChart(period);
    }

    @GetMapping("/differ/analyze")
    public N3DifferAnalyzeVo differAnalyze(String period) {
        return fc3dICaiService.differAnalyze(period);
    }

    @GetMapping("/combine/analyze")
    public Fc3dCombineAnalyzeVo combineAnalyze(String period) {
        return fc3dICaiService.combineAnalyze(period);
    }

    @GetMapping("/reverse/analyze")
    public Fc3dReverseAnalyzeVo reverseAnalyze(String period) {
        return fc3dICaiService.reverseAnalyze(period);
    }

    @PostMapping("/comRec/analyze")
    public Fc3dComRecommendVo comPickAnalyze(@Validated @RequestBody Fc3dComSelectCmd cmd) {
        return fc3dICaiService.comRecommendAnalyze(cmd);
    }

    @PutMapping("/danKill")
    public DanKillCalcResult danKill(@NotBlank(message = "计算期号为空") String period,
                                     @NotNull(message = "统计类型为空") ChartType type) {
        return fc3dICaiService.danKillCalc(period, type);
    }

    @PostMapping("/dk/recommend")
    public Fc3dDkRecommendVo dkRecommend(@Validated @RequestBody DkRecommendCmd cmd) {
        return fc3dICaiService.dkRecommend(cmd);
    }

    @PutMapping("/comRec/calc/{period}")
    public void calcComRecommend(@PathVariable String period) {
        fc3dICaiService.calcComRecommend(period);
    }

    @GetMapping("/item/census")
    public N3ItemCensusDo calcItemCensus(@NotBlank(message = "预测数据期号为空") String period) {
        return fc3dICaiService.calcItemCensus(period);
    }

    @PostMapping("/pivot")
    public void addFc3dPivot(@Validated @RequestBody N3TodayPivotCmd command) {
        fc3dICaiService.addFc3dPivot(command);
    }

    @PutMapping("/pivot")
    public void calcTodayPivot(@NotBlank(message = "计算期号为空") String period) {
        fc3dICaiService.calcPivotHit(period);
    }

    @PutMapping("/mark")
    public void forecastMark(@NotBlank(message = "预测期号为空") String period) {
        fc3dICaiService.calcForecastMark(period);
    }


}
