package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.notify.model.NotifyDeviceDo;
import com.prize.lottery.domain.notify.repository.INotifyDeviceRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;
import com.prize.lottery.infrast.repository.converter.NotifyDeviceConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NotifyDeviceRepository implements INotifyDeviceRepository {

    private final NotifyAppMapper       mapper;
    private final NotifyDeviceConverter converter;


    @Override
    public void save(Aggregate<Long, NotifyDeviceDo> aggregate) {
        NotifyDeviceDo root = aggregate.getRoot();
        if (root.isNew()) {
            NotifyDevicePo device = converter.toPo(root);
            mapper.addNotifyDevice(device);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editNotifyDevice);
    }

    @Override
    public Aggregate<Long, NotifyDeviceDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getDeviceById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseErrorHandler.NOTIFY_DEVICE_NONE);
    }

    @Override
    public Optional<Aggregate<Long, NotifyDeviceDo>> ofDevice(String deviceId) {
        return Optional.ofNullable(mapper.getDeviceByDeviceId(deviceId))
                       .map(converter::toDo)
                       .map(AggregateFactory::create);
    }

}
