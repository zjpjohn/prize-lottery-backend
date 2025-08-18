package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AppCommentBatchCmd;
import com.prize.lottery.application.command.dto.AppCommentCreateCmd;
import com.prize.lottery.application.command.dto.AppCommentEditCmd;

public interface IAppCommentCommandService {

    void batchComments(AppCommentBatchCmd command);

    void addAppComment(AppCommentCreateCmd command);

    void editAppComment(AppCommentEditCmd command);

}
