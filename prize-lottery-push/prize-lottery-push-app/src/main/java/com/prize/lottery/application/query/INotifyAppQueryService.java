package com.prize.lottery.application.query;


import com.prize.lottery.infrast.persist.po.NotifyAppPo;

import java.util.List;

public interface INotifyAppQueryService {

    List<NotifyAppPo> getAppList();

}
