package com.prize.lottery.application.command.impl;

import com.prize.lottery.application.command.ILauncherCommandService;
import com.prize.lottery.application.command.dto.AppLauncherCmd;
import com.prize.lottery.domain.launch.model.AppLauncherDo;
import com.prize.lottery.domain.launch.repository.IAppLauncherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LauncherCommandService implements ILauncherCommandService {

    private final IAppLauncherRepository appLauncherRepository;

    @Async
    @Override
    @Transactional
    public void appLaunch(AppLauncherCmd command, String requestIp) {
        AppLauncherDo appLauncher = new AppLauncherDo(command, requestIp);
        appLauncherRepository.save(appLauncher);
    }

}
