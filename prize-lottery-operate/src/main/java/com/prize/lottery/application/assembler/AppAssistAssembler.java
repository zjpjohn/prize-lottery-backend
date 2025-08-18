package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.AssistantCreateCmd;
import com.prize.lottery.application.command.dto.AssistantModifyCmd;
import com.prize.lottery.application.vo.AppAssistantVo;
import com.prize.lottery.domain.app.model.AppAssistantDo;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface AppAssistAssembler {

    void toDo(AssistantCreateCmd command, @MappingTarget AppAssistantDo assistant);

    void toDo(AssistantModifyCmd command, @MappingTarget AppAssistantDo assistant);

    @Mapping(source = "content", target = "content", ignore = true)
    AppAssistantVo toNoContentVo(AppAssistantPo assistant);

    AppAssistantVo toVo(AppAssistantPo assistant);

}
