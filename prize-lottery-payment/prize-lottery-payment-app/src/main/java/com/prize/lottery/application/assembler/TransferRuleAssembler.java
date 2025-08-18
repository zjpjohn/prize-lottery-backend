package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.TransRuleCreateCmd;
import com.prize.lottery.domain.transfer.model.aggregate.TransferRuleDo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransferRuleAssembler {

    void toDo(TransRuleCreateCmd command, @MappingTarget TransferRuleDo rule);

}
