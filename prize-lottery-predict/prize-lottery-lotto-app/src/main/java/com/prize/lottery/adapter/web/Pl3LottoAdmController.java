package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import com.prize.lottery.application.command.service.IPlsLottoCommandService;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.service.IPl3LottoQueryService;
import com.prize.lottery.application.vo.Com7CombineResult;
import com.prize.lottery.application.vo.N3Comb5StatsVo;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.pl3.Pl3IcaiDataVo;
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
@RequestMapping(value = "/adm/pls")
@Permission(domain = LotteryAuth.MANAGER)
public class Pl3LottoAdmController {

    private final IPl3LottoQueryService   pl3LottoQueryService;
    private final IPlsLottoCommandService plsLottoCommandService;

    @GetMapping("/period")
    public Period latestPeriod() {
        return pl3LottoQueryService.latestPeriod();
    }

    @GetMapping("/periods")
    public List<String> lastPeriods(@RequestParam(defaultValue = "50") Integer size) {
        return pl3LottoQueryService.lastPeriods(size);
    }

    @GetMapping("/rank/period")
    public String rankLatestPeriod() {
        return pl3LottoQueryService.latestRank();
    }

    @GetMapping("/masters")
    public Page<LotteryMasterVo> lotteryMasters(@Validated Pl3LottoMasterQuery query) {
        return pl3LottoQueryService.getPl3LotteryMasters(query);
    }

    @GetMapping("/item/list")
    public List<Pl3IcaiDataVo> itemRankList(@NotNull(message = "字段渠道为空") Pl3Channel channel, String period) {
        return pl3LottoQueryService.getItemRankedDatas(channel, period);
    }

    @PostMapping("/dan/best")
    public List<Pl3IcaiDataVo> bestDanForecasts(@RequestBody @Validated N3BestQuery query) {
        return pl3LottoQueryService.getBestDanForecasts(query);
    }

    @PostMapping("/dan3/filter")
    public List<ForecastValue> dan3Filter(@Validated @RequestBody Dan3FilterQuery query) {
        return pl3LottoQueryService.getDan3FilterList(query);
    }

    @GetMapping("/dan/filter")
    public List<Pl3IcaiDataVo> danFilter(@Validated Num3DanFilterQuery query) {
        return pl3LottoQueryService.getDanFilterForecasts(query);
    }

    @GetMapping("/mul/list")
    public List<Pl3IcaiDataVo> mulRankList(String period) {
        return pl3LottoQueryService.getMulRankedDatas(period);
    }

    @GetMapping("/list")
    public Page<ICaiRankedDataVo> rankDataList(@Validated PlsAdmRankQuery query) {
        return pl3LottoQueryService.getPlsRankedDataList(query);
    }

    @GetMapping("/chart/full/{type}")
    public SyntheticFullCensusVo fullChart(@PathVariable Pl3Channel type, String period) {
        return pl3LottoQueryService.fullChart(type, period);
    }

    @GetMapping("/chart/item/{type}")
    public SyntheticItemCensusVo itemChart(@PathVariable Pl3Channel type, String period) {
        return pl3LottoQueryService.itemChart(type, period);
    }

    @GetMapping("/chart/vip/{type}")
    public SyntheticVipCensusVo vipChart(@PathVariable Pl3Channel type, String period) {
        return pl3LottoQueryService.vipChart(type, period);
    }

    @GetMapping("/chart/rate")
    public NumberThreeCensusVo rateChart(String period) {
        return pl3LottoQueryService.rateChart(period);
    }

    @GetMapping("/chart/hot")
    public NumberThreeCensusVo hotChart(String period) {
        return pl3LottoQueryService.hotChart(period);
    }

    @GetMapping("/com/recommend/list")
    public Page<Pl3ComRecommendPo> comRecommends(@Validated ComRecommendQuery query) {
        return pl3LottoQueryService.getComRecommendList(query);
    }

    @GetMapping("/com/recommend")
    public Pl3ComRecommendPo comRecommend(@NotBlank(message = "推荐期号为空") String period) {
        return pl3LottoQueryService.getComRecommend(period);
    }

    @PostMapping("/com7/combine")
    public Com7CombineResult com7Combine(@Validated @RequestBody ComCombineCalcCmd cmd) {
        return plsLottoCommandService.com7Combine(cmd);
    }

    @GetMapping("/item/census")
    public N3ItemCensusVo itemCensus(String period) {
        return pl3LottoQueryService.itemCensus(period);
    }

    @GetMapping("/item/table")
    public N3ItemBestTableVo itemTable(String period, @Enumerable(
            ranges = {"10", "15", "20", "30", "40", "50", "60", "80", "100"},
            message = "参数仅允许10,15,20,30,40,50,60,80,100") Integer limit) {
        return pl3LottoQueryService.itemTable(period, limit);
    }

    @GetMapping("/pivot/list")
    public Page<Pl3PivotPo> pivotList(@Validated PivotListQuery query) {
        return pl3LottoQueryService.getPivotList(query);
    }

    @GetMapping("/pivot")
    public Pl3PivotPo pivotInfo(@NotNull(message = "唯一标识为空") Long id) {
        return pl3LottoQueryService.getPivotInfo(id);
    }

    @PostMapping("/d3/filter")
    public List<N3Dan3MetricVo> dan3Filter(@Validated @RequestBody N3DanFilterQuery query) {
        return pl3LottoQueryService.dan3Filter(query);
    }

    @GetMapping("/whole/best")
    public List<Num3MasterCountVo> wholeBestForecasts(@Validated N3WholeBestQuery query) {
        return pl3LottoQueryService.getWholeBestForecasts(query);
    }

    @PostMapping("/comb5/stats")
    public N3Comb5StatsVo comb5Stats(@RequestBody @Validated N3DanFilterQuery query) {
        return plsLottoCommandService.comb5Stats(query);
    }

}
