package com.prize.lottery.application.assembler;

import com.prize.lottery.application.query.vo.AdmOrderInfoVo;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderInfoAssembler {

    @Mapping(source = "order.state", target = "state")
    @Mapping(source = "user.userId", target = "userId", ignore = true)
    AdmOrderInfoVo toVo(OrderInfoPo order, UserInfo user);
}
