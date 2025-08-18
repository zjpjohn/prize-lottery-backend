package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.DeviceCreateCmd;

public interface INotifyDeviceCommandService {

    void createDevice(DeviceCreateCmd command);

}
