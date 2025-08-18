package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.AvatarCreateCmd;
import com.prize.lottery.po.master.AvatarInfoPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarInfoAssembler {

    AvatarInfoPo toPo(AvatarCreateCmd avatar);
}
