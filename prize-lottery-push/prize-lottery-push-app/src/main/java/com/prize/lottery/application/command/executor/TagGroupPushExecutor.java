package com.prize.lottery.application.command.executor;

import com.prize.lottery.domain.notify.repository.INotifyInfoRepository;
import com.prize.lottery.infrast.external.push.TagBasedPushFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagGroupPushExecutor {

    private final INotifyInfoRepository repository;
    private final TagBasedPushFacade    pushFacade;

    public void execute() {
    }
}
