package com.prize.lottery.application.schedule;

import com.prize.lottery.application.command.executor.PushRecordSyncExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushRecordSyncScheduler {

    private final PushRecordSyncExecutor recordSyncExecutor;

    public void schedule() {
        recordSyncExecutor.execute();
    }

}
