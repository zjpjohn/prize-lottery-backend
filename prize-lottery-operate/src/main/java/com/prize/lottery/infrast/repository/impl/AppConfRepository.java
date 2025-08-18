package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.cache.support.CacheEvictEvent;
import com.cloud.arch.cache.support.CacheEvictPublisher;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.AppConfDo;
import com.prize.lottery.domain.app.repository.IAppConfRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppConfPo;
import com.prize.lottery.infrast.repository.converter.AppInfoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AppConfRepository implements IAppConfRepository {

    public static final String APP_CONF_CACHE_NAME = "app:conf";

    private final AppInfoMapper    mapper;
    private final AppInfoConverter converter;

    @Override
    public void save(Aggregate<Long, AppConfDo> aggregate) {
        AppConfDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppConfPo appConfPo = converter.toPo(root);
            int       result    = mapper.addAppConf(appConfPo);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        AppConfDo changed = aggregate.changed();
        if (changed != null) {
            AppConfPo appConfPo = converter.toPo(changed);
            mapper.editAppConf(appConfPo);
            //发布配置或者配置下线，清除缓存
            ConfState changedState = changed.getState();
            if (changedState == null) {
                return;
            }
            AppConfDo snapshot = aggregate.getSnapshot();
            if (changedState == ConfState.USING || (snapshot.getState() == ConfState.USING)) {
                CacheEvictEvent evictEvent = new CacheEvictEvent(APP_CONF_CACHE_NAME, root.getAppNo(), false);
                CacheEvictPublisher.publish(evictEvent);
            }
        }
    }

    @Override
    public Aggregate<Long, AppConfDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getAppConfById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.APP_CONF_NONE);
    }
}
