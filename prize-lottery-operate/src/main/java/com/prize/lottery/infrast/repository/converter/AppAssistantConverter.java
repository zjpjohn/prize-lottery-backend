package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.app.model.AppAssistantDo;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppAssistantConverter {

    AppAssistantDo toDo(AppAssistantPo assistant);

    AppAssistantPo toPo(AppAssistantDo assistant);

}
