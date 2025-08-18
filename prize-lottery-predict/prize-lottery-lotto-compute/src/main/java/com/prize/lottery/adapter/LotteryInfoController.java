package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.cmd.*;
import com.prize.lottery.application.service.ILotteryInfoService;
import com.prize.lottery.application.service.INum3ComWarnService;
import com.prize.lottery.application.service.INum3LayerService;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/lottery")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class LotteryInfoController {

    private final ILotteryInfoService lotteryInfoService;
    private final INum3ComWarnService num3ComWarnService;
    private final INum3LayerService   num3LayerService;

    @PostMapping("/{type}")
    public String fetchLatest(
            @PathVariable @Enumerable(enums = LotteryEnum.class, message = "开奖类型错误.") String type) {
        return lotteryInfoService.fetchLatestLottery(type);
    }

    @PostMapping("/{type}/{size}")
    public String fetchLottery(
            @PathVariable @Enumerable(enums = LotteryEnum.class, message = "开奖类型错误.") String type,
            @PathVariable @Min(value = 1, message = "数量必须大于0") Integer size) {
        return lotteryInfoService.fetchLotteries(type, size);
    }

    @PostMapping("/range")
    public String fetchLottery(@NotBlank(message = "彩票类型为空")
                               @Enumerable(enums = LotteryEnum.class, message = "开奖类型错误.") String type,
                               @NotBlank(message = "开始期号为空") String start,
                               @NotBlank(message = "截止期号为空") String end) {
        return lotteryInfoService.fetchLotteries(type, start, end);
    }

    @PostMapping("/trial/{type}/{size}")
    public void fetchBatchTrail(
            @PathVariable @Enumerable(ranges = {"fc3d", "pl3"}, message = "开奖类型错误.") String type,
            @PathVariable @Min(value = 1, message = "数量必须大于0") Integer size) {
        lotteryInfoService.fetchLotteryTrials(type, size);
    }

    @PostMapping("/trial/{type}")
    public void fetchTrail(@PathVariable @Enumerable(ranges = {"fc3d", "pl3"}, message = "开奖类型错误.") String type,
                           @NotBlank(message = "期号为空") String period) {
        lotteryInfoService.fetchLotteryTrial(type, period);
    }

    @PostMapping("/omit/initial")
    public void loadInitialOmit() {
        lotteryInfoService.loadInitialOmit();
    }

    @PostMapping("/omit/calc/{type}")
    public void initializeOmit(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type) {
        lotteryInfoService.calcLotteryOmit(type);
    }

    @PostMapping("/omit/pian/calc/{type}")
    public void initPianOmits(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type) {
        lotteryInfoService.initPianOmits(type);
    }

    @PostMapping("/code/calc/{type}")
    public void initializeCode(@NotNull(message = "彩种类型为空") @PathVariable LotteryEnum type) {
        lotteryInfoService.codeInitialize(type);
    }

    @PostMapping("/omit/kua/load")
    public void loadKuaOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.loadKuaOmit(type);
    }

    @PostMapping("/omit/kua/calc")
    public void calcKuaOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.calcKuaOmit(type);
    }

    @PostMapping("/omit/trend/load")
    public void loadTrendOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.loadTrendOmit(type);
    }

    @PostMapping("/omit/trend/calc")
    public void calcTrendOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.calcTrendOmit(type);
    }

    @PostMapping("/omit/match/load")
    public void loadMatchOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.loadMatchOmit(type);
    }

    @PostMapping("/omit/match/calc")
    public void calcMatchOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.calcMatchOmit(type);
    }

    @PostMapping("/omit/item/load")
    public void loadItemOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.loadItemOmit(type);
    }

    @PostMapping("/omit/item/calc")
    public void calcItemOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.calcItemOmit(type);
    }

    @PostMapping("/omit/sum/load")
    public void loadSumOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.loadSumOmit(type);
    }

    @PostMapping("/omit/sum/calc")
    public void calcSumOmit(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.calcSumOmit(type);
    }

    @PostMapping("/dan/init")
    public void initLoadDan(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.initLottoDan(type);
    }

    @PostMapping("/ott/init")
    public void initLottoOtt(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        lotteryInfoService.initLottoOtt(type);
    }

    @PostMapping("/around")
    public void addAround(@RequestBody @Validated AroundSingleCmd cmd) {
        lotteryInfoService.addAroundSingle(cmd);
    }

    @PostMapping("/around/batch")
    public void addAroundBatch(@RequestBody @Validated AroundBatchCmd cmd) {
        lotteryInfoService.addAroundBatch(cmd);
    }

    @PostMapping("/around/fetch")
    public void fetchAroundList(@PositiveOrZero(message = "期数必须大等于0") @Max(
            value = 20, message = "最多允许抓取20期") Integer size) {
        lotteryInfoService.fetchAround(size);
    }

    @DeleteMapping("/around")
    public void removeAround(@NotNull(message = "唯一标识为空") Long id) {
        lotteryInfoService.removeAround(id);
    }

    @PutMapping("/around/calc")
    public void calcAroundResult(@NotNull(message = "彩票类型为空") LotteryEnum type,
                                 @NotBlank(message = "期号为空") String period) {
        lotteryInfoService.calcAroundResult(type, period);
    }

    @PostMapping("/honey")
    public void addHoney(@NotNull(message = "彩票类型为空") LotteryEnum type,
                         @RequestBody @Validated HoneySingleCmd cmd) {
        lotteryInfoService.addHoneySingle(type, cmd);
    }

    @PostMapping("/honey/batch")
    public void addHoneyBatch(@NotNull(message = "彩票类型为空") LotteryEnum type,
                              @RequestBody @Valid @NotEmpty(message = "配胆数据集合为空") List<HoneySingleCmd> cmds) {
        lotteryInfoService.addHoneyBatch(type, cmds);
    }

    @DeleteMapping("/honey")
    public void removeHoney(@NotNull(message = "唯一标识为空") Long id) {
        lotteryInfoService.removeHoney(id);
    }

    @PostMapping("/num3/warn")
    public void createNumWarn(@RequestBody @Validated Num3ComWarnCmd cmd) {
        num3ComWarnService.createComWarn(cmd);
    }

    @PutMapping("/num3/warn/hit")
    public void calcWarnHit(@NotNull(message = "唯一标识为空") Long id) {
        num3ComWarnService.calcWarnHit(id);
    }

    @PostMapping("/num3/layer")
    public void createNum3Layer(@RequestBody @Validated Num3LayerCmd command) {
        num3LayerService.createNum3Layer(command);
    }

    @PostMapping("/num3/layer/sync")
    public void syncNum3Layer(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        num3LayerService.syncNum3Layer(type);
    }

    @PutMapping("/num3/layer/hit")
    public void calcLayerHit(@NotNull(message = "唯一标识为空") Long id) {
        num3LayerService.calcLayerHit(id);
    }

    @PostMapping("/num3/index")
    public void calcNum3Index(@NotNull(message = "彩票类型为空") LotteryEnum type,
                              @NotBlank(message = "计算期号为空") String period) {
        lotteryInfoService.calcNum3Index(type, period);
    }

    @PostMapping("/fair/trial")
    public void createFairTrials(@RequestBody @Valid TrialCreateCmd cmd) {
        lotteryInfoService.createFaireTrial(cmd);
    }

    @PutMapping("/init/n3/com")
    public void initN3Com(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        lotteryInfoService.initCalcNum3Com(type, 4000);
    }

    @PutMapping("/init/n3/same")
    public void initN3Same(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        lotteryInfoService.initCalcNum3Same(type, 200);
    }

}
