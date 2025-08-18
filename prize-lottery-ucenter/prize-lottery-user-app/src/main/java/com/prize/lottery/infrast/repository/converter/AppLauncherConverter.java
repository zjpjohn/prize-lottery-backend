package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.launch.model.AppLauncherDo;
import com.prize.lottery.domain.launch.valobj.AppDevice;
import com.prize.lottery.domain.launch.valobj.AppLaunchLog;
import com.prize.lottery.infrast.persist.po.AppDevicePo;
import com.prize.lottery.infrast.persist.po.AppLauncherLogPo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppLauncherConverter {

    AppDevicePo toPo(AppDevice device);

    AppLauncherPo toPo(AppLauncherDo launcher);

    AppLauncherLogPo toPo(AppLaunchLog launchLog);

}
