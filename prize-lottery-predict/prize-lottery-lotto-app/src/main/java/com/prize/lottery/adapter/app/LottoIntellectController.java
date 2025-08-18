package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.dto.LotteryIndexCmd;
import com.prize.lottery.application.command.dto.LottoN3PickCmd;
import com.prize.lottery.application.command.dto.LottoPickCmd;
import com.prize.lottery.application.command.service.ILotteryPickCommandService;
import com.prize.lottery.application.query.dto.LotteryPickQuery;
import com.prize.lottery.application.query.service.ILotteryPickQueryService;
import com.prize.lottery.application.vo.LotteryIndexVo;
import com.prize.lottery.application.vo.LotteryPickVo;
import com.prize.lottery.application.vo.Num3LottoIndexVo;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping(value = "/app/intellect")
@Permission(domain = LotteryAuth.USER)
public class LottoIntellectController {

    private final ILotteryPickQueryService   pickQueryService;
    private final ILotteryPickCommandService pickCommandService;

    @PostMapping(value = "/pick", params = "type=n3")
    public void lotteryN3Pick(@NotNull(message = "用户标识为空") Long userId,
                              @Validated @RequestBody LottoN3PickCmd command) {
        pickCommandService.lottoPick(userId, command);
    }

    @PostMapping(value = "/pick", params = "typ=rb")
    public void lotteryPick(@NotNull(message = "用户标识为空") Long userId,
                            @Validated @RequestBody LottoPickCmd command) {
        pickCommandService.lottoPick(userId, command);
    }

    @GetMapping("/pick")
    public LotteryPickVo lotteryPick(@Validated LotteryPickQuery query) {
        return pickQueryService.getLotteryPick(query);
    }

    @GetMapping("/index/periods")
    public List<String> indexPeriods(@Enumerable(enums = LotteryEnum.class, message = "彩票类型错误")
                                     @NotBlank(message = "彩票类型为空") String type) {
        return pickQueryService.indexPeriods(type);
    }

    @PostMapping("/index")
    public LotteryIndexVo lotteryIndex(@Validated LotteryIndexCmd command) {
        return pickCommandService.lotteryIndex(command);
    }

    @PostMapping("/num3/index")
    public Num3LottoIndexVo num3Index(@Validated LotteryIndexCmd command) {
        return pickCommandService.num3Index(command);
    }

    @GetMapping("/num3/index/periods")
    public List<String> num3Periods(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        return pickQueryService.num3IndexPeriods(type);
    }

}
