package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.IDltLottoCommandService;
import com.prize.lottery.application.query.dto.DltRankQuery;
import com.prize.lottery.application.query.dto.MasterBattleRankQuery;
import com.prize.lottery.application.query.dto.MulRankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.service.IDltLottoQueryService;
import com.prize.lottery.application.vo.DltForecastVo;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.dlt.*;
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
@RequestMapping(value = "/app/dlt")
@RequiredArgsConstructor
public class DltLottoAppController {

    private final IDltLottoQueryService   dltLottoQueryService;
    private final IDltLottoCommandService dltLottoCommandService;

    @GetMapping("/glad")
    public Page<DltICaiGladVo> lottoGlads(@Validated PageQuery query) {
        return dltLottoQueryService.getDltGladList(query);
    }

    @GetMapping("/random/masters")
    public List<DltMasterMulRankVo> randomMasters() {
        return dltLottoQueryService.getDltRandomMasters(6, 60);
    }

    @GetMapping("/rank")
    public Page<DltMasterRankVo> rankList(@Validated DltRankQuery query) {
        return dltLottoQueryService.getDltRankedMasters(query);
    }

    @GetMapping("/mul/rank")
    public Page<DltMasterMulRankVo> mulRankList(@Validated MulRankQuery query) {
        return dltLottoQueryService.getDltMasterMulRankList(query);
    }

    @GetMapping("/history/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public List<DltIcaiHistoryVo> masterHistories(@PathVariable String masterId) {
        return dltLottoQueryService.getDltMasterHistories(masterId);
    }

    @GetMapping("/master/homed")
    public Map<String, List<HomeMasterVo>> fsdHomedMasters() {
        return dltLottoQueryService.getDltHomedMasters();
    }

    @GetMapping("/master/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public DltMasterDetail masterDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoQueryService.getDltMasterDetail(masterId, userId);
    }

    @GetMapping("/master/follow")
    @Permission(domain = LotteryAuth.USER)
    public Page<DltMasterSubscribeVo> subscribeMasters(@Validated SubscribeQuery query) {
        return dltLottoQueryService.getDltSubscribeMasters(query);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/forecast/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public DltForecastVo forecastDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.lookupForecast(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticItemCensusVo itemCensus(@PathVariable DltChannel type,
                                            @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getItemCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticItemCensusVo> itemCensusV1(@PathVariable DltChannel type,
                                                             @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getItemCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticFullCensusVo fullCensus(@PathVariable DltChannel type,
                                            @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getFullCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticFullCensusVo> fullCensusV1(@PathVariable DltChannel type,
                                                             @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getFullCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public DltChartCensusVo levelFullCensus(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "") @PathVariable Integer level) {
        return dltLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<DltChartCensusVo> levelFullCensusV1(@NotNull(message = "用户标识为空") Long userId,
                                                             @Enumerable(
                                                                     ranges = {
                                                                             "100", "200", "400", "600", "800", "1000"
                                                                     }, message = "级别错误") @PathVariable
                                                             Integer level) {
        return dltLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticVipCensusVo vipCensus(@PathVariable DltChannel type,
                                          @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getVipCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticVipCensusVo> vipCensusV1(@PathVariable DltChannel type,
                                                           @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getVipCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public DltChartCensusVo levelVipCensus(@NotNull(message = "用户标识为空") Long userId,
                                           @Enumerable(ranges = {"10", "20", "50", "100", "150"}, message = "")
                                           @PathVariable Integer level) {
        return dltLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<DltChartCensusVo> levelVipCensusV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "") @PathVariable Integer level) {
        return dltLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public DltChartCensusVo rateCensus(@NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getHotOrRateCensusDetail(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<DltChartCensusVo> rateCensusV1(@NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getHotOrRateCensusDetailV1(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public DltChartCensusVo hotCensus(@NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getHotOrRateCensusDetail(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<DltChartCensusVo> hotCensusV1(@NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getHotOrRateCensusDetailV1(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/compare/{type}")
    @Permission(domain = LotteryAuth.USER)
    public List<ICaiRankedDataVo> batchCompare(@PathVariable DltChannel type,
                                               @NotNull(message = "用户标识为空") Long userId) {
        return dltLottoCommandService.getBatchCompareForecast(userId, type.getChannel(), 15);
    }

    @GetMapping("/schema/masters")
    public List<DltMasterSchemaVo> schemaMasters() {
        return dltLottoQueryService.getDltSchemaMasters(3);
    }

    @GetMapping("/renew/masters")
    public List<SchemaRenewMasterVo> renewMasters() {
        return dltLottoQueryService.schemaRenewMasters();
    }

    @ApiBody(encrypt = true)
    @PostMapping("/battle")
    @Permission(domain = LotteryAuth.USER)
    public MasterBattleVo addBattle(@NotNull(message = "用户标识为空") Long userId,
                                    @NotBlank(message = "专家标识为空") String masterId) {
        return dltLottoCommandService.addMasterBattle(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/battles")
    @Permission(domain = LotteryAuth.USER)
    public List<MasterBattleVo> latestBattles(@NotNull(message = "用户标识为空") Long userId) {
        return dltLottoQueryService.getDltMasterBattles(userId);
    }

    @GetMapping("/battle/ranks")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterBattleRankVo<DltMasterMulRankVo>> battleMasterRanks(@Validated MasterBattleRankQuery query) {
        return dltLottoQueryService.getDltBattleMasterRanks(query);
    }

    @GetMapping("/master/rate")
    @Permission(domain = LotteryAuth.USER)
    public DltMasterRateVo masterRate(@NotBlank(message = "专家标识为空") String masterId) {
        return dltLottoQueryService.getDltMasterRate(masterId);
    }

    @GetMapping("/hits")
    @Permission(domain = LotteryAuth.USER)
    public List<DltICaiHitVo> masterHits(@NotBlank(message = "专家标识为空") String masterId) {
        return dltLottoQueryService.getDltMasterBeforeHits(masterId);
    }

}
