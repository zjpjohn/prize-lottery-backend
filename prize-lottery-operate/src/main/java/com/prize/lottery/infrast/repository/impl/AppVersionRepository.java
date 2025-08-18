package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.cache.support.CacheEvictEvent;
import com.cloud.arch.cache.support.CacheEvictPublisher;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.AppVersionDo;
import com.prize.lottery.domain.app.repository.IAppVersionRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.repository.converter.AppInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.prize.lottery.infrast.repository.impl.AppInfoRepository.H5_APP_CACHE_NAME;
import static com.prize.lottery.infrast.repository.impl.AppInfoRepository.MOBILE_APP_CACHE_NAME;


@Slf4j
@Component
@RequiredArgsConstructor
public class AppVersionRepository implements IAppVersionRepository {

    private final AppInfoMapper    mapper;
    private final AppInfoConverter converter;

    @Override
    public void save(Aggregate<Long, AppVersionDo> aggregate) {
        AppVersionDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppVersionPo versionPo = converter.toPo(root);
            int          result    = mapper.addAppVersion(versionPo);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        AppVersionDo changed = aggregate.changed();
        if (changed != null) {
            AppVersionPo versionPo = converter.toPo(changed);
            mapper.editAppVersion(versionPo);
            //修改版本信息淘汰缓存
            CacheEvictEvent h5EvictEvent  = new CacheEvictEvent(H5_APP_CACHE_NAME, root.getAppNo());
            CacheEvictEvent appEvictEvent = new CacheEvictEvent(MOBILE_APP_CACHE_NAME, null);
            CacheEvictPublisher.publish(h5EvictEvent, appEvictEvent);
        }
    }

    @Override
    public Aggregate<Long, AppVersionDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getAppVersionById(id, false))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(Assert.supply(ResponseHandler.APP_VERSION_NOT_EXIST));
    }

}
