package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.TransRuleCreateCmd;
import com.prize.lottery.application.command.dto.TransRuleModifyCmd;

public interface ITransferRuleCommandService {

    void createRule(TransRuleCreateCmd command);

    void modifyRule(TransRuleModifyCmd command);

}
