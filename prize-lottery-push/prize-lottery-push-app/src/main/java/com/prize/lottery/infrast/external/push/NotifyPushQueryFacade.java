package com.prize.lottery.infrast.external.push;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.push.model.v20160801.QueryPushRecordsRequest;
import com.aliyuncs.push.model.v20160801.QueryPushRecordsResponse;
import com.prize.lottery.infrast.external.push.request.NotifyQueryDto;
import com.prize.lottery.infrast.external.push.response.NotifyPushRecord;
import com.prize.lottery.infrast.persist.enums.PushState;
import com.prize.lottery.infrast.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyPushQueryFacade {

    private final IAcsClient client;

    public List<NotifyPushRecord> execute(NotifyQueryDto data) {
        try {
            QueryPushRecordsRequest request = new QueryPushRecordsRequest();
            request.setAppKey(data.appKey());
            request.setStartTime(DateUtils.formatUtc(data.startTime()));
            request.setEndTime(DateUtils.formatUtc(data.endTime()));
            QueryPushRecordsResponse                response  = client.getAcsResponse(request);
            List<QueryPushRecordsResponse.PushInfo> pushInfos = response.getPushInfos();
            if (CollectionUtils.isEmpty(pushInfos)) {
                return Collections.emptyList();
            }
            return pushInfos.stream().map(p -> {
                NotifyPushRecord record = new NotifyPushRecord();
                record.setAppKey(p.getAppKey());
                record.setMessageId(p.getMessageId());
                record.setState(PushState.of(p.getStatus()));
                record.setPushTime(DateUtils.parseUtc(p.getPushTime()));
                return record;
            }).collect(Collectors.toList());
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return Collections.emptyList();
    }

}
