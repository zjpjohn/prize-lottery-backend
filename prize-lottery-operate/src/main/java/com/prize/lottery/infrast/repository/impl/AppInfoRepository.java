package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.cache.support.CacheEvictEvent;
import com.cloud.arch.cache.support.CacheEvictPublisher;
import com.prize.lottery.domain.app.model.AppInfoDo;
import com.prize.lottery.domain.app.repository.IAppInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.repository.converter.AppInfoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppInfoRepository implements IAppInfoRepository {

    public static final String H5_APP_CACHE_NAME     = "h5:mobile:app";
    public static final String MOBILE_APP_CACHE_NAME = "native:mobile:app";

    private final AppInfoMapper    mapper;
    private final AppInfoConverter converter;


    @Override
    public void save(Aggregate<Long, AppInfoDo> aggregate) {
        AppInfoDo appInfoDo = aggregate.getRoot();
        if (appInfoDo.isNew()) {
            AppInfoPo appInfoPo = converter.toPo(appInfoDo);
            mapper.addAppInfo(appInfoPo);
            return;
        }
        AppInfoDo changed = aggregate.changed();
        if (changed != null) {
            AppInfoPo appInfoPo = converter.toPo(changed);
            mapper.editAppInfo(appInfoPo);
            //应用更新抛出缓存失效事件
            CacheEvictEvent h5EvictEvent  = new CacheEvictEvent(H5_APP_CACHE_NAME, appInfoDo.getSeqNo());
            CacheEvictEvent appEvictEvent = new CacheEvictEvent(MOBILE_APP_CACHE_NAME, null);
            CacheEvictPublisher.publish(h5EvictEvent, appEvictEvent);
        }
    }

    @Override
    public Aggregate<Long, AppInfoDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getAppInfoById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.APP_NOT_EXIST);
    }

    @Override
    public boolean existApp(String name) {
        return mapper.hasExistAppName(name) == 0;
    }

    @Override
    public boolean existApp(Long appId) {
        return mapper.getAppInfoById(appId) != null;
    }

    @Override
    public boolean existAppNo(String appNo) {
        return mapper.getAppInfo(appNo) != null;
    }

    @Override
    public void issueAppMainVersion(String appNo, String mainVersion) {
        mapper.issueAppMainVersion(appNo, mainVersion);
        CacheEvictEvent h5EvictEvent  = new CacheEvictEvent(H5_APP_CACHE_NAME, appNo);
        CacheEvictEvent appEvictEvent = new CacheEvictEvent(MOBILE_APP_CACHE_NAME, null);
        CacheEvictPublisher.publish(h5EvictEvent, appEvictEvent);
    }

}
