package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ConfCreateCmd;
import com.prize.lottery.application.command.dto.ConfModifyCmd;

public interface IAppConfCommandService {

    void createAppConf(ConfCreateCmd command);

    void modifyAppConf(ConfModifyCmd command);

}
