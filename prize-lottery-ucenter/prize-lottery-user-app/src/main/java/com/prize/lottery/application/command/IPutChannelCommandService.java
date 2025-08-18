package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.PutChannelCreateCmd;
import com.prize.lottery.application.command.dto.PutChannelEditCmd;
import com.prize.lottery.application.command.dto.PutRecordCreateCmd;
import com.prize.lottery.application.command.dto.PutRecordEditCmd;

public interface IPutChannelCommandService {

    void createChannel(PutChannelCreateCmd cmd);

    void editChannel(PutChannelEditCmd command);

    void createRecord(PutRecordCreateCmd command);

    void editRecord(PutRecordEditCmd command);
}
