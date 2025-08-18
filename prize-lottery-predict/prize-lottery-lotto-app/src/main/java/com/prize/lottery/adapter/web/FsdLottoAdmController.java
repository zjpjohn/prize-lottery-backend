package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import com.prize.lottery.application.command.service.IFsdLottoCommandService;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.service.IFsdLottoQueryService;
import com.prize.lottery.application.vo.Com7CombineResult;
import com.prize.lottery.application.vo.N3Comb5StatsVo;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.fc3d.Fc3dIcaiDataVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/adm/fsd")
@Permission(domain = LotteryAuth.MANAGER)
public class FsdLottoAdmController {

    private final IFsdLottoQueryService   fsdLottoQueryService;
    private final IFsdLottoCommandService fsdLottoCommandService;

    @GetMapping("/period")
    public Period latestPeriod() {
        return fsdLottoQueryService.latestPeriod();
    }

    @GetMapping("/periods")
    public List<String> lastPeriods(@RequestParam(defaultValue = "50") Integer size) {
        return fsdLottoQueryService.lastPeriods(size);
    }

    @GetMapping("/rank/period")
    public String latestRankPeriod() {
        return fsdLottoQueryService.latestRank();
    }

    @GetMapping("/masters")
    public Page<LotteryMasterVo> fc3dMasters(@Validated FsdLottoMasterQuery query) {
        return fsdLottoQueryService.getFc3dLotteryMasters(query);
    }

    @GetMapping("/item/list")
    public List<Fc3dIcaiDataVo> itemRankList(@NotNull(message = "字段渠道为空") Fc3dChannel channel, String period) {
        return fsdLottoQueryService.getItemRankedDatas(channel, period);
    }

    @GetMapping("/dan/filter")
    public List<Fc3dIcaiDataVo> danFilter(@Validated Num3DanFilterQuery query) {
        return fsdLottoQueryService.getDanFilterForecasts(query);
    }

    @PostMapping("/dan3/filter")
    public List<ForecastValue> dan3Filter(@Validated @RequestBody Dan3FilterQuery query) {
        return fsdLottoQueryService.getDan3FilterList(query);
    }

    @PostMapping("/dan/best")
    public List<Fc3dIcaiDataVo> bestDanForecasts(@RequestBody @Validated N3BestQuery query) {
        return fsdLottoQueryService.getBestDanForecasts(query);
    }

    @GetMapping("/mul/list")
    public List<Fc3dIcaiDataVo> mulRankList(String period) {
        return fsdLottoQueryService.getMulRankedDatas(period);
    }

    @GetMapping("/list")
    public Page<ICaiRankedDataVo> rankDataList(@Validated FsdAdmRankQuery query) {
        return fsdLottoQueryService.getFsdRankedDataList(query);
    }

    @GetMapping("/chart/full/{type}")
    public SyntheticFullCensusVo fullChart(@PathVariable Fc3dChannel type, String period) {
        return fsdLottoQueryService.fullChart(type, period);
    }

    @GetMapping("/chart/item/{type}")
    public SyntheticItemCensusVo itemChart(@PathVariable Fc3dChannel type, String period) {
        return fsdLottoQueryService.itemChart(type, period);
    }

    @GetMapping("/chart/vip/{type}")
    public SyntheticVipCensusVo vipChart(@PathVariable Fc3dChannel type, String period) {
        return fsdLottoQueryService.vipChart(type, period);
    }

    @GetMapping("/chart/rate")
    public NumberThreeCensusVo rateChart(String period) {
        return fsdLottoQueryService.rateChart(period);
    }

    @GetMapping("/chart/hot")
    public NumberThreeCensusVo hotChart(String period) {
        return fsdLottoQueryService.hotChart(period);
    }

    @GetMapping("/com/recommend/list")
    public Page<Fc3dComRecommendPo> comRecommends(@Validated ComRecommendQuery query) {
        return fsdLottoQueryService.getFc3dComRecommends(query);
    }

    @GetMapping("/com/recommend")
    public Fc3dComRecommendPo comRecommend(@NotBlank(message = "推荐期号为空") String period) {
        return fsdLottoQueryService.getComRecommend(period);
    }

    @PostMapping("/com7/combine")
    public Com7CombineResult com7Combine(@Validated @RequestBody ComCombineCalcCmd cmd) {
        return fsdLottoCommandService.com7Combine(cmd);
    }

    @GetMapping("/item/census")
    public N3ItemCensusVo itemCensus(String period) {
        return fsdLottoQueryService.itemCensus(period);
    }

    @GetMapping("/item/table")
    public N3ItemBestTableVo itemTable(String period, @RequestParam(defaultValue = "15") @Enumerable(
            ranges = {"10", "15", "20", "30", "40", "50", "60", "80", "100"},
            message = "参数仅允许10,15,20,30,40,50,60,80,100") Integer limit) {
        return fsdLottoQueryService.itemTable(period, limit);
    }

    @GetMapping("/pivot/list")
    public Page<Fc3dPivotPo> pivotList(@Validated PivotListQuery query) {
        return fsdLottoQueryService.getPivotList(query);
    }

    @GetMapping("/pivot")
    public Fc3dPivotPo pivotInfo(@NotNull(message = "唯一标识为空") Long id) {
        return fsdLottoQueryService.getPivotInfo(id);
    }

    @PostMapping("/d3/filter")
    public List<N3Dan3MetricVo> dan3Filter(@Validated @RequestBody N3DanFilterQuery query) {
        return fsdLottoQueryService.dan3Filter(query);
    }

    @GetMapping("/whole/best")
    public List<Num3MasterCountVo> wholeBestForecasts(@Validated N3WholeBestQuery query) {
        return fsdLottoQueryService.getWholeBestForecasts(query);
    }

    @PostMapping("/comb5/stats")
    public N3Comb5StatsVo comb5Stats(@RequestBody @Validated N3DanFilterQuery query) {
        return fsdLottoCommandService.statsComb5(query);
    }

}
