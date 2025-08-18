package com.prize.lottery.domain.avatar.repository;


import com.prize.lottery.domain.avatar.model.AvatarInfo;

public interface IAvatarInfoRepository {

    void saveAvatar(AvatarInfo avatar);

    AvatarInfo getAvatarInfo(Long id);

    void removeAvatar(Long id);
}
