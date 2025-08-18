package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.notify.model.NotifyAppDo;
import com.prize.lottery.domain.notify.repository.INotifyAppRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import com.prize.lottery.infrast.repository.converter.NotifyAppConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NotifyAppRepository implements INotifyAppRepository {

    private final NotifyAppMapper    mapper;
    private final NotifyAppConverter converter;

    @Override
    public void save(Aggregate<Long, NotifyAppDo> aggregate) {
        NotifyAppDo root = aggregate.getRoot();
        if (root.isNew()) {
            NotifyAppPo appPo  = converter.toPo(root);
            int         result = mapper.addNotifyApp(appPo);
            Assert.state(result > 0, ResponseErrorHandler.DATA_SAVE_ERROR);
            root.setId(appPo.getId());
            return;
        }
        NotifyAppDo changed = aggregate.changed();
        if (changed != null) {
            NotifyAppPo appPo  = converter.toPo(changed);
            int         result = mapper.editNotifyApp(appPo);
            Assert.state(result > 0, ResponseErrorHandler.DATA_SAVE_ERROR);
        }
    }

    @Override
    public Aggregate<Long, NotifyAppDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getNotifyApp(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseErrorHandler.NOTIFY_APP_NONE);
    }

    @Override
    public Long appKey(String appNo) {
        return Optional.ofNullable(mapper.getNotifyAppByNo(appNo)).map(NotifyAppPo::getAppKey).orElse(null);
    }

    @Override
    public boolean exist(Long appKey) {
        return mapper.getNotifyAppByKey(appKey) != null;
    }

    @Override
    public List<Long> ofAllKeys() {
        return mapper.getNotifyAppList().stream().map(NotifyAppPo::getAppKey).collect(Collectors.toList());
    }

}
