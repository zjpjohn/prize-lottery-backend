package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.IQlcLottoCommandService;
import com.prize.lottery.application.query.dto.MasterBattleRankQuery;
import com.prize.lottery.application.query.dto.QlcRankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.application.query.service.IQlcLottoQueryService;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.QlcForecastVo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.qlc.*;
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
@RequestMapping(value = "/app/qlc")
public class QlcLottoAppController {

    private final IQlcLottoQueryService   qlcLottoQueryService;
    private final IQlcLottoCommandService qlcLottoCommandService;

    @GetMapping("/glad")
    public Page<QlcICaiGladVo> qlcGladList(@Validated PageQuery query) {
        return qlcLottoQueryService.getQlcGladList(query);
    }

    @GetMapping("/random/masters")
    public List<QlcMasterMulRankVo> randomMasters() {
        return qlcLottoQueryService.getQlcRandomMasters(6, 60);
    }

    @GetMapping("/rank")
    public Page<QlcMasterRankVo> masterRankList(@Validated QlcRankQuery query) {
        return qlcLottoQueryService.getQlcRankMasters(query);
    }

    @GetMapping("/mul/rank")
    public Page<QlcMasterMulRankVo> mulRankList(@Validated PageQuery query) {
        return qlcLottoQueryService.getQlcMasterMulRankList(query);
    }

    @GetMapping("/history/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public List<QlcIcaiHistoryVo> masterHistories(@PathVariable String masterId) {
        return qlcLottoQueryService.getQlcMasterHistories(masterId);
    }

    @GetMapping("/master/homed")
    public Map<String, List<HomeMasterVo>> qlcHomedMasters() {
        return qlcLottoQueryService.getQlcHomedMasters();
    }

    @GetMapping("/master/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public QlcMasterDetail masterDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoQueryService.getQlcMasterDetail(masterId, userId);
    }

    @GetMapping("/master/follow")
    @Permission(domain = LotteryAuth.USER)
    public Page<QlcMasterSubscribeVo> subscribeMasters(@Validated SubscribeQuery query) {
        return qlcLottoQueryService.getQlcMasterSubscribeList(query);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/forecast/{masterId}")
    @Permission(domain = LotteryAuth.USER)
    public QlcForecastVo forecastDetail(@PathVariable String masterId, @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.lookupForecast(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticItemCensusVo itemChart(@PathVariable QlcChannel type,
                                           @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getItemCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/item/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticItemCensusVo> itemChartV1(@PathVariable QlcChannel type,
                                                            @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getItemCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticFullCensusVo fullChart(@PathVariable QlcChannel type,
                                           @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getFullCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/full/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticFullCensusVo> fullChartV1(@PathVariable QlcChannel type,
                                                            @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getFullCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public QlcChartCensusVo levelFull(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "分级数据错误") @PathVariable
    Integer level) {
        return qlcLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/full/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<QlcChartCensusVo> levelFullV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"100", "200", "400", "600", "800", "1000"}, message = "分级数据错误") @PathVariable
    Integer level) {
        return qlcLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.ALL_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public SyntheticVipCensusVo vipChart(@PathVariable QlcChannel type,
                                         @NotNull(message = "用户表示为空") Long userId) {
        return qlcLottoCommandService.getVipCensusDetail(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/vip/{type}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<SyntheticVipCensusVo> vipChartV1(@PathVariable QlcChannel type,
                                                          @NotNull(message = "用户表示为空") Long userId) {
        return qlcLottoCommandService.getVipCensusDetailV1(userId, type.getChannel());
    }

    @ApiBody(encrypt = true)
    @GetMapping("/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public QlcChartCensusVo levelVipChart(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "分级数据错误") @PathVariable Integer level) {
        return qlcLottoCommandService.getTypeLevelCensusDetail(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/vip/{level}")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<QlcChartCensusVo> levelVipChartV1(@NotNull(message = "用户标识为空") Long userId, @Enumerable(
            ranges = {"10", "20", "50", "100", "150"}, message = "分级数据错误") @PathVariable Integer level) {
        return qlcLottoCommandService.getTypeLevelCensusDetailV1(userId, ChartType.VIP_CHART, level);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public QlcChartCensusVo rateChart(@NotNull(message = "用户表示为空") Long userId) {
        return qlcLottoCommandService.getHotOrRateCensusDetail(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/rate")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<QlcChartCensusVo> rateChartV1(@NotNull(message = "用户表示为空") Long userId) {
        return qlcLottoCommandService.getHotOrRateCensusDetailV1(ChartType.RATE_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public QlcChartCensusVo hotChart(@NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getHotOrRateCensusDetail(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/v1/chart/hot")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<QlcChartCensusVo> hotChartV1(@NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getHotOrRateCensusDetailV1(ChartType.HOT_CHART, userId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/compare/{type}")
    @Permission(domain = LotteryAuth.USER)
    public List<ICaiRankedDataVo> batchCompare(@PathVariable QlcChannel type,
                                               @NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoCommandService.getBatchCompareForecast(userId, type.getChannel(), 15);
    }

    @GetMapping("/schema/masters")
    public List<QlcMasterSchemaVo> schemaMasters() {
        return qlcLottoQueryService.getQlcSchemaMasters(3);
    }

    @GetMapping("/renew/masters")
    public List<SchemaRenewMasterVo> renewMasters() {
        return qlcLottoQueryService.schemaRenewMasters();
    }

    @ApiBody(encrypt = true)
    @PostMapping("/battle")
    @Permission(domain = LotteryAuth.USER)
    public MasterBattleVo addBattle(@NotNull(message = "用户标识为空") Long userId,
                                    @NotBlank(message = "专家标识为空") String masterId) {
        return qlcLottoCommandService.addMasterBattle(userId, masterId);
    }

    @ApiBody(encrypt = true)
    @GetMapping("/battles")
    @Permission(domain = LotteryAuth.USER)
    public List<MasterBattleVo> latestBattles(@NotNull(message = "用户标识为空") Long userId) {
        return qlcLottoQueryService.getQlcMasterBattles(userId);
    }

    @GetMapping("/battle/ranks")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterBattleRankVo<QlcMasterMulRankVo>> battleMasterRanks(@Validated MasterBattleRankQuery query) {
        return qlcLottoQueryService.getQlcBattleMasterRanks(query);
    }

    @GetMapping("/master/rate")
    @Permission(domain = LotteryAuth.USER)
    public QlcMasterRateVo masterRate(@NotBlank(message = "专家标识为空") String masterId) {
        return qlcLottoQueryService.getQlcMasterRate(masterId);
    }

    @GetMapping("/hits")
    @Permission(domain = LotteryAuth.USER)
    public List<QlcICaiHitVo> masterHits(@NotBlank(message = "专家标识为空") String masterId) {
        return qlcLottoQueryService.getQlcMasterBeforeHits(masterId);
    }

}
