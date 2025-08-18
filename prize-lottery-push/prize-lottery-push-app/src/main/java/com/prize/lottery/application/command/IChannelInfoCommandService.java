package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ChannelCreateCmd;
import com.prize.lottery.application.command.dto.ChannelModifyCmd;
import com.prize.lottery.application.command.dto.MessageClearCmd;

public interface IChannelInfoCommandService {

    void createChannel(ChannelCreateCmd command);

    void editChannel(ChannelModifyCmd command);

    void clearMessage(MessageClearCmd command);

}
