package com.prize.lottery.adapter.adm;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IPayOrderQueryService;
import com.prize.lottery.application.query.vo.PayTotalMetricsVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/metrics")
@Permission(domain = LotteryAuth.MANAGER)
public class PayMetricsAdmController {

    private final IPayOrderQueryService payOrderQueryService;

    @GetMapping("/total")
    public PayTotalMetricsVo totalMetrics(@Enumerable(
            ranges = {
                    "7", "30", "90"
            }, message = "查询天数错误") @NotNull(message = "查询时间天数为空") Integer days) {
        return payOrderQueryService.payTotalMetrics(days);
    }

}
