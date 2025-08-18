package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.application.query.IResourceQueryService;
import com.prize.lottery.application.query.dto.AdmResourceQuery;
import com.prize.lottery.application.vo.ResourceExportVo;
import com.prize.lottery.domain.app.ability.AppResourceDomainService;
import com.prize.lottery.infrast.persist.mapper.AppResourceMapper;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import com.prize.lottery.infrast.persist.valobj.AppResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceQueryService implements IResourceQueryService {

    private final AppResourceDomainService appResourceDomainService;
    private final AppResourceMapper        appResourceMapper;

    @Override
    public Page<AppResourcePo> getAppResourceList(AdmResourceQuery query) {
        return query.from().count(appResourceMapper::countAppResources).query(appResourceMapper::getAppResourceList);
    }

    @Override
    public AppResourcePo getAppResourceDetail(Long id) {
        return appResourceMapper.getAppResourceById(id, true);
    }

    @Override
    public Map<String, AppResource> getAppResources(String appNo) {
        return appResourceDomainService.getAppResources(appNo);
    }

    @Override
    public List<ResourceExportVo> exportAppResource(String appNo) {
        List<AppResourcePo> resources = appResourceMapper.getUsingAppResources(appNo);
        if (CollectionUtils.isEmpty(resources)) {
            return Collections.emptyList();
        }
        return resources.stream().map(ResourceExportVo::new).collect(Collectors.toList());
    }

}
