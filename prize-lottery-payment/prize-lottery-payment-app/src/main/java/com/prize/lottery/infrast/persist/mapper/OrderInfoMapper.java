package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.dto.MetricsTimeDto;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import com.prize.lottery.infrast.persist.po.OrderStatisticsPo;
import com.prize.lottery.infrast.persist.vo.OrderMetricsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderInfoMapper {

    int addOrderInfo(OrderInfoPo order);

    int editOrderInfo(OrderInfoPo order);

    OrderInfoPo getOrderInfoByNo(String bizNo);

    Optional<OrderInfoPo> getWaitPayOrder(Integer type);

    List<OrderInfoPo> getExpiredWaitOrders();

    List<OrderInfoPo> getOrderInfoList(PageCondition condition);

    int countOrderInfos(PageCondition condition);

    int addOrderStatistics(OrderStatisticsPo statistics);

    OrderMetricsVo getOrderMetricsVo(MetricsTimeDto time);

    List<OrderStatisticsPo> getLatestOrderMetrics(Integer limit);

    List<OrderStatisticsPo> getStatisticsList(@Param("startDay") LocalDate startDay, @Param("endDay") LocalDate endDay);

    OrderStatisticsPo getDateOrderStatistics(@Param("metricDate") LocalDate metricDate);

    OrderStatisticsPo getOrderSumMetrics(@Param("startDay") LocalDate startDay, @Param("endDay") LocalDate endDay);

}
