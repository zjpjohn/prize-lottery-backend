package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.MetricsDevicePo;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;
import com.prize.lottery.infrast.persist.vo.DeviceMetricsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotifyAppMapper {

    int addNotifyApp(NotifyAppPo notifyApp);

    int editNotifyApp(NotifyAppPo notifyApp);

    NotifyAppPo getNotifyApp(Long id);

    NotifyAppPo getNotifyAppByKey(Long appKey);

    NotifyAppPo getNotifyAppByNo(String appNo);

    List<NotifyAppPo> getNotifyAppList();

    int addNotifyDevice(NotifyDevicePo device);

    int editNotifyDevice(NotifyDevicePo device);

    NotifyDevicePo getDeviceById(Long id);

    NotifyDevicePo getDeviceByDeviceId(String deviceId);

    List<NotifyDevicePo> getNotifyDeviceList(PageCondition condition);

    List<NotifyDevicePo> getWithoutBindDevices(@Param("appKey") Long appKey,
                                               @Param("groupId") Long groupId,
                                               @Param("limit") Integer limit);

    int countNotifyDevices(PageCondition condition);

    int addDeviceMetrics(List<MetricsDevicePo> list);

    List<MetricsDevicePo> getMetricsDevices(@Param("appKey") Long appKey,
                                            @Param("startDay") LocalDate startDay,
                                            @Param("endDay") LocalDate endDay);

    DeviceMetricsVo getDeviceMetricsVo(Long appKey);

    DeviceMetricsVo getDeviceMetricsCnt(Long appKey);

}
