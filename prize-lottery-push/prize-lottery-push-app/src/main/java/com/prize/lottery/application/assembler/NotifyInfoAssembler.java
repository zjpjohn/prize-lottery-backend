package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.NotifyInfoCreateCmd;
import com.prize.lottery.application.command.dto.NotifyInfoModifyCmd;
import com.prize.lottery.domain.notify.model.NotifyInfoDo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotifyInfoAssembler {

    void toDo(NotifyInfoCreateCmd command, @MappingTarget NotifyInfoDo notifyInfo);

    void toDo(NotifyInfoModifyCmd command, @MappingTarget NotifyInfoDo notifyInfo);

}
