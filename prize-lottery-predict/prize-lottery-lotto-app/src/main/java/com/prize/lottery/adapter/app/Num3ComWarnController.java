package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.INum3WarnCommandService;
import com.prize.lottery.application.query.service.INum3WarnQueryService;
import com.prize.lottery.application.vo.Num3ComWarnVo;
import com.prize.lottery.application.vo.Num3LayerVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app/num3")
public class Num3ComWarnController {

    private final INum3WarnQueryService   warnQueryService;
    private final INum3WarnCommandService warnCommandService;

    @PostMapping("/warn")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<Num3ComWarnVo> browse(@NotNull(message = "用户标识为空") Long userId,
                                               @NotNull(message = "彩种类型为空") LotteryEnum type,
                                               String period) {
        return warnCommandService.num3ComWarn(userId, type, period);
    }

    @GetMapping("/warn/periods")
    public List<String> warnPeriods(@NotNull(message = "彩种类型错误") LotteryEnum type) {
        return warnQueryService.comWarningPeriods(type);
    }

    @PostMapping("/layer")
    @Permission(domain = LotteryAuth.USER)
    public FeeDataResult<Num3LayerVo> browseLayer(@NotNull(message = "用户标识为空") Long userId,
                                                  @NotNull(message = "彩种类型为空") LotteryEnum type,
                                                  String period) {
        return warnCommandService.num3Layer(userId, type, period);
    }

    @GetMapping("/layer/periods")
    public List<String> layerPeriods(@NotNull(message = "彩种类型错误") LotteryEnum type) {
        return warnQueryService.num3LayerPeriods(type);
    }

}
