package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.QlcAdmRankQuery;
import com.prize.lottery.application.query.dto.QlcLottoMasterQuery;
import com.prize.lottery.application.query.dto.QlcWholeBestQuery;
import com.prize.lottery.application.query.service.IQlcLottoQueryService;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.qlc.QlcChartCensusVo;
import com.prize.lottery.vo.qlc.QlcMasterCountVo;
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
@RequestMapping(value = "/adm/qlc")
@Permission(domain = LotteryAuth.MANAGER)
public class QlcLottoAdmController {

    private final IQlcLottoQueryService qlcLottoQueryService;

    @GetMapping("/period")
    public Period latestPeriod() {
        return qlcLottoQueryService.latestPeriod();
    }

    @GetMapping("/periods")
    public List<String> lastPeriods(@RequestParam(defaultValue = "50") Integer size) {
        return qlcLottoQueryService.lastPeriods(size);
    }

    @GetMapping("/rank/period")
    public String latestRank() {
        return qlcLottoQueryService.latestRank();
    }

    @GetMapping("/masters")
    public Page<LotteryMasterVo> lotteryMasters(@Validated QlcLottoMasterQuery query) {
        return qlcLottoQueryService.getQlcLotteryMasters(query);
    }

    @GetMapping("/list")
    public Page<ICaiRankedDataVo> rankedDataList(@Validated QlcAdmRankQuery query) {
        return qlcLottoQueryService.getQlcRankedDataList(query);
    }

    @GetMapping("/chart/full/{type}")
    public SyntheticFullCensusVo fullChart(@PathVariable QlcChannel type, String period) {
        return qlcLottoQueryService.fullChart(type, period);
    }

    @GetMapping("/chart/item/{type}")
    public SyntheticItemCensusVo itemChart(@PathVariable QlcChannel type, String period) {
        return qlcLottoQueryService.itemChart(type, period);
    }

    @GetMapping("/chart/vip/{type}")
    public SyntheticVipCensusVo vipChart(@PathVariable QlcChannel type, String period) {
        return qlcLottoQueryService.vipChart(type, period);
    }

    @GetMapping("/chart/rate")
    public QlcChartCensusVo rateChart(String period) {
        return qlcLottoQueryService.rateChart(period);
    }

    @GetMapping("/chart/hot")
    public QlcChartCensusVo hotChart(String period) {
        return qlcLottoQueryService.hotChart(period);
    }

    @GetMapping("/whole/best")
    public List<QlcMasterCountVo> wholeBestForecasts(@Validated QlcWholeBestQuery query) {
        return qlcLottoQueryService.getWholeBestForecasts(query);
    }

}
