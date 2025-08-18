package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.PayChannelCreateCmd;
import com.prize.lottery.application.command.dto.PayChannelModifyCmd;

public interface IPayChannelCommandService {

    void createChannel(PayChannelCreateCmd command);

    void modifyChannel(PayChannelModifyCmd command);

}
