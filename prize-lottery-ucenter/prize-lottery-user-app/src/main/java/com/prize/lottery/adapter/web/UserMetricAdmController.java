package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.application.command.IUserStatsCommandService;
import com.prize.lottery.application.query.IUserMetricQueryService;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.UserMetricsTrendVo;
import com.prize.lottery.application.query.vo.UserTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.MemberTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.UserMetricsVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/census")
public class UserMetricAdmController {

    private final IUserMetricQueryService  metricQueryService;
    private final IUserStatsCommandService statsCommandService;

    @PostMapping("/report")
    public void userReport(@NotNull(message = "统计日期为空") LocalDate date) {
        statsCommandService.dateReport(date);
    }

    @GetMapping()
    public UserMetricsVo metrics() {
        return metricQueryService.userMetrics();
    }

    @GetMapping("/trends")
    public List<UserMetricsTrendVo> metricsTrends() {
        return metricQueryService.metricsTrendList();
    }

    @GetMapping("/chart")
    public CensusLineChartVo<Integer> metricsChart(@NotNull(message = "统计天数为空")
                                                   @Enumerable(ranges = {"10", "15", "20", "25", "30"}) Integer day) {
        return metricQueryService.metricsChart(day);
    }

    @GetMapping("/total")
    public UserTotalMetricsVo totalMetrics(@Enumerable(ranges = {"7", "30", "90"}, message = "查询天数错误")
                                           @NotNull(message = "查询时间天数为空") Integer days) {
        return metricQueryService.userTotalMetrics(days);
    }

    @GetMapping("/member/total")
    public MemberTotalMetricsVo memberMetrics(@Enumerable(ranges = {"7", "30", "90"}, message = "查询天数错误")
                                              @NotNull(message = "查询时间天数为空") Integer days) {
        return metricQueryService.memberTotalMetrics(days);
    }

}
