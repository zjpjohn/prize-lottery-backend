package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.NotifyAppCreateCmd;
import com.prize.lottery.application.command.dto.NotifyAppModifyCmd;

public interface INotifyAppCommandService {

    void createNotifyApp(NotifyAppCreateCmd command);

    void modifyNotifyApp(NotifyAppModifyCmd command);

}
