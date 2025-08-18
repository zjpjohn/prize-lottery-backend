package com.prize.lottery.adapter.share;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.application.query.dto.LotteryListQuery;
import com.prize.lottery.application.query.dto.LottoFilterQuery;
import com.prize.lottery.application.query.service.ILotteryQueryService;
import com.prize.lottery.application.query.service.INum3WarnQueryService;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.*;
import com.prize.lottery.vo.Num3LayerStateVo;
import com.prize.lottery.vo.Num3LottoFollowVo;
import com.prize.lottery.vo.pl5.Pl5ItemOmitVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/share/lotto")
public class LotteryShareController {

    private final ILotteryQueryService  lotteryQueryService;
    private final INum3WarnQueryService num3WarnQueryService;

    @GetMapping("/periods")
    public List<String> lotteryPeriods(@NotNull(message = "彩票类型为空") LotteryEnum type,
                                       @NotNull(message = "查询数量为空") @Positive(message = "查询数量为整数") Integer limit) {
        return lotteryQueryService.lotteryPeriods(type, limit);
    }

    @GetMapping("/newest/{type}")
    public LotteryInfoPo latestLottery(@PathVariable LotteryEnum type) {
        return lotteryQueryService.getLatestLottery(type);
    }

    @GetMapping("/latest/period/{type}")
    public String latestPeriod(@PathVariable LotteryEnum type) {
        return lotteryQueryService.latestPeriod(type);
    }

    @GetMapping("/newest/group")
    public List<LotteryInfoPo> groupLatest() {
        return lotteryQueryService.getLatestGroupsLotteries();
    }

    @GetMapping("/newest/group/v1")
    public List<LotteryInfoPo> groupV1Latest() {
        return lotteryQueryService.getLatestGroupsV1Lotteries();
    }

    @PostMapping("/newest/types")
    public List<LotteryInfoPo> typedLatest(@RequestBody @NotEmpty(message = "类型为空") List<String> types) {
        return lotteryQueryService.getTypedLatestLotteries(types);
    }

    @GetMapping("/latest/list")
    public List<LotteryInfoPo> latestLotteries(@NotNull(message = "彩票类型为空") LotteryEnum type, @Range(
            min = 1, max = 10, message = "最多允许查询最近期10期数据") @NotNull(
            message = "查询期数为空") Integer limit) {
        return lotteryQueryService.getLatestLimitLotteries(type, limit);
    }

    @GetMapping("/list")
    public Page<LotteryInfoPo> lotteryPages(@Validated LotteryListQuery query) {
        return lotteryQueryService.getLotteryInfoByPage(query);
    }

    @GetMapping("/qtable")
    public Map<String, LotteryInfoPo> fastTable(@Enumerable(ranges = {"fc3d", "pl3"}, message = "彩票类型错误.")
                                                @NotNull(message = "彩票类型为空") String type, String period) {
        return lotteryQueryService.lotteryFastTable(type, period);
    }

    @GetMapping("/{type}/{period}")
    public LotteryInfoVo lotteryDetail(
            @Enumerable(enums = LotteryEnum.class, message = "彩票种类错误.") @PathVariable String type,
            @PathVariable String period) {
        return lotteryQueryService.getLotteryDetail(type, period);
    }

    @GetMapping("/omit/{lottery}")
    public List<LotteryOmitPo> lotteryOmits(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum lottery,
                                            @Enumerable(
                                                    ranges = {"50", "80", "100", "120", "150", "200"},
                                                    message = "查询数据量错误") @RequestParam(defaultValue = "50")
                                            Integer size) {
        return lotteryQueryService.getLotteryOmitList(lottery, size);
    }


    @GetMapping("/pl5/omit")
    public List<LotteryPl5OmitPo> pl5OmitList(
            @Enumerable(ranges = {"50", "80", "100", "120", "150", "200"}, message = "查询数据量错误")
            @RequestParam(defaultValue = "50") Integer size) {
        return lotteryQueryService.getPl5OmitList(size);
    }

    @GetMapping("/omit/item/pl5")
    public List<Pl5ItemOmitVo> pl5ItemOmits(@Enumerable(ranges = {"1", "2", "3", "4", "5"}, message = "查询类型错误")
                                            @NotNull(message = "查询类型为空") Integer type, @Enumerable(
            ranges = {"50", "80", "100", "120", "150", "200"}, message = "查询数据量错误")
                                            @RequestParam(defaultValue = "50") Integer size) {
        return lotteryQueryService.getPl5ItemOmits(type, size);
    }

    @GetMapping("/omit/kua/{type}")
    public List<LotteryKuaOmitPo> kuaOmitList(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type,
                                              @Enumerable(
                                                      ranges = {"50", "80", "100", "120", "150", "200"},
                                                      message = "查询数据量错误") @RequestParam(defaultValue = "50")
                                              Integer size) {
        return lotteryQueryService.getKuaOmitList(type, size);
    }

    @GetMapping("/omit/trend/{type}")
    public List<LotteryTrendOmitPo> trendOmitList(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type,
                                                  @Enumerable(
                                                          ranges = {"50", "80", "100", "120", "150", "200"},
                                                          message = "查询数据量错误") @RequestParam(defaultValue = "50")
                                                  Integer size) {
        return lotteryQueryService.getTrendOmitList(type, size);
    }

    @GetMapping("/omit/match/{type}")
    public List<LotteryMatchOmitPo> matchOmitList(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type,
                                                  @Enumerable(
                                                          ranges = {"50", "80", "100", "120", "150", "200"},
                                                          message = "查询数据量错误") @RequestParam(defaultValue = "50")
                                                  Integer size) {
        return lotteryQueryService.getMatchOmitList(type, size);
    }

    @GetMapping("/omit/item/{type}")
    public List<LotteryItemOmitPo> itemOmitList(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type,
                                                @Enumerable(
                                                        ranges = {"50", "80", "100", "120", "150", "200"},
                                                        message = "查询数据量错误") @RequestParam(defaultValue = "50")
                                                Integer size) {
        return lotteryQueryService.getItemOmitList(type, size);
    }

    @GetMapping("/omit/sum/{type}")
    public List<LotterySumOmitPo> sumOmitList(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type,
                                              @Enumerable(
                                                      ranges = {"50", "80", "100", "120", "150", "200"},
                                                      message = "查询数据量错误") @RequestParam(defaultValue = "50")
                                              Integer size) {
        return lotteryQueryService.getSumOmitList(type, size);
    }

    @GetMapping("/kl8/omit")
    public List<LotteryKl8OmitPo> kl8Omits() {
        return lotteryQueryService.getKl8OmitList();
    }

    @GetMapping("/kl8/tail/omit")
    public List<LotteryKl8OmitPo> kl8TailOmits(@NotNull(message = "查询量为空")
                                               @Enumerable(ranges = {"30", "50", "80", "100"}, message = "查询数量错误")
                                               Integer limit) {
        return lotteryQueryService.getKl8TailOmits(limit);
    }

    @GetMapping("/kl8/base/omit/list")
    public Page<LotteryKl8OmitPo> kl8BaseOmits(@Validated PageQuery query) {
        return lotteryQueryService.getKl8BaseOmits(query);
    }

    @GetMapping("/code/list")
    public List<LotteryCodeVo> codeList(@NotNull(message = "彩种类型为空") LotteryEnum lotto,
                                        @NotNull(message = "万能码类型为空") CodeType type) {
        return lotteryQueryService.getLotteryCodeList(lotto, type);
    }

    @GetMapping("/dan/list")
    public List<LotteryDanVo> lottoDanList(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        return lotteryQueryService.getLotteryDanList(type);
    }

    @GetMapping("/ott/list")
    public List<LotteryOttVo> lottoOttList(@NotNull(message = "彩种类型为空") LotteryEnum type, @Enumerable(
            ranges = {"50", "80", "100", "120", "150", "200"}, message = "查询数据量错误")
    @RequestParam(defaultValue = "50") Integer size) {
        return lotteryQueryService.getLotteryOttList(type, size);
    }

    @GetMapping("/around/periods")
    public List<String> aroundPeriods(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        return lotteryQueryService.aroundPeriods(type);
    }

    @GetMapping("/around")
    public LotteryAroundVo lotteryAround(@NotNull(message = "彩种类型为空") LotteryEnum type, String period) {
        return lotteryQueryService.lotteryAround(period, type);
    }

    @GetMapping("/honey/periods")
    public List<String> honeyPeriods(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        return lotteryQueryService.honeyPeriods(type);
    }

    @GetMapping("/honey")
    public LotteryHoneyVo honeyAround(@NotNull(message = "彩种类型为空") LotteryEnum type, String period) {
        return lotteryQueryService.lotteryHoney(period, type);
    }

    @GetMapping("/num3/lottery")
    public Num3LotteryVo num3Lottery(@NotNull(message = "彩种类型为空") LotteryEnum type, String period) {
        return lotteryQueryService.getNum3Lottery(type, period);
    }

    @GetMapping("/num3/last/lottery")
    public Num3LotteryVo num3LastLottery(@NotNull(message = "彩种类型为空") LotteryEnum type, String period) {
        return lotteryQueryService.getNum3LastLottery(type, period);
    }

    @GetMapping("/num3/periods")
    public List<String> num3LotteryPeriods(@NotNull(message = "彩票类型错误") LotteryEnum type) {
        return lotteryQueryService.num3LotteryPeriods(type);
    }

    @GetMapping("/num3/lotteries")
    public List<LotteryInfoPo> num3BeforeLotteries(@NotNull(message = "彩票类型错误") LotteryEnum type, @Range(
            min = 1, max = 10, message = "最多允许查询最近期10期数据") @NotNull(
            message = "查询期数为空") Integer limit, String period) {
        return lotteryQueryService.num3BeforeLimitLotteries(type, period, limit);
    }

    @GetMapping("/num3/dan")
    public Num3LottoDanVo lottoDan(@NotNull(message = "彩票类型为空") LotteryEnum type, String period) {
        return lotteryQueryService.getNum3LottoDan(type, period);
    }

    @GetMapping("/num3/pian/omit")
    public List<LottoN3PianOmitVo> n3PianOmits(@NotNull(message = "彩票类型为空") LotteryEnum type, @Enumerable(
            ranges = {"20", "40", "60", "80"}, message = "查询数据量错误") @RequestParam(defaultValue = "30")
    Integer limit) {
        return lotteryQueryService.getN3PianOmits(type, limit);
    }

    @GetMapping("/trial/periods")
    public List<String> trialPeriods(@NotNull(message = "开奖类型为空") LotteryEnum type) {
        return lotteryQueryService.trialPeriods(type, 30);
    }

    @GetMapping("/trial/table")
    public Map<String, LotteryFairTrialPo> trailTable(@NotNull(message = "开奖类型为空") LotteryEnum type,
                                                      String period) {
        return lotteryQueryService.trailTable(type, period);
    }

    @GetMapping("/num3/com/counts")
    public Map<Integer, List<String>> num3ComCounts(@NotNull(message = "彩种类型为空") LotteryEnum type,
                                                    @NotNull(message = "统计期数为空") Integer limit) {
        return lotteryQueryService.getNum3ComCounts(type, limit);
    }

    @GetMapping("/num3/follow/list")
    public List<Num3LottoFollowVo> numFollowList(@NotNull(message = "彩种类型为空") LotteryEnum type, String period) {
        return lotteryQueryService.getNum3FollowList(type, period);
    }

    @GetMapping("/num3/com/follow")
    public List<Num3LottoFollowVo> comFollowList(@NotNull(message = "彩种类型为空") LotteryEnum type,
                                                 @NotBlank(message = "组选号码为空") String com) {
        return lotteryQueryService.getComFollowList(type, com);
    }

    @PostMapping("/num3/filter")
    public List<Num3LottoFollowVo> num3FilterList(@Validated @RequestBody LottoFilterQuery query) {
        return lotteryQueryService.getFilterList(query);
    }

    @GetMapping("/num3/layer/state")
    public Num3LayerStateVo num3LayerState(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        return num3WarnQueryService.getNum3LayerState(type);
    }

}

