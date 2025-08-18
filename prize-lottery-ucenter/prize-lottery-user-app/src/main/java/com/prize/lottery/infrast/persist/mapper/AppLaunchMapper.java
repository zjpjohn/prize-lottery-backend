package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AppDevicePo;
import com.prize.lottery.infrast.persist.po.AppLauncherLogPo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;
import com.prize.lottery.infrast.persist.po.UserDevicePo;
import com.prize.lottery.infrast.persist.vo.UserActiveIndexVo;
import com.prize.lottery.infrast.persist.vo.UserLaunchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AppLaunchMapper {

    int addAppLauncher(AppLauncherPo launcher);

    AppLauncherPo getAppLauncherByUk(@Param("deviceId") String deviceId, @Param("launchDate") LocalDate launchDate);

    List<AppLauncherPo> getAppLaunchers(PageCondition condition);

    int countAppLaunchers(PageCondition condition);

    int addAppLauncherLog(AppLauncherLogPo launcherLog);

    List<AppLauncherLogPo> getDeviceLauncherLogs(@Param("deviceId") String deviceId,
                                                 @Param("launchDate") LocalDate launchDate);

    List<AppLauncherLogPo> getLatestUserLaunchLogs(List<Long> userIds);

    List<UserLaunchVo> getUserTotalLaunches(List<Long> usereIds);

    int addAppDevice(AppDevicePo device);

    int existAppDevice(String deviceId);

    AppDevicePo getAppDevice(String deviceId);

    List<AppDevicePo> getUserDeviceList(Long userId);

    int addUserDevice(UserDevicePo userDevice);

    int hasBindDevice(String deviceId);

    UserActiveIndexVo getUserActiveIndex(Long userId);

    List<UserActiveIndexVo> getActiveIndexByUsers(List<Long> userIds);

}
