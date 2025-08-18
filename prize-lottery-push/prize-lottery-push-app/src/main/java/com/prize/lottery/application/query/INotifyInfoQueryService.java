package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.NotifyInfoQuery;
import com.prize.lottery.infrast.persist.po.NotifyInfoPo;

public interface INotifyInfoQueryService {

    NotifyInfoPo getNotifyInfo(Long id);

    Page<NotifyInfoPo> getNotifyInfoList(NotifyInfoQuery query);

}
