package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.service.ILotteryQueryService;
import com.prize.lottery.application.query.service.IMasterQueryService;
import com.prize.lottery.application.vo.LotteryCodeVo;
import com.prize.lottery.application.vo.LottoBrowseVo;
import com.prize.lottery.infrast.commons.WensFilterResult;
import com.prize.lottery.po.lottery.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/lotto")
@Permission(domain = LotteryAuth.MANAGER)
public class LotteryAdmController {

    private final ILotteryQueryService lotteryQueryService;
    private final IMasterQueryService  masterQueryService;

    @PostMapping("/filter")
    public WensFilterResult wensFilter(@RequestBody @Validated WensFilterQuery query) {
        return lotteryQueryService.filterNum3Lotto(query);
    }

    @GetMapping("/index/list")
    public Page<LotteryIndexPo> lotteryIndexes(@Validated LotteryIndexQuery query) {
        return lotteryQueryService.getLotteryIndexList(query);
    }

    @GetMapping("/code/list")
    public Page<LotteryCodeVo> codeList(@Validated LotteryCodeQuery query) {
        return lotteryQueryService.getLotteryCodeList(query);
    }

    @GetMapping("/browse/list")
    public Page<LottoBrowseVo> browseList(@Validated LottoBrowseQuery query) {
        return masterQueryService.lottoBrowseList(query);
    }

    @GetMapping("/around/list")
    public Page<LotteryAroundPo> aroundList(@Validated LottoAroundQuery query) {
        return lotteryQueryService.getAroundList(query);
    }

    @GetMapping("/honey/list")
    public Page<LotteryHoneyPo> honeyList(@Validated LottoHoneyQuery query) {
        return lotteryQueryService.getHoneyList(query);
    }

    @GetMapping("/num3/index")
    public Page<Num3LottoIndexPo> num3Index(@Validated Num3IndexQuery query) {
        return lotteryQueryService.getNum3IndexList(query);
    }

    @GetMapping("/fair/trial/list")
    public Page<LotteryFairTrialPo> trialList(@Validated TrialListQuery query) {
        return lotteryQueryService.fairTrialList(query);
    }

}
