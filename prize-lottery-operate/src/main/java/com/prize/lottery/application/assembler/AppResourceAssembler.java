package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.ResourceCreateCmd;
import com.prize.lottery.application.command.dto.ResourceEditCmd;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppResourceAssembler {

    AppResourceVal toVal(ResourceCreateCmd command);

    AppResourceVal toVal(ResourceEditCmd command);

}
