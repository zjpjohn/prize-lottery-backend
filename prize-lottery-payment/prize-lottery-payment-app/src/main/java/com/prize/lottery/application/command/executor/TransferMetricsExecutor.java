package com.prize.lottery.application.command.executor;

import com.prize.lottery.infrast.persist.mapper.TransferRecordMapper;
import com.prize.lottery.infrast.persist.po.TransferStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferMetricsExecutor {

    private final TransferRecordMapper transferMapper;

    @Transactional
    public void execute(LocalDate metricDate) {
        TransferStatisticsPo statistics = transferMapper.getDateTransferStatistics(metricDate);
        if (statistics != null) {
            transferMapper.addTransferStatistics(statistics);
        }
    }
}
