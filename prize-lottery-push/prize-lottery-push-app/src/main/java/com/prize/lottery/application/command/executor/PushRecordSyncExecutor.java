package com.prize.lottery.application.command.executor;

import com.prize.lottery.infrast.external.push.NotifyPushQueryFacade;
import com.prize.lottery.infrast.external.push.request.NotifyQueryDto;
import com.prize.lottery.infrast.external.push.response.NotifyPushRecord;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.mapper.NotifyInfoMapper;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import com.prize.lottery.infrast.persist.po.NotifyTaskPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushRecordSyncExecutor {

    private final NotifyAppMapper       appMapper;
    private final NotifyInfoMapper      notifyMapper;
    private final NotifyPushQueryFacade queryFacade;

    public void execute() {
        List<NotifyAppPo> appList   = appMapper.getNotifyAppList();
        LocalDateTime     endTime   = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime     startTime = endTime.minusDays(1);
        for (NotifyAppPo app : appList) {
            NotifyQueryDto         queryDto = new NotifyQueryDto(app.getAppKey(), startTime, endTime);
            List<NotifyPushRecord> records  = queryFacade.execute(queryDto);
            if (CollectionUtils.isEmpty(records)) {
                continue;
            }
            List<NotifyTaskPo> tasks = records.stream().map(r -> {
                NotifyTaskPo task = new NotifyTaskPo();
                task.setState(r.getState());
                task.setPushTime(r.getPushTime());
                task.setMessageId(r.getMessageId());
                return task;
            }).collect(Collectors.toList());
            notifyMapper.editNotifyTasks(tasks);
        }
    }
}
