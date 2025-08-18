package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.RequestUtils;
import lombok.Getter;


@Getter
public class LauncherCreateCmd {

    private final String launchIp;
    private final Long   userId;
    private final String version;

    public LauncherCreateCmd(Long userId, String version) {
        this.launchIp = RequestUtils.ipAddress();
        this.userId   = userId;
        this.version  = version;
    }
}
