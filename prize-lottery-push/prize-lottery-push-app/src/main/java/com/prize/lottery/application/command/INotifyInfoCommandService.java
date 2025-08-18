package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.NotifyInfoCreateCmd;
import com.prize.lottery.application.command.dto.NotifyInfoModifyCmd;

public interface INotifyInfoCommandService {

    void createNotifyInfo(NotifyInfoCreateCmd command);

    void modifyNotifyInfo(NotifyInfoModifyCmd command);

}
