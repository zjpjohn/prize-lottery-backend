package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.avatar.model.AvatarInfo;
import com.prize.lottery.po.master.AvatarInfoPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarInfoConverter {

    AvatarInfo toDo(AvatarInfoPo avatar);

    AvatarInfoPo toPo(AvatarInfo avatar);
}
