package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.FeedbackCreateCmd;
import com.prize.lottery.application.command.dto.FeedbackHandleCmd;

public interface IFeedbackCommandService {

    void createFeedback(FeedbackCreateCmd command);

    void handleFeedback(FeedbackHandleCmd command);

}
