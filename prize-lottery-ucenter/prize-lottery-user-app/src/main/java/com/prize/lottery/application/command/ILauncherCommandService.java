package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AppLauncherCmd;

public interface ILauncherCommandService {

    void appLaunch(AppLauncherCmd command, String requestIp);

}
