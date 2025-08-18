package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IRemindInfoQueryService;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.dto.RemindAdmQuery;
import com.prize.lottery.application.query.executor.RemindMessageExecutor;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.infrast.persist.mapper.RemindInfoMapper;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemindInfoQueryService implements IRemindInfoQueryService {

    private final RemindMessageExecutor remindMessageExecutor;
    private final RemindInfoMapper      remindInfoMapper;

    @Override
    public Page<MessageInfoVo> remindAppQuery(MessageAppQuery query) {
        return remindMessageExecutor.execute(query);
    }

    @Override
    public Page<RemindInfoPo> remindAdmQuery(RemindAdmQuery query) {
        return query.from().count(remindInfoMapper::countReminds).query(remindInfoMapper::getRemindList);
    }

}
