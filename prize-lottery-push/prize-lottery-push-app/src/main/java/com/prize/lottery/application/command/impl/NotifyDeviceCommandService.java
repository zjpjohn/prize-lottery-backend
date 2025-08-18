package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.NotifyAppAssembler;
import com.prize.lottery.application.command.INotifyDeviceCommandService;
import com.prize.lottery.application.command.dto.DeviceCreateCmd;
import com.prize.lottery.domain.notify.model.NotifyDeviceDo;
import com.prize.lottery.domain.notify.repository.INotifyAppRepository;
import com.prize.lottery.domain.notify.repository.INotifyDeviceRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyDeviceCommandService implements INotifyDeviceCommandService {

    private final NotifyAppAssembler      assembler;
    private final INotifyAppRepository    notifyAppRepository;
    private final INotifyDeviceRepository notifyDeviceRepository;

    @Override
    public void createDevice(DeviceCreateCmd command) {
        Long appKey = notifyAppRepository.appKey(command.getAppNo());
        Assert.notNull(appKey, ResponseErrorHandler.INVALID_APP_NO);

        NotifyDeviceDo deviceDo = new NotifyDeviceDo(appKey, command, assembler::toDo);
        AggregateFactory.create(deviceDo).save(notifyDeviceRepository::save);
    }

}
