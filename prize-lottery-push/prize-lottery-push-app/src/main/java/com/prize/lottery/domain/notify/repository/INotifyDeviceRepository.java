package com.prize.lottery.domain.notify.repository;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.notify.model.NotifyDeviceDo;

import java.util.Optional;

public interface INotifyDeviceRepository {

    void save(Aggregate<Long, NotifyDeviceDo> aggregate);

    Aggregate<Long, NotifyDeviceDo> ofId(Long id);

    Optional<Aggregate<Long, NotifyDeviceDo>> ofDevice(String deviceId);

}
