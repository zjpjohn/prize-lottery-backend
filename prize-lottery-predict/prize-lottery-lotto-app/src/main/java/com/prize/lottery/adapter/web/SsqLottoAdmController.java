package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.SsqAdmRankQuery;
import com.prize.lottery.application.query.dto.SsqLottoMasterQuery;
import com.prize.lottery.application.query.service.impl.SsqLottoQueryService;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.ssq.SsqChartCensusVo;
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
@RequestMapping(value = "/adm/ssq")
@Permission(domain = LotteryAuth.MANAGER)
public class SsqLottoAdmController {

    private final SsqLottoQueryService ssqLottoQueryService;

    @GetMapping("/period")
    public Period latestPeriod() {
        return ssqLottoQueryService.latestPeriod();
    }

    @GetMapping("/periods")
    public List<String> lastPeriods(@RequestParam(defaultValue = "50") Integer size) {
        return ssqLottoQueryService.lastPeriods(size);
    }

    @GetMapping("/rank/period")
    public String latestRank() {
        return ssqLottoQueryService.latestRank();
    }

    @GetMapping("/masters")
    public Page<LotteryMasterVo> lotteryMasters(@Validated SsqLottoMasterQuery query) {
        return ssqLottoQueryService.getSsqLotteryMasters(query);
    }

    @GetMapping("/list")
    public Page<ICaiRankedDataVo> rankedDataList(@Validated SsqAdmRankQuery query) {
        return ssqLottoQueryService.getSsqRankedDataList(query);
    }

    @GetMapping("/chart/full/{type}")
    public SyntheticFullCensusVo fullChart(@PathVariable SsqChannel type, String period) {
        return ssqLottoQueryService.fullChart(type, period);
    }

    @GetMapping("/chart/item/{type}")
    public SyntheticItemCensusVo itemChart(@PathVariable SsqChannel type, String period) {
        return ssqLottoQueryService.itemChart(type, period);
    }

    @GetMapping("/chart/vip/{type}")
    public SyntheticVipCensusVo vipChart(@PathVariable SsqChannel type, String period) {
        return ssqLottoQueryService.vipChart(type, period);
    }

    @GetMapping("/chart/rate")
    public SsqChartCensusVo rateChart(String period) {
        return ssqLottoQueryService.rateChart(period);
    }

    @GetMapping("/chart/hot")
    public SsqChartCensusVo hotChart(String period) {
        return ssqLottoQueryService.hotChart(period);
    }

}
