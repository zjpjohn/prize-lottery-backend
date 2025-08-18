package com.prize.lottery.domain.launch.valobj;

import com.prize.lottery.application.command.dto.AppLauncherCmd;
import lombok.Data;

@Data
public class AppDevice {
    private Long    id;
    private String  deviceId;
    private String  brand;
    private String  manufacturer;
    private Integer sdkInt;
    private String  release;
    private String  hardware;
    private String  fromCode;

    public AppDevice(AppLauncherCmd command) {
        this.deviceId     = command.getDeviceId();
        this.brand        = command.getBrand();
        this.manufacturer = command.getManufacturer();
        this.sdkInt       = command.getSdkInt();
        this.release      = command.getRelease();
        this.hardware     = command.getHardware();
        this.fromCode     = command.getFromCode();
    }

}
