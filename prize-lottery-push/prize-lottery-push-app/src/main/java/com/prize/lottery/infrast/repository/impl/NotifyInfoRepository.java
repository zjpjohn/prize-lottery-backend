package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.notify.model.NotifyInfoDo;
import com.prize.lottery.domain.notify.repository.INotifyInfoRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.mapper.NotifyInfoMapper;
import com.prize.lottery.infrast.persist.po.NotifyInfoPo;
import com.prize.lottery.infrast.repository.converter.NotifyInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NotifyInfoRepository implements INotifyInfoRepository {

    private final NotifyInfoMapper    mapper;
    private final NotifyInfoConverter converter;


    @Override
    public void save(Aggregate<Long, NotifyInfoDo> aggregate) {
        NotifyInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            //创建推送信息
            NotifyInfoPo notifyInfo = converter.toPo(root);
            mapper.addNotifyInfo(notifyInfo);
            return;
        }
        //更新推送信息
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editNotifyInfo);
        //保存推送任务信息
        Optional.ofNullable(root.getNotifyTask()).map(converter::toPo).ifPresent(mapper::addNotifyTask);

    }

    @Override
    public Aggregate<Long, NotifyInfoDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getNotifyInfo(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseErrorHandler.NOTIFY_INFO_NONE);
    }

    @Override
    public List<Long> notifyIdList(Long appKey) {
        return mapper.getUsingNotifyList(appKey).stream().map(NotifyInfoPo::getId).collect(Collectors.toList());
    }

}
