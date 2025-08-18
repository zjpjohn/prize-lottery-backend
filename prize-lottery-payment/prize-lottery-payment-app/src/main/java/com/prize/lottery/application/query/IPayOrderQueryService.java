package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AdmOrderQuery;
import com.prize.lottery.application.query.dto.AppOrderQuery;
import com.prize.lottery.application.query.vo.AdmOrderInfoVo;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.OrderMetricsTrendVo;
import com.prize.lottery.application.query.vo.PayTotalMetricsVo;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import com.prize.lottery.infrast.persist.vo.OrderMetricsVo;

import java.util.List;

public interface IPayOrderQueryService {

    Page<AdmOrderInfoVo> getAdmOrderList(AdmOrderQuery query);

    Page<OrderInfoPo> getAppOrderList(AppOrderQuery query);

    AdmOrderInfoVo getOrderDetail(String orderNo);

    OrderInfoPo getUserOrderInfo(String orderNo, Long userId);

    String getWaitPayOrder(Integer type);

    OrderMetricsVo orderMetrics();

    CensusLineChartVo orderCensus(Integer day);

    List<OrderMetricsTrendVo> orderTrends();

    PayTotalMetricsVo payTotalMetrics(Integer days);

}
