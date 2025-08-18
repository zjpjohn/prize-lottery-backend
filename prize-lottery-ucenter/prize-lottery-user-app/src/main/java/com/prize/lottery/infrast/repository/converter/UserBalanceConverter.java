package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.infrast.persist.po.UserBalancePo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserBalanceConverter {

    UserBalance toDo(UserBalancePo balance);

    UserBalancePo toPo(UserBalance balance);

}
