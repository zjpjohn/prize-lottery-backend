package com.prize.lottery.domain.notify.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.application.command.dto.DeviceCreateCmd;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

@Data
public class NotifyDeviceDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 5909063687384059424L;

    private Long          id;
    private Long          appKey;
    private String        deviceId;
    private String        deviceType;
    private Integer       enable;
    private Long          userId;
    private String        phone;
    private LocalDateTime onlineTime;

    public NotifyDeviceDo(Long appKey, DeviceCreateCmd command, BiConsumer<DeviceCreateCmd, NotifyDeviceDo> consumer) {
        this.appKey     = appKey;
        this.onlineTime = LocalDateTime.now();
        consumer.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
