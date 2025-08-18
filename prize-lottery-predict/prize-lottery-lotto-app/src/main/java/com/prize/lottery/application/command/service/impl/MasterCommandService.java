package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.command.dto.BatchSubscribeCmd;
import com.prize.lottery.application.command.dto.MasterEnableCmd;
import com.prize.lottery.application.command.dto.SubscribeMasterCmd;
import com.prize.lottery.application.command.executor.master.FollowMasterActionExe;
import com.prize.lottery.application.command.executor.master.MasterEnableLottoExe;
import com.prize.lottery.application.command.service.IMasterCommandService;
import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.domain.master.repository.IMasterBattleRepository;
import com.prize.lottery.domain.user.model.UserFocus;
import com.prize.lottery.domain.user.repository.IUserFocusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MasterCommandService implements IMasterCommandService {

    @Resource
    private MasterEnableLottoExe    masterEnableLottoExe;
    @Resource
    private FollowMasterActionExe   followMasterActionExe;
    @Resource
    private IMasterBattleRepository battleRepository;
    @Resource
    private IUserFocusRepository    focusRepository;

    @Override
    public void enableMasterLotto(MasterEnableCmd command) {
        masterEnableLottoExe.execute(command);
    }

    @Override
    @Transactional
    public void subscribeMaster(SubscribeMasterCmd command) {
        followMasterActionExe.subscribe(command);
    }

    @Override
    @Transactional
    public void traceSubscribeMaster(SubscribeMasterCmd command) {
        followMasterActionExe.traceMaster(command);
    }

    @Override
    @Transactional
    public void specialOrCancelMaster(SubscribeMasterCmd command) {
        followMasterActionExe.specialOrCancel(command);
    }

    @Override
    @Transactional
    public void batchSubscribeMasters(BatchSubscribeCmd command) {
        followMasterActionExe.batchSubscribe(command.toCommands());
    }

    @Override
    @Transactional
    public void unsubscribeMaster(SubscribeMasterCmd command) {
        followMasterActionExe.unsubscribe(command);
    }

    @Override
    public void removeMasterBattle(Long id) {
        MasterBattle masterBattle = battleRepository.ofId(id);
        battleRepository.save(masterBattle.remove());
    }

    @Override
    public void focusMaster(Long userId, String masterId) {
        UserFocus userFocus = new UserFocus(userId, masterId).focus();
        focusRepository.save(userFocus);
    }

    @Override
    public void unFocusMaster(Long userId, String masterId) {
        UserFocus userFocus = focusRepository.ofUk(userId, masterId).unFocus();
        focusRepository.remove(userFocus);
    }
}
