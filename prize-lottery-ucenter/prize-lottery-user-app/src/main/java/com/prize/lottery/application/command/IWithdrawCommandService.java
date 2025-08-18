package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.*;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.infrast.persist.enums.TransferScene;

public interface IWithdrawCommandService {

    void createRule(WithRuleCreateCmd command);

    void modifyRule(WithRuleEditCmd command);

    void createWithdrawLevel(WithLevelCreateCmd command);

    void editWithdrawLevel(WithLevelEditCmd command);

    void payWithdraw(WithdrawCreateCmd command, TransferScene scene);

    void withdrawRollback(TransCallbackEvent event);

}
