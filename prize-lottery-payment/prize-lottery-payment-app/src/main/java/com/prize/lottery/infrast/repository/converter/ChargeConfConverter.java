package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.order.model.aggregate.ChargeConfDo;
import com.prize.lottery.infrast.persist.po.ChargeConfPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChargeConfConverter {

    ChargeConfPo toPo(ChargeConfDo config);

    ChargeConfDo toDo(ChargeConfPo config);

}
