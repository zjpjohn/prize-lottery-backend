package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.IFsdLottoCommandService;
import com.prize.lottery.application.query.dto.FsdRankQuery;
import com.prize.lottery.application.query.dto.MasterBattleRankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.service.IFsdLottoQueryService;
import com.prize.lottery.application.vo.Fc3dForecastVo;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.N3TodayPivotVo;
import com.prize.lottery.application.vo.N3WarnRecommendVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.fc3d.*;
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
@RequestMapping(value = "/app/fsd")
@RequiredArgsConstructor
public class FsdLottoAppController {

    private final IFsdLottoQueryService   fsdLottoQueryService;
    private final IFsdLottoCommandService fsdLottoCommandService;

    @GetMapping("/glad")
    public Page<Fc3dICaiGladVo> lottoGlads(@Validated PageQuery query) {
        return fsdLottoQueryService.getFc3dGladList(query);
    }

    @GetMapping("/random/masters")
    public List<Fc3dMasterMulRankVo> randomMasters() {
        return fsdLottoQueryService.getFc3dRandomMasters(6, 120);
    }

    @GetMapping("/rank")
    public Page<Fc3dMasterRankVo> rankList(@Validated FsdRankQuery query) {
        return fsdLottoQueryService.getFc3dRankMasters(query);
    }

    @GetMapping("/mul/rank")
    public Page<Fc3dMasterMulRankVo> mulRankList(@Validated PageQuery query) {
        return fsdLottoQueryService.getFc3dMasterMulRanks(query);
    }

    @GetMapping("/history/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public List<Fc3dIcaiHistoryVo> masterHistories(@PathVariable String masterId) {
        return fsdLottoQueryService.getFsdMasterHistories(masterId);
    }

    @GetMapping("/master/homed")
    public Map<String, List<HomeMasterVo>> fsdHomedMasters() {
        return fsdLottoQueryService.getFsdHomedMasters();
    }

    @GetMapping("/master/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public Fc3dMasterDetail masterDetail(@PathVariable String masterId,
                                         @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoQueryService.getFsdMasterDetail(masterId, userId);
    }

    @GetMapping("/master/follow")
    @Permission(domain = LotteryAuth.USER)
    public Page<Fc3dMasterSubscribeVo> subscribeMasters(@Validated SubscribeQuery query) {
        return fsdLottoQueryService.getFsdSubscribeMasters(query);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/forecast/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public Fc3dForecastVo forecastDetail(@PathVariable String masterId,
                                         @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.lookupForecast(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticItemCensusVo itemCensus(@PathVariable Fc3dChannel type,
                                            @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getItemCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticItemCensusVo> itemCensusV1(@PathVariable Fc3dChannel type,
                                                             @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getItemCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticFullCensusVo fullCensus(@PathVariable Fc3dChannel type,
                                            @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getFullCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticFullCensusVo> fullCensusV1(@PathVariable Fc3dChannel type,
                                                             @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getFullCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo leveledFullCensus(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "级别数据错误") @PathVariable
    Integer level) {
        return fsdLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> leveledFullCensusV1(@NotNull(message = "用户标识为空") Long userId,
                                                                  @Enumerable(
                                                                          ranges = {
                                                                                  "100",
                                                                                  "200",
                                                                                  "400",
                                                                                  "600",
                                                                                  "800",
                                                                                  "1000"
                                                                          }, message = "级别数据错误") @PathVariable
                                                                  Integer level) {
        return fsdLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticVipCensusVo vipCensus(@PathVariable Fc3dChannel type,
                                          @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getVipCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticVipCensusVo> vipCensusV1(@PathVariable Fc3dChannel type,
                                                           @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getVipCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo levelVipCensus(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "级别数据错误") @PathVariable Integer level) {
        return fsdLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> levelVipCensusV1(@NotNull(message = "用户标识为空") Long userId,
                                                               @Enumerable(
                                                                       ranges = {"10", "20", "50", "100", "150"},
                                                                       message = "级别数据错误") @PathVariable
                                                               Integer level) {
        return fsdLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo rateCensus(@NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getHotOrRateCensusDetail(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> rateCensusV1(@NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getHotOrRateCensusDetailV1(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public NumberThreeCensusVo hotCensus(@NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getHotOrRateCensusDetail(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<NumberThreeCensusVo> hotCensusV1(@NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getHotOrRateCensusDetailV1(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/compare/{type}")
    @Permission(domain = LotteryAuth.USER)
    public List<ICaiRankedDataVo> batchCompare(@PathVariable Fc3dChannel type,
                                               @NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoCommandService.getBatchCompareForecast(userId, type.getChannel(), 15);
    }

    @GetMapping("/schema/masters")
    public List<Fc3dMasterSchemaVo> schemaMasters() {
        return fsdLottoQueryService.fc3dSchemaMasters(3);
    }

    @GetMapping("/renew/masters")
    public List<SchemaRenewMasterVo> renewMaster() {
        return fsdLottoQueryService.schemaRenewMasters();
    }

    @ApiBody(encrypt = true)
    @PostMapping("/battle")
    @Permission(domain = LotteryAuth.USER)
    public MasterBattleVo addBattle(@NotNull(message = "用户标识为空") Long userId,
                                    @NotBlank(message = "专家标识为空") String masterId) {
        return fsdLottoCommandService.addMasterBattle(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/battles")
    @Permission(domain = LotteryAuth.USER)
    public List<MasterBattleVo> latestBattles(@NotNull(message = "用户标识为空") Long userId) {
        return fsdLottoQueryService.getFc3dMasterBattles(userId);
    }

    @GetMapping("/battle/ranks")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterBattleRankVo<Fc3dMasterMulRankVo>> battleMasterRanks(@Validated MasterBattleRankQuery query) {
        return fsdLottoQueryService.getFc3dBattleMasterRanks(query);
    }

    @GetMapping("/master/rate")
    @Permission(domain = LotteryAuth.USER)
    public Fc3dMasterRateVo masterRate(@NotBlank(message = "专家标识为空") String masterId) {
        return fsdLottoQueryService.getFc3dMasterRate(masterId);
    }

    @GetMapping("/hits")
    @Permission(domain = LotteryAuth.USER)
    public List<Fc3dICaiHitVo> masterHits(@NotBlank(message = "专家标识为空") String masterId) {
        return fsdLottoQueryService.getFc3dMasterBeforeHits(masterId);
    }

    @PostMapping("/warn")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<N3WarnRecommendVo> warnRecommend(@NotNull(message = "用户标识为空") Long userId,
                                                          String period) {
        return fsdLottoCommandService.warnRecommend(userId, period);
    }

    @GetMapping("/warn/periods")
    public List<String> warnPeriods() {
        return fsdLottoQueryService.warningPeriods();
    }

    @PostMapping("/pivot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<N3TodayPivotVo> todayPivot(@NotNull(message = "用户标识为空") Long userId, String period) {
        return fsdLottoCommandService.todayPivot(userId, period);
    }

    @GetMapping("/pivot/periods")
    public List<String> pivotPeriods() {
        return fsdLottoQueryService.pivotPeriods();
    }

}
