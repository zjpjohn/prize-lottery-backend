package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ChargeConfCreateCmd;
import com.prize.lottery.application.command.dto.ChargeConfEditCmd;

public interface IChargeConfCommandService {

    void createConf(ChargeConfCreateCmd command);

    void editConf(ChargeConfEditCmd command);

    void removeConf();

}
