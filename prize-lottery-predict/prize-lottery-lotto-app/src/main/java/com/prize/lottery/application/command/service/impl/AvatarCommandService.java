package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.command.dto.AvatarCreateCmd;
import com.prize.lottery.application.command.service.IAvatarCommandService;
import com.prize.lottery.domain.avatar.model.AvatarInfo;
import com.prize.lottery.domain.avatar.repository.IAvatarInfoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AvatarCommandService implements IAvatarCommandService {

    @Resource
    private IAvatarInfoRepository avatarInfoRepository;

    @Override
    public void createAvatar(AvatarCreateCmd command) {
        AvatarInfo avatarInfo = new AvatarInfo(command.getKey(), command.getUri());
        avatarInfoRepository.saveAvatar(avatarInfo);
    }

    @Override
    public void removeAvatar(Long avatarId) {
        avatarInfoRepository.removeAvatar(avatarId);
    }
}
