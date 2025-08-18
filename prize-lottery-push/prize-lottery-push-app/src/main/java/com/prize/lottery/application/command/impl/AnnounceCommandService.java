package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IAnnounceCommandService;
import com.prize.lottery.application.command.dto.AnnounceCreateCmd;
import com.prize.lottery.application.command.dto.AnnounceModifyCmd;
import com.prize.lottery.domain.message.model.AnnounceInfoDo;
import com.prize.lottery.domain.message.model.ChannelInfoDo;
import com.prize.lottery.domain.message.repository.IAnnounceRepository;
import com.prize.lottery.domain.message.repository.IChannelInfoRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnounceCommandService implements IAnnounceCommandService {

    private final IChannelInfoRepository channelRepository;
    private final IAnnounceRepository    announceRepository;

    @Override
    @Transactional
    public void createAnnounce(AnnounceCreateCmd command) {
        ChannelInfoDo channelInfo = channelRepository.ofUk(command.getChannel());
        Assert.state(channelInfo.isAnnounce(), ResponseErrorHandler.CHANNEL_NO_PRIVILEGE);

        AnnounceInfoDo announce = new AnnounceInfoDo(command);
        AggregateFactory.create(announce).save(announceRepository::save);
    }

    @Override
    @Transactional
    public void editAnnounce(AnnounceModifyCmd command) {
        announceRepository.of(command.getId()).peek(root -> root.modify(command)).save(announceRepository::save);
    }

}
