package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AnnounceCreateCmd;
import com.prize.lottery.application.command.dto.AnnounceModifyCmd;

public interface IAnnounceCommandService {

    void createAnnounce(AnnounceCreateCmd command);

    void editAnnounce(AnnounceModifyCmd command);

}
