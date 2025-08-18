package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AppLaunchQuery;
import com.prize.lottery.infrast.persist.po.AppDevicePo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;

import java.util.List;

public interface IAppLaunchQueryService {
    Page<AppLauncherPo> getUserAppLaunchList(AppLaunchQuery query);

    AppDevicePo getAppDevice(String deviceId);

    List<AppDevicePo> getUserDevices(Long userId);

}
