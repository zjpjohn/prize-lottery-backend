package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AppAssistantQuery;
import com.prize.lottery.application.query.dto.AssistantListQuery;
import com.prize.lottery.application.vo.AppAssistantVo;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;

import java.util.List;

public interface IAppAssistQueryService {

    AssistantApp assistantApp(String appNo);

    AppAssistantPo assistant(Long id);

    AppAssistantVo assistant(Long id, String appNo);

    Page<AppAssistantPo> listQuery(AssistantListQuery query);

    List<AppAssistantVo> appAssistants(AppAssistantQuery query);

}
