package com.prize.lottery.application.command.executor;

import com.prize.lottery.infrast.persist.mapper.OrderInfoMapper;
import com.prize.lottery.infrast.persist.po.OrderStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMetricsExecutor {

    private final OrderInfoMapper orderMapper;

    @Transactional
    public void execute(LocalDate metricDate) {
        OrderStatisticsPo statistics = orderMapper.getDateOrderStatistics(metricDate);
        if (statistics != null) {
            orderMapper.addOrderStatistics(statistics);
        }
    }
}
