package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderInfoConverter {

    OrderInfoPo toPo(OrderInfoDo orderInfo);

    OrderInfoDo toDo(OrderInfoPo orderInfo);

    List<OrderInfoDo> toList(List<OrderInfoPo> orders);

}
