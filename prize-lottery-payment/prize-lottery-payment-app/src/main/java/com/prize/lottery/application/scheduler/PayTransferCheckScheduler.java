package com.prize.lottery.application.scheduler;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.executor.transfer.TransCheckExecutor;
import com.prize.lottery.infrast.persist.mapper.TransferRecordMapper;
import com.prize.lottery.infrast.persist.po.TransferRecordPo;
import com.prize.lottery.pay.PayChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayTransferCheckScheduler {

    private final TransferRecordMapper                                      transferRecordMapper;
    private final EnumerableExecutorFactory<PayChannel, TransCheckExecutor> transCheckExecutorFactory;

    @Scheduled(initialDelay = 60, fixedRate = 600, timeUnit = TimeUnit.SECONDS)
    public void transferCheck() {
        try {
            LocalDateTime          time         = LocalDateTime.now().minusMinutes(5);
            List<TransferRecordPo> transferList = transferRecordMapper.getProcessingTransferList(time);
            if (!CollectionUtils.isEmpty(transferList)) {
                transferList.forEach(this::checkRecord);
            }
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
    }

    private void checkRecord(TransferRecordPo record) {
        PayChannel channel = record.getChannel();
        transCheckExecutorFactory.ofNullable(channel).ifPresent(executor -> {
            executor.checkTransResult(record.getTransNo(), record.getBatchNo());
        });
    }
}
