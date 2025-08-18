package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.INotifyStatsQueryService;
import com.prize.lottery.application.query.dto.NotifyChartQuery;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.vo.DeviceMetricsVo;
import com.prize.lottery.infrast.persist.vo.PushMetricsVo;
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
@RequestMapping("/adm")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class StatsAdmController {

    private final INotifyStatsQueryService statsQueryService;

    @GetMapping("/device/metrics")
    public DeviceMetricsVo deviceMetrics(@NotNull(message = "应用标识为空") Long appKey) {
        return statsQueryService.getAppDeviceMetrics(appKey);
    }

    @GetMapping("device/chart")
    public CensusLineChartVo<Long> deviceChart(@Validated NotifyChartQuery query) {
        return statsQueryService.deviceMetrics(query);
    }

    @GetMapping("/push/metrics")
    public PushMetricsVo pushMetrics(@NotNull(message = "应用标识为空") Long appKey) {
        return statsQueryService.getAppPushMetrics(appKey);
    }

    @GetMapping("/push/chart")
    public CensusLineChartVo<Long> pushChart(@Validated NotifyChartQuery query) {
        return statsQueryService.notifyMetrics(query);
    }

}
