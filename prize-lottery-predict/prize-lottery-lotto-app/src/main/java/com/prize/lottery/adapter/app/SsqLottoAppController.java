package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.ISsqLottoCommandService;
import com.prize.lottery.application.query.dto.MasterBattleRankQuery;
import com.prize.lottery.application.query.dto.MulRankQuery;
import com.prize.lottery.application.query.dto.SsqRankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.service.ISsqLottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.SsqForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.ssq.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app/ssq")
public class SsqLottoAppController {

    private final ISsqLottoQueryService   ssqLottoQueryService;
    private final ISsqLottoCommandService ssqLottoCommandService;

    @GetMapping("/glad")
    public Page<SsqICaiGladVo> lottoGlads(@Validated PageQuery query) {
        return ssqLottoQueryService.getSsqGladList(query);
    }

    @GetMapping("/random/masters")
    public List<SsqMasterMulRankVo> randomMasters() {
        return ssqLottoQueryService.getSsqRandomMasters(6, 60);
    }

    @GetMapping("/rank")
    public Page<SsqMasterRankVo> rankMasters(@Validated SsqRankQuery query) {
        return ssqLottoQueryService.getSsqRankedMasters(query);
    }

    @GetMapping("/mul/rank")
    public Page<SsqMasterMulRankVo> mulRankList(@Validated MulRankQuery query) {
        return ssqLottoQueryService.getSsqMasterMulRankList(query);
    }

    @GetMapping("/history/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public List<SsqIcaiHistoryVo> masterHistories(@PathVariable String masterId) {
        return ssqLottoQueryService.geSsqMasterHistories(masterId);
    }

    @GetMapping("/master/homed")
    public Map<String, List<HomeMasterVo>> ssqHomedMasters() {
        return ssqLottoQueryService.getSsqHomedMasters();
    }

    @GetMapping("/master/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public SsqMasterDetail masterDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoQueryService.getSsqMasterDetail(masterId, userId);
    }

    @GetMapping("/master/follow")
    @Permission(domain = LotteryAuth.USER)
    public Page<SsqMasterSubscribeVo> subscribeMasters(@Validated SubscribeQuery query) {
        return ssqLottoQueryService.getSsqSubscribeMasters(query);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/forecast/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public SsqForecastVo forecastDetail(@PathVariable String masterId, @NotNull(message = "用户表示为空") Long userId) {
        return ssqLottoCommandService.lookupForecast(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticItemCensusVo itemChart(@PathVariable SsqChannel type,
                                           @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getItemCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticItemCensusVo> itemChartV1(@PathVariable SsqChannel type,
                                                            @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getItemCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticFullCensusVo fullChart(@PathVariable SsqChannel type,
                                           @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getFullCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticFullCensusVo> fullChartV1(@PathVariable SsqChannel type,
                                                            @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getFullCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public SsqChartCensusVo levelFullChart(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "级别数据错误") @PathVariable
    Integer level) {
        return ssqLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SsqChartCensusVo> levelFullChartV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {
                    "100", "200", "400", "600", "800", "1000"
            }, message = "级别数据错误") @PathVariable Integer level) {
        return ssqLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticVipCensusVo vipChart(@PathVariable SsqChannel type,
                                         @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getVipCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticVipCensusVo> vipChartV1(@PathVariable SsqChannel type,
                                                          @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getVipCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public SsqChartCensusVo levelVipChart(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "分级数据错误") @PathVariable Integer level) {
        return ssqLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SsqChartCensusVo> levelVipChartV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "分级数据错误") @PathVariable Integer level) {
        return ssqLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public SsqChartCensusVo rateChart(@NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getHotOrRateCensusDetail(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SsqChartCensusVo> rateChartV1(@NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getHotOrRateCensusDetailV1(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public SsqChartCensusVo hotChart(@NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getHotOrRateCensusDetail(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SsqChartCensusVo> hotChartV1(@NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getHotOrRateCensusDetailV1(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/compare/{type}")
    @Permission(domain = LotteryAuth.USER)
    public List<ICaiRankedDataVo> batchCompare(@PathVariable SsqChannel type,
                                               @NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoCommandService.getBatchCompareForecast(userId, type.getChannel(), 15);
    }

    @GetMapping("/schema/masters")
    public List<SsqMasterSchemaVo> schemaMasters() {
        return ssqLottoQueryService.getSsqSchemaMasters(3);
    }

    @GetMapping("/renew/masters")
    public List<SchemaRenewMasterVo> renewMaster() {
        return ssqLottoQueryService.schemaRenewMasters();
    }

    @ApiBody(encrypt = true)
    @PostMapping("/battle")
    @Permission(domain = LotteryAuth.USER)
    public MasterBattleVo addBattle(@NotNull(message = "用户标识为空") Long userId,
                                    @NotBlank(message = "专家标识为空") String masterId) {
        return ssqLottoCommandService.addMasterBattle(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/battles")
    @Permission(domain = LotteryAuth.USER)
    public List<MasterBattleVo> latestBattles(@NotNull(message = "用户标识为空") Long userId) {
        return ssqLottoQueryService.getSsqMasterBattles(userId);
    }

    @GetMapping("/battle/ranks")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterBattleRankVo<SsqMasterMulRankVo>> battleMasterRanks(@Validated MasterBattleRankQuery query) {
        return ssqLottoQueryService.getSsqBattleMastersRanks(query);
    }

    @GetMapping("/master/rate")
    @Permission(domain = LotteryAuth.USER)
    public SsqMasterRateVo masterRate(@NotBlank(message = "专家标识为空") String masterId) {
        return ssqLottoQueryService.getSsqMasterRate(masterId);
    }

    @GetMapping("/hits")
    @Permission(domain = LotteryAuth.USER)
    public List<SsqICaiHitVo> masterHits(@NotBlank(message = "专家标识为空") String masterId) {
        return ssqLottoQueryService.getSsqMasterBeforeHits(masterId);
    }

}
