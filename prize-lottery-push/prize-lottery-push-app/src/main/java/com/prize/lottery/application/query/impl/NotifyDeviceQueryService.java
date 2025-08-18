package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.INotifyDeviceQueryService;
import com.prize.lottery.application.query.dto.NotifyDeviceQuery;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyDeviceQueryService implements INotifyDeviceQueryService {

    private final NotifyAppMapper notifyAppMapper;

    @Override
    public Page<NotifyDevicePo> getNotifyDeviceList(NotifyDeviceQuery query) {
        return query.from().count(notifyAppMapper::countNotifyDevices).query(notifyAppMapper::getNotifyDeviceList);
    }

}
