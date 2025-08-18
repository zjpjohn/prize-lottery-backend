package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.AppResourceDo;
import com.prize.lottery.domain.app.repository.IAppResourceRepository;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppResourceMapper;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import com.prize.lottery.infrast.repository.converter.AppResourceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppResourceRepository implements IAppResourceRepository {

    private final AppResourceMapper    mapper;
    private final AppResourceConverter converter;

    @Override
    public void save(Aggregate<Long, AppResourceDo> aggregate) {
        AppResourceDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppResourcePo appResource = converter.toPo(root, root.getResource());
            int           result      = mapper.addAppResource(appResource);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        Integer result = aggregate.ifChanged()
                                  .map(changed -> converter.toPo(changed, changed.getResource()))
                                  .map(mapper::editAppResource)
                                  .orElse(0);
        Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
    }

    @Override
    public void batch(List<AppResourceVal> resources) {
        List<AppResourcePo> resourceList = converter.toList(resources);
        mapper.addResourceList(resourceList);
    }

    @Override
    public Aggregate<Long, AppResourceDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getAppResourceById(id, true)).map(po -> {
            AppResourceVal resource = converter.toVal(po);
            return converter.toDo(po.getId(), po.getState(), po.getLastUri(), resource);
        }).map(AggregateFactory::create).orElseThrow(Assert.supply(ResponseHandler.APP_RESOURCE_NONE));
    }

    @Override
    public boolean existResourceNo(String appNo, String feNo) {
        return mapper.existResourceFeNo(appNo, feNo) != 0;
    }

    @Override
    public List<AppResourcePo> getUsingAppResources(String appNo) {
        return mapper.getUsingAppResources(appNo);
    }

}
