package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IAppLaunchQueryService;
import com.prize.lottery.application.query.dto.AppLaunchQuery;
import com.prize.lottery.infrast.persist.mapper.AppLaunchMapper;
import com.prize.lottery.infrast.persist.po.AppDevicePo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppLaunchQueryService implements IAppLaunchQueryService {

    private final AppLaunchMapper appLaunchMapper;

    @Override
    public Page<AppLauncherPo> getUserAppLaunchList(AppLaunchQuery query) {
        return query.from().count(appLaunchMapper::countAppLaunchers).query(appLaunchMapper::getAppLaunchers);
    }

    @Override
    public AppDevicePo getAppDevice(String deviceId) {
        return appLaunchMapper.getAppDevice(deviceId);
    }

    @Override
    public List<AppDevicePo> getUserDevices(Long userId) {
        return appLaunchMapper.getUserDeviceList(userId);
    }

}
