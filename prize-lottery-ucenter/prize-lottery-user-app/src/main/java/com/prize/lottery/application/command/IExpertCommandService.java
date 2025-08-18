package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ExpertCreateCmd;
import com.prize.lottery.application.command.dto.ExpertResetPwdCmd;
import com.prize.lottery.application.command.vo.ExpertAccountVo;

public interface IExpertCommandService {

    void createExpertAcct(ExpertCreateCmd command);

    void resetExpertAcctPwd(ExpertResetPwdCmd command);

    ExpertAccountVo getExpertAcctDetail(Long userId);

}
