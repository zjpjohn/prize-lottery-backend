package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AdmResourceQuery;
import com.prize.lottery.application.vo.ResourceExportVo;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import com.prize.lottery.infrast.persist.valobj.AppResource;

import java.util.List;
import java.util.Map;

public interface IResourceQueryService {

    Page<AppResourcePo> getAppResourceList(AdmResourceQuery query);

    AppResourcePo getAppResourceDetail(Long id);

    Map<String, AppResource> getAppResources(String appNo);


    List<ResourceExportVo> exportAppResource(String appNo);
}
