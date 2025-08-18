package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AssistantCreateCmd;
import com.prize.lottery.application.command.dto.AssistantModifyCmd;

public interface IAppAssistCommandService {

    void createAssistant(AssistantCreateCmd command);

    void modifyAssistant(AssistantModifyCmd command);

    void sortAssistant(Long id, Integer position);

}
