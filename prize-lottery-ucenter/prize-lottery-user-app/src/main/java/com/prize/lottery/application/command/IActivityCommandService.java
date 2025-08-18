package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ActivityCreateCmd;
import com.prize.lottery.application.command.dto.ActivityEditCmd;
import com.prize.lottery.application.command.vo.ActivityJoinResult;
import com.prize.lottery.application.command.vo.DrawMemberResult;

public interface IActivityCommandService {

    void createActivity(ActivityCreateCmd command);

    void editActivity(ActivityEditCmd command);

    ActivityJoinResult joinActivity(Long userId);

    DrawMemberResult drawActivity(Long drawId, Long userId);

}
