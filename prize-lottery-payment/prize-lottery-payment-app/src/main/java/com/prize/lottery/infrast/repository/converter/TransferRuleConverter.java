package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.transfer.model.aggregate.TransferRuleDo;
import com.prize.lottery.infrast.persist.po.TransferRulePo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferRuleConverter {

    TransferRuleDo toDo(TransferRulePo rule);

    TransferRulePo toPo(TransferRuleDo rule);

}
