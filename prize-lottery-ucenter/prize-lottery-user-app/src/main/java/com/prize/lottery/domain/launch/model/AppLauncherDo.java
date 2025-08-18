package com.prize.lottery.domain.launch.model;

import com.prize.lottery.application.command.dto.AppLauncherCmd;
import com.prize.lottery.domain.launch.valobj.AppDevice;
import com.prize.lottery.domain.launch.valobj.AppLaunchLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AppLauncherDo {

    private Long         id;
    private String       deviceId;
    private LocalDate    launchDate;
    private Integer      launches;
    private AppDevice    device;
    private AppLaunchLog log;

    public AppLauncherDo(AppLauncherCmd command, String requestIp) {
        this.launches   = 1;
        this.deviceId   = command.getDeviceId();
        this.launchDate = LocalDate.now();
        this.device     = new AppDevice(command);
        this.log        = new AppLaunchLog(command, requestIp, launchDate);
    }

}
