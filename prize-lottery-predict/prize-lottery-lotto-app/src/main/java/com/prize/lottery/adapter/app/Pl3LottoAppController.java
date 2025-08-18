package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.IPlsLottoCommandService;
import com.prize.lottery.application.query.dto.MasterBattleRankQuery;
import com.prize.lottery.application.query.dto.PlsRankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.service.IPl3LottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.N3TodayPivotVo;
import com.prize.lottery.application.vo.N3WarnRecommendVo;
import com.prize.lottery.application.vo.Pl3ForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.pl3.*;
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
@RequestMapping(value = "/app/pls")
public class Pl3LottoAppController {

    private final IPl3LottoQueryService   pl3LottoQueryService;
    private final IPlsLottoCommandService plsLottoCommandService;

    @GetMapping("/glad")
    public Page<Pl3ICaiGladVo> plsGladList(@Validated PageQuery query) {
        return pl3LottoQueryService.getPlsGladList(query);
    }

    @GetMapping("/random/masters")
    public List<Pl3MasterMulRankVo> randomMasters() {
        return pl3LottoQueryService.getPl3RandomMasters(6, 120);
    }

    @GetMapping("/rank")
    public Page<Pl3MasterRankVo> plsRankList(@Validated PlsRankQuery query) {
        return pl3LottoQueryService.getPlsRankMasters(query);
    }

    @GetMapping("/mul/rank")
    public Page<Pl3MasterMulRankVo> mulRankList(@Validated PageQuery query) {
        return pl3LottoQueryService.getPlsMasterMulRankList(query);
    }

    @GetMapping("/history/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public List<Pl3IcaiHistoryVo> masterHistories(@PathVariable String masterId) {
        return pl3LottoQueryService.getPlsMasterHistories(masterId);
    }

    @GetMapping("/master/homed")
    public Map<String, List<HomeMasterVo>> plsHomedMasters() {
        return pl3LottoQueryService.getPlsHomedMasters();
    }

    @GetMapping("/master/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public Pl3MasterDetail masterDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return pl3LottoQueryService.getPlsMasterDetail(masterId, userId);
    }

    @GetMapping("/master/follow")
    @Permission(domain = LotteryAuth.USER)
    public Page<Pl3MasterSubscribeVo> subscribeMasters(@Validated SubscribeQuery query) {
        return pl3LottoQueryService.getPlsSubscribeMasters(query);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/forecast/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public Pl3ForecastVo forecastDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.lookupForecast(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticItemCensusVo itemCensus(@PathVariable Pl3Channel type,
                                            @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getItemCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticItemCensusVo> itemCensusV1(@PathVariable Pl3Channel type,
                                                             @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getItemCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticFullCensusVo fullCensus(@PathVariable Pl3Channel type,
                                            @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getFullCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticFullCensusVo> fullCensusV1(@PathVariable Pl3Channel type,
                                                             @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getFullCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo fullCensus(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "级别数据错误") @PathVariable
    Integer level) {
        return plsLottoCommandService.getTypeLeveledCensusDetail(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> fullCensusV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "级别数据错误") @PathVariable
    Integer level) {
        return plsLottoCommandService.getTypeLeveledCensusDetailV1(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticVipCensusVo vipCensus(@PathVariable Pl3Channel type,
                                          @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getVipCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticVipCensusVo> vipCensusV1(@PathVariable Pl3Channel type,
                                                           @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getVipCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo vipCensus(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "级别数据错误") @PathVariable Integer level) {
        return plsLottoCommandService.getTypeLeveledCensusDetail(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> vipCensusV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "级别数据错误") @PathVariable Integer level) {
        return plsLottoCommandService.getTypeLeveledCensusDetailV1(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo rateCensus(@NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getHotOrRateCensusDetail(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> rateCensusV1(@NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getHotOrRateCensusDetailV1(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo hotCensus(@NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getHotOrRateCensusDetail(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> hotCensusV1(@NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getHotOrRateCensusDetailV1(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/compare/{type}")
    @Permission(domain = LotteryAuth.USER)
    public List<ICaiRankedDataVo> batchCompare(@PathVariable Pl3Channel type,
                                               @NotNull(message = "用户标识为空") Long userId) {
        return plsLottoCommandService.getBatchCompareForecast(userId, type.getChannel(), 15);
    }

    @GetMapping("/schema/masters")
    public List<Pl3MasterSchemaVo> pl3SchemaMasters() {
        return pl3LottoQueryService.getPl3SchemaMasters(3);
    }

    @GetMapping("/renew/masters")
    public List<SchemaRenewMasterVo> renewMasters() {
        return pl3LottoQueryService.schemaRenewMasters();
    }

    @ApiBody(encrypt = true)
    @PostMapping("/battle")
    @Permission(domain = LotteryAuth.USER)
    public MasterBattleVo addBattle(@NotNull(message = "用户标识为空") Long userId,
                                    @NotBlank(message = "专家标识为空") String masterId) {
        return plsLottoCommandService.addMasterBattle(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/battles")
    @Permission(domain = LotteryAuth.USER)
    public List<MasterBattleVo> latestBattle(@NotNull(message = "用户标识为空") Long userId) {
        return pl3LottoQueryService.getPl3MasterBattles(userId);
    }

    @GetMapping("/battle/ranks")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterBattleRankVo<Pl3MasterMulRankVo>> battleMasterRanks(@Validated MasterBattleRankQuery query) {
        return pl3LottoQueryService.getPl3BattleMasterRanks(query);
    }

    @GetMapping("/master/rate")
    @Permission(domain = LotteryAuth.USER)
    public Pl3MasterRateVo masterRate(@NotBlank(message = "专家标识为空") String masterId) {
        return pl3LottoQueryService.getPl3MasterRate(masterId);
    }

    @GetMapping("/hits")
    @Permission(domain = LotteryAuth.USER)
    public List<Pl3ICaiHitVo> masterHits(@NotBlank(message = "专家标识为空") String masterId) {
        return pl3LottoQueryService.getPl3MasterBeforeHits(masterId);
    }

    @PostMapping("/warn")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<N3WarnRecommendVo> warnRecommend(@NotNull(message = "用户标识为空") Long userId,
                                                          String period) {
        return plsLottoCommandService.warnRecommend(userId, period);
    }

    @GetMapping("/warn/periods")
    public List<String> warnPeriods() {
        return pl3LottoQueryService.warningPeriods();
    }

    @PostMapping("/pivot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<N3TodayPivotVo> todayPivot(@NotNull(message = "用户表示为空") Long userId, String period) {
        return plsLottoCommandService.todayPivot(userId, period);
    }

    @GetMapping("/pivot/periods")
    public List<String> pivotPeriods() {
        return pl3LottoQueryService.pivotPeriods();
    }

}
