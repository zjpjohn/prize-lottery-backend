package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.Kl8LottoMasterQuery;
import com.prize.lottery.application.query.dto.Kl8RankQuery;
import com.prize.lottery.application.query.service.IKl8LottoQueryService;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.po.kl8.Kl8MasterInfoPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;
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
@RequestMapping(value = "/adm/kl8")
@Permission(domain = LotteryAuth.MANAGER)
public class Kl8LottoAdmController {

    private final IKl8LottoQueryService kl8LottoQueryService;

    @GetMapping("/period")
    public Period latestPeriod() {
        return kl8LottoQueryService.latestPeriod();
    }

    @GetMapping("/periods")
    public List<String> lastPeriods(@RequestParam(defaultValue = "50") Integer size) {
        return kl8LottoQueryService.lastPeriods(size);
    }

    @GetMapping("/rank/period")
    public String rankPeriod() {
        return kl8LottoQueryService.latestRank();
    }

    @GetMapping("/list")
    public Page<ICaiRankedDataVo> rankDataList(@Validated Kl8RankQuery query) {
        return kl8LottoQueryService.getKl8RankedDataList(query);
    }

    @GetMapping("/masters")
    public Page<LotteryMasterVo> kl8Masters(@Validated Kl8LottoMasterQuery query) {
        return kl8LottoQueryService.getKl8LotteryMasters(query);
    }

    @GetMapping("/master/list")
    public Page<Kl8MasterInfoPo> masterList(@Validated PageQuery query) {
        return kl8LottoQueryService.getKl8MasterList(query);
    }

    @GetMapping("/chart/full/{type}")
    public Kl8FullCensusVo fullChart(@PathVariable Kl8Channel type, String period) {
        return kl8LottoQueryService.fullChart(type, period);
    }

}
