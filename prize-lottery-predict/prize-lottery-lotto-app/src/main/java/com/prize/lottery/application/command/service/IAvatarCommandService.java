package com.prize.lottery.application.command.service;


import com.prize.lottery.application.command.dto.AvatarCreateCmd;

public interface IAvatarCommandService {

    void createAvatar(AvatarCreateCmd command);

    void removeAvatar(Long avatarId);

}
