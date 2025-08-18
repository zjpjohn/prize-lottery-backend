package com.prize.lottery.application.command.executor;

import com.prize.lottery.infrast.persist.mapper.PackInfoMapper;
import com.prize.lottery.infrast.persist.po.PackStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PackMetricsExecutor {

    private final PackInfoMapper packMapper;

    @Transactional
    public void execute(LocalDate metricDate) {
        List<PackStatisticsPo> statisticsList = packMapper.getDayPackStatistics(metricDate);
        if (!CollectionUtils.isEmpty(statisticsList)) {
            packMapper.addPackStatistics(statisticsList);
        }
    }

}
