package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.cache.support.CacheEvictEvent;
import com.cloud.arch.cache.support.CacheEvictPublisher;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.AppVerifyDo;
import com.prize.lottery.domain.app.repository.IAppVerifyRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.mapper.AppVerifyMapper;
import com.prize.lottery.infrast.persist.po.AppVerifyPo;
import com.prize.lottery.infrast.repository.converter.AppVerifyConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppVerifyRepository implements IAppVerifyRepository {

    public static final String APP_VERIFY_CACHE_NAME = "app:verify";

    private final AppVerifyMapper    mapper;
    private final AppVerifyConverter converter;

    @Override
    public void save(Aggregate<Long, AppVerifyDo> aggregate) {
        AppVerifyDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppVerifyPo appVerify = converter.toPo(root);
            int         result    = mapper.addAppVerify(appVerify);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        AppVerifyDo changed = aggregate.changed();
        if (changed == null) {
            return;
        }
        //更新配置信息
        AppVerifyPo appVerify = converter.toPo(changed);
        mapper.editAppVerify(appVerify);
        //发布上线或者下线，清除缓存
        CommonState state    = changed.getState();
        AppVerifyDo snapshot = aggregate.getSnapshot();
        if (state == null || (state != CommonState.USING && snapshot.getState() != CommonState.USING)) {
            return;
        }
        CacheEvictEvent evictEvent = new CacheEvictEvent(APP_VERIFY_CACHE_NAME, snapshot.getAppNo());
        CacheEvictPublisher.publish(evictEvent);
    }

    @Override
    public Aggregate<Long, AppVerifyDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getVerifyById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.APP_VERIFY_NONE);
    }

}
