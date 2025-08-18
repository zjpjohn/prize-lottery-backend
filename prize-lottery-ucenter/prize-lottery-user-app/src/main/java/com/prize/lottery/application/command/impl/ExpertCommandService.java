package com.prize.lottery.application.command.impl;

import com.prize.lottery.application.command.IExpertCommandService;
import com.prize.lottery.application.command.dto.ExpertCreateCmd;
import com.prize.lottery.application.command.dto.ExpertResetPwdCmd;
import com.prize.lottery.application.command.executor.ExpertAcctDetailExecutor;
import com.prize.lottery.application.command.executor.ExpertCreateExecutor;
import com.prize.lottery.application.command.executor.ExpertResetPwdExecutor;
import com.prize.lottery.application.command.vo.ExpertAccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpertCommandService implements IExpertCommandService {

    private final ExpertResetPwdExecutor   expertResetPwdExe;
    private final ExpertAcctDetailExecutor expertAcctDetailExe;
    private final ExpertCreateExecutor     expertCreateExecutor;

    @Override
    public void createExpertAcct(ExpertCreateCmd command) {
        expertCreateExecutor.execute(command);
    }

    @Override
    public void resetExpertAcctPwd(ExpertResetPwdCmd command) {
        expertResetPwdExe.execute(command.validate());
    }

    @Override
    public ExpertAccountVo getExpertAcctDetail(Long userId) {
        return expertAcctDetailExe.execute(userId);
    }
}
