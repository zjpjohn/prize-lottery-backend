package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.AgentRuleCreateCmd;
import com.prize.lottery.application.command.dto.AgentRuleEditCmd;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AgentRuleAssembler {

    void toDo(AgentRuleCreateCmd command, @MappingTarget AgentRuleDo agentRule);

    @Mapping(source = "startTime", target = "startTime", ignore = true)
    void toDo(AgentRuleEditCmd command, @MappingTarget AgentRuleDo agentRule);

}
