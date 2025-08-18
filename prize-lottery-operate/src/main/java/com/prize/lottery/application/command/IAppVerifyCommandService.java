package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AppVerifyCreateCmd;
import com.prize.lottery.application.command.dto.AppVerifyModifyCmd;

public interface IAppVerifyCommandService {

    void createVerify(AppVerifyCreateCmd command);

    void modifyVerify(AppVerifyModifyCmd command);

}
