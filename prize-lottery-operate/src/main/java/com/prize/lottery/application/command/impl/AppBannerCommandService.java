package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.command.IAppBannerCommandService;
import com.prize.lottery.application.command.dto.BannerCreateCmd;
import com.prize.lottery.application.command.dto.BannerModifyCmd;
import com.prize.lottery.domain.app.model.AppBannerDo;
import com.prize.lottery.domain.app.repository.IAppBannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppBannerCommandService implements IAppBannerCommandService {

    private final IAppBannerRepository repository;

    @Override
    public void createBanner(BannerCreateCmd cmd) {
        AppBannerDo bannerDo = new AppBannerDo(cmd);
        AggregateFactory.create(bannerDo).save(repository::save);
    }

    @Override
    public void editBanner(BannerModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> root.modify(command)).save(repository::save);
    }

}
