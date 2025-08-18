package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.INotifyInfoQueryService;
import com.prize.lottery.application.query.dto.NotifyInfoQuery;
import com.prize.lottery.infrast.persist.mapper.NotifyInfoMapper;
import com.prize.lottery.infrast.persist.po.NotifyInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyInfoQueryService implements INotifyInfoQueryService {

    private final NotifyInfoMapper notifyInfoMapper;

    @Override
    public NotifyInfoPo getNotifyInfo(Long id) {
        return notifyInfoMapper.getNotifyInfo(id);
    }

    @Override
    public Page<NotifyInfoPo> getNotifyInfoList(NotifyInfoQuery query) {
        return query.from().count(notifyInfoMapper::countNotifyInfos).query(notifyInfoMapper::getNotifyInfoList);
    }

}
