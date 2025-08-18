package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AppConfQuery;
import com.prize.lottery.application.vo.AppConfVo;
import com.prize.lottery.infrast.persist.po.AppConfPo;

import java.util.List;


public interface IAppConfQueryService {

    Page<AppConfPo> getAppConfList(AppConfQuery query);

    AppConfPo getAppConf(Long id);

    List<AppConfVo> getReleasedConfList(String appNo);

}
