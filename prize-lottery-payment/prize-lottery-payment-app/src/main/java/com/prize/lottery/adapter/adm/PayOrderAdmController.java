package com.prize.lottery.adapter.adm;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IPayOrderQueryService;
import com.prize.lottery.application.query.dto.AdmOrderQuery;
import com.prize.lottery.application.query.vo.AdmOrderInfoVo;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.OrderMetricsTrendVo;
import com.prize.lottery.infrast.persist.vo.OrderMetricsVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/order")
@Permission(domain = LotteryAuth.MANAGER)
public class PayOrderAdmController {

    private final IPayOrderQueryService payOrderQueryService;

    @GetMapping("/")
    public AdmOrderInfoVo orderInfo(@NotBlank(message = "订单编号为空") String orderNo) {
        return payOrderQueryService.getOrderDetail(orderNo);
    }

    @GetMapping("/list")
    public Page<AdmOrderInfoVo> orderList(@Validated AdmOrderQuery query) {
        return payOrderQueryService.getAdmOrderList(query);
    }

    @GetMapping("/metrics")
    public OrderMetricsVo orderMetrics() {
        return payOrderQueryService.orderMetrics();
    }

    @GetMapping("/trends")
    public List<OrderMetricsTrendVo> metricsTrend() {
        return payOrderQueryService.orderTrends();
    }

    @GetMapping("/chart")
    public CensusLineChartVo orderChart(@NotNull(message = "统计天数为空")
                                        @Enumerable(ranges = {"10", "15", "20", "25", "30"}) Integer day) {
        return payOrderQueryService.orderCensus(day);
    }

}
