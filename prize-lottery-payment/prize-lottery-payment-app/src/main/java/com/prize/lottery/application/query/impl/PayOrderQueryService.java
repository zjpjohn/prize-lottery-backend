package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.OrderInfoAssembler;
import com.prize.lottery.application.query.IPayOrderQueryService;
import com.prize.lottery.application.query.dto.AdmOrderQuery;
import com.prize.lottery.application.query.dto.AppOrderQuery;
import com.prize.lottery.application.query.executor.OrderLineChartExecutor;
import com.prize.lottery.application.query.executor.OrderMetricsTrendExecutor;
import com.prize.lottery.application.query.vo.AdmOrderInfoVo;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.OrderMetricsTrendVo;
import com.prize.lottery.application.query.vo.PayTotalMetricsVo;
import com.prize.lottery.domain.facade.IUserAccountFacade;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.dto.MetricsTimeDto;
import com.prize.lottery.infrast.persist.mapper.OrderInfoMapper;
import com.prize.lottery.infrast.persist.mapper.TransferRecordMapper;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import com.prize.lottery.infrast.persist.po.OrderStatisticsPo;
import com.prize.lottery.infrast.persist.po.TransferStatisticsPo;
import com.prize.lottery.infrast.persist.vo.OrderMetricsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayOrderQueryService implements IPayOrderQueryService {

    private final OrderInfoMapper           orderMapper;
    private final TransferRecordMapper      transferMapper;
    private final OrderInfoAssembler        orderAssembler;
    private final IUserAccountFacade        userAccountFacade;
    private final OrderMetricsTrendExecutor trendExecutor;
    private final OrderLineChartExecutor    chartExecutor;

    @Override
    public AdmOrderInfoVo getOrderDetail(String orderNo) {
        OrderInfoPo orderInfo = orderMapper.getOrderInfoByNo(orderNo);
        Assert.notNull(orderInfo, ResponseHandler.ORDER_NOT_EXIST);
        UserInfo userInfo = userAccountFacade.getUserInfo(orderInfo.getUserId());
        return orderAssembler.toVo(orderInfo, userInfo);
    }

    @Override
    public OrderInfoPo getUserOrderInfo(String orderNo, Long userId) {
        OrderInfoPo order = orderMapper.getOrderInfoByNo(orderNo);
        Assert.notNull(order, ResponseHandler.TRANSFER_RECORD_NONE);
        Assert.state(order.getUserId().equals(userId), ResponseHandler.ORDER_QUERY_FORBIDDEN);
        return order;
    }

    @Override
    public String getWaitPayOrder(Integer type) {
        return orderMapper.getWaitPayOrder(type).map(OrderInfoPo::getBizNo).orElse(null);
    }

    @Override
    public Page<OrderInfoPo> getAppOrderList(AppOrderQuery query) {
        return query.from().count(orderMapper::countOrderInfos).query(orderMapper::getOrderInfoList);
    }

    @Override
    public Page<AdmOrderInfoVo> getAdmOrderList(AdmOrderQuery query) {
        PageCondition condition = query.from();
        if (StringUtils.isNotBlank(query.getPhone())) {
            UserInfo userInfo = userAccountFacade.getUserInfo(query.getPhone());
            condition.setParam("userId", userInfo.getUserId());
            return condition.count(orderMapper::countOrderInfos)
                            .query(orderMapper::getOrderInfoList)
                            .map(order -> orderAssembler.toVo(order, userInfo));
        }
        return condition.count(orderMapper::countOrderInfos).query(orderMapper::getOrderInfoList).flatMap(orders -> {
            List<Long> userIds = orders.stream().map(OrderInfoPo::getUserId).distinct().collect(Collectors.toList());
            List<UserInfo>      userInfos = userAccountFacade.getUserList(userIds);
            Map<Long, UserInfo> userMap   = Maps.uniqueIndex(userInfos, UserInfo::getUserId);
            return orders.stream().map(order -> {
                UserInfo userInfo = userMap.get(order.getUserId());
                return orderAssembler.toVo(order, userInfo);
            }).collect(Collectors.toList());
        });
    }

    @Override
    public OrderMetricsVo orderMetrics() {
        MetricsTimeDto metricsTime = new MetricsTimeDto();
        return orderMapper.getOrderMetricsVo(metricsTime);
    }

    @Override
    public CensusLineChartVo orderCensus(Integer day) {
        return chartExecutor.execute(day);
    }

    @Override
    public List<OrderMetricsTrendVo> orderTrends() {
        return trendExecutor.execute();
    }

    @Override
    public PayTotalMetricsVo payTotalMetrics(Integer days) {
        LocalDate               endDay       = LocalDate.now();
        LocalDate               startDay     = endDay.minusDays(days);
        final PayTotalMetricsVo totalMetrics = new PayTotalMetricsVo();
        //订单总数统计信息
        PayTotalMetricsVo.TotalMetricsItem orderMetrics    = new PayTotalMetricsVo.TotalMetricsItem();
        OrderStatisticsPo                  orderStatistics = orderMapper.getOrderSumMetrics(startDay, endDay);
        totalMetrics.setOrder(orderMetrics);
        orderMetrics.setTotalAmt(orderStatistics.getSuccessAmt());
        orderMetrics.setTotalCnt(orderStatistics.getSuccessCnt());
        //提现总数统计信息
        PayTotalMetricsVo.TotalMetricsItem transferMetrics    = new PayTotalMetricsVo.TotalMetricsItem();
        TransferStatisticsPo               transferStatistics = transferMapper.getTransferSumStatistics(startDay, endDay);
        totalMetrics.setTransfer(transferMetrics);
        transferMetrics.setTotalAmt(transferStatistics.getSuccessAmt());
        transferMetrics.setTotalCnt(transferStatistics.getSuccessCnt());
        return totalMetrics;
    }

}
