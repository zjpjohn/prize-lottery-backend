package com.prize.lottery.domain.launch.valobj;

import com.prize.lottery.application.command.dto.AppLauncherCmd;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppLaunchLog {

    private String    deviceId;
    private LocalDate launchDate;
    private String    launchIp;
    private LocalTime launchTime;
    private String    launchVersion;

    public AppLaunchLog(AppLauncherCmd command, String requestIp, LocalDate launchDate) {
        this.launchIp      = requestIp;
        this.deviceId      = command.getDeviceId();
        this.launchVersion = command.getVersion();
        this.launchDate    = launchDate;
        this.launchTime    = LocalTime.now();
    }

}
