package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.query.INotifyAppQueryService;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyAppQueryService implements INotifyAppQueryService {

    private final NotifyAppMapper notifyAppMapper;

    @Override
    public List<NotifyAppPo> getAppList() {
        return notifyAppMapper.getNotifyAppList();
    }

}
