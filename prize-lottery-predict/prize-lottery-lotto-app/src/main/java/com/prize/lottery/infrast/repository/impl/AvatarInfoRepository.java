package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.avatar.model.AvatarInfo;
import com.prize.lottery.domain.avatar.repository.IAvatarInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.AvatarInfoConverter;
import com.prize.lottery.mapper.AvatarInfoMapper;
import com.prize.lottery.po.master.AvatarInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarInfoRepository implements IAvatarInfoRepository {

    private final AvatarInfoMapper    avatarInfoMapper;
    private final AvatarInfoConverter avatarInfoConverter;

    @Override
    public void saveAvatar(AvatarInfo avatar) {
        AvatarInfoPo avatarInfo = avatarInfoConverter.toPo(avatar);
        if (avatarInfo.getId() != null) {
            avatarInfoMapper.editAvatarInfo(avatarInfo);
            return;
        }
        avatarInfoMapper.addAvatarList(Lists.newArrayList(avatarInfo));
    }

    @Override
    public AvatarInfo getAvatarInfo(Long id) {
        return Optional.ofNullable(avatarInfoMapper.getAvatarInfoById(id))
                       .map(avatarInfoConverter::toDo)
                       .orElseThrow(ResponseHandler.AVATAR_NOT_EXISTED);
    }

    @Override
    public void removeAvatar(Long id) {
        avatarInfoMapper.removeAvatarInfo(id);
    }
}
