package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.NotifyDeviceQuery;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;

public interface INotifyDeviceQueryService {

    Page<NotifyDevicePo> getNotifyDeviceList(NotifyDeviceQuery query);

}
