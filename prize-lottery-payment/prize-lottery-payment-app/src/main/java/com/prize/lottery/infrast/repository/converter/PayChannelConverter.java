package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;
import com.prize.lottery.infrast.persist.po.PayChannelPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayChannelConverter {

    PayChannelPo toPo(PayChannelDo channel);

    PayChannelDo toDo(PayChannelPo channel);
}
