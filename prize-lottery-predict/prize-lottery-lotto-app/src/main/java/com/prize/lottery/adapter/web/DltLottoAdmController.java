package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.DltAdmRankQuery;
import com.prize.lottery.application.query.dto.DltLottoMasterQuery;
import com.prize.lottery.application.query.service.IDltLottoQueryService;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.dlt.DltChartCensusVo;
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
@RequestMapping(value = "/adm/dlt")
@Permission(domain = LotteryAuth.MANAGER)
public class DltLottoAdmController {

    private final IDltLottoQueryService dltLottoQueryService;

    @GetMapping("/period")
    public Period latestPeriod() {
        return dltLottoQueryService.latestPeriod();
    }

    @GetMapping("/periods")
    public List<String> latestPeriods(@RequestParam(defaultValue = "50") Integer size) {
        return dltLottoQueryService.lastPeriods(size);
    }

    @GetMapping("/rank/period")
    public String latestRank() {
        return dltLottoQueryService.latestRank();
    }

    @GetMapping("/masters")
    public Page<LotteryMasterVo> lotteryMasters(@Validated DltLottoMasterQuery query) {
        return dltLottoQueryService.getDltLotteryMasters(query);
    }

    @GetMapping("/list")
    public Page<ICaiRankedDataVo> rankDataList(@Validated DltAdmRankQuery query) {
        return dltLottoQueryService.getDltRankedDataList(query);
    }

    @GetMapping("/chart/full/{type}")
    public SyntheticFullCensusVo fullChart(@PathVariable DltChannel type, String period) {
        return dltLottoQueryService.fullChart(type, period);
    }

    @GetMapping("/chart/item/{type}")
    public SyntheticItemCensusVo itemChart(@PathVariable DltChannel type, String period) {
        return dltLottoQueryService.itemChart(type, period);
    }

    @GetMapping("/chart/vip/{type}")
    public SyntheticVipCensusVo vipChart(@PathVariable DltChannel type, String period) {
        return dltLottoQueryService.vipChart(type, period);
    }

    @GetMapping("/chart/rate")
    public DltChartCensusVo rateChart(String period) {
        return dltLottoQueryService.rateChart(period);
    }

    @GetMapping("/chart/hot")
    public DltChartCensusVo hotChart(String period) {
        return dltLottoQueryService.hotChart(period);
    }

}
