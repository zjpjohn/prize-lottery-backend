package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.TagGroupCreateCmd;
import com.prize.lottery.application.command.dto.TagGroupModifyCmd;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotifyTagAssembler {

    void toDo(TagGroupCreateCmd command, @MappingTarget NotifyTagGroupDo tagGroup);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "state", target = "state", ignore = true)
    void toDo(TagGroupModifyCmd command, @MappingTarget NotifyTagGroupDo tagGroup);
}
