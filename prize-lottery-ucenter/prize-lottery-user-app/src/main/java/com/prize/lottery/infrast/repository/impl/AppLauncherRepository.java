package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.launch.model.AppLauncherDo;
import com.prize.lottery.domain.launch.repository.IAppLauncherRepository;
import com.prize.lottery.infrast.persist.mapper.AppLaunchMapper;
import com.prize.lottery.infrast.persist.po.AppDevicePo;
import com.prize.lottery.infrast.persist.po.AppLauncherLogPo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;
import com.prize.lottery.infrast.repository.converter.AppLauncherConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppLauncherRepository implements IAppLauncherRepository {

    private final AppLaunchMapper      mapper;
    private final AppLauncherConverter converter;

    @Override
    public void save(AppLauncherDo launcher) {
        //应用启动信息
        AppLauncherPo appLauncher = converter.toPo(launcher);
        mapper.addAppLauncher(appLauncher);
        //应用启动日志
        AppLauncherLogPo launcherLog = converter.toPo(launcher.getLog());
        mapper.addAppLauncherLog(launcherLog);
        //应用设备信息
        AppDevicePo appDevice = converter.toPo(launcher.getDevice());
        mapper.addAppDevice(appDevice);
    }

}
