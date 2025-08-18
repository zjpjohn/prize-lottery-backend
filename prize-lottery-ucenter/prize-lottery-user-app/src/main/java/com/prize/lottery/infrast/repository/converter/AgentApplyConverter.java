package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.agent.model.AgentApplyDo;
import com.prize.lottery.infrast.persist.po.AgentApplyPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentApplyConverter {

    AgentApplyDo toDo(AgentApplyPo apply);

    AgentApplyPo toPo(AgentApplyDo apply);

}
