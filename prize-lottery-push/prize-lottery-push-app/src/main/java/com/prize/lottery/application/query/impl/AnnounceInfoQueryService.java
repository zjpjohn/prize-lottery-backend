package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IAnnounceInfoQueryService;
import com.prize.lottery.application.query.dto.AnnounceAdmQuery;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.executor.AnnounceMessageExecutor;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.infrast.persist.mapper.AnnounceInfoMapper;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnounceInfoQueryService implements IAnnounceInfoQueryService {

    private final AnnounceMessageExecutor announceMessageExecutor;
    private final AnnounceInfoMapper      announceInfoMapper;

    @Override
    public Page<MessageInfoVo> announceAppQuery(MessageAppQuery query) {
        return announceMessageExecutor.execute(query);
    }

    @Override
    public Page<AnnounceInfoPo> announceAdmQuery(AnnounceAdmQuery query) {
        return query.from().count(announceInfoMapper::countAnnounces).query(announceInfoMapper::getAnnounceList);
    }

}
