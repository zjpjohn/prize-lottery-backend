package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.IKl8LottoCommandService;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.service.IKl8LottoQueryService;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;
import com.prize.lottery.vo.kl8.Kl8MasterDetail;
import com.prize.lottery.vo.kl8.Kl8MasterRankVo;
import com.prize.lottery.vo.kl8.Kl8MasterSubscribeVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@ApiBody
@RestController
@RequestMapping(value = "/app/kl8")
@RequiredArgsConstructor
public class Kl8LottoAppController {

    private final IKl8LottoQueryService   kl8LottoQueryService;
    private final IKl8LottoCommandService kl8LottoCommandService;

    @GetMapping("/rank")
    public Page<Kl8MasterRankVo> rankList(@Validated PageQuery query) {
        return kl8LottoQueryService.getKl8MasterRankList(query);
    }

    @GetMapping("/history/{masterId}")
    public List<Kl8IcaiInfoPo> masterHistories(@PathVariable String masterId) {
        return kl8LottoQueryService.getMasterForecastHistories(masterId);
    }

    @GetMapping("/master/follow")
    @Permission(domain = LotteryAuth.USER)
    public Page<Kl8MasterSubscribeVo> subscribeMasters(@Validated SubscribeQuery query) {
        return kl8LottoQueryService.getKl8MasterSubscribeList(query);
    }

    @GetMapping("/master/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public Kl8MasterDetail masterDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return kl8LottoQueryService.queryMasterDetail(masterId, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/forecast/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public Kl8IcaiInfoPo forecastDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return kl8LottoCommandService.lookupForecast(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public Kl8FullCensusVo fullCensus(@PathVariable Kl8Channel type, @NotNull(message = "用户标识为空") Long userId) {
        return kl8LottoCommandService.getFullChartDetail(userId, type);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/compare/{type}")
    @Permission(domain = LotteryAuth.USER)
    public List<ICaiRankedDataVo> batchCompare(@PathVariable Kl8Channel type,
                                               @NotNull(message = "用户标识为空") Long userId) {
        return kl8LottoCommandService.getBatchCompareForecast(userId, type, 15);
    }

}
