package com.prize.lottery.application.command.service;


import com.prize.lottery.application.command.dto.BatchSubscribeCmd;
import com.prize.lottery.application.command.dto.MasterEnableCmd;
import com.prize.lottery.application.command.dto.SubscribeMasterCmd;

public interface IMasterCommandService {

    void enableMasterLotto(MasterEnableCmd command);

    void subscribeMaster(SubscribeMasterCmd command);

    void traceSubscribeMaster(SubscribeMasterCmd command);

    void specialOrCancelMaster(SubscribeMasterCmd command);

    void batchSubscribeMasters(BatchSubscribeCmd command);

    void unsubscribeMaster(SubscribeMasterCmd command);

    void removeMasterBattle(Long id);

    void focusMaster(Long userId, String masterId);

    void unFocusMaster(Long userId, String masterId);
}
