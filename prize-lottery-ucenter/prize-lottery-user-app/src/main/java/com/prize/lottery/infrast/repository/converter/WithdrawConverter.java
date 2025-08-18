package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.domain.withdraw.model.WithdrawLevelDo;
import com.prize.lottery.domain.withdraw.model.WithdrawRuleDo;
import com.prize.lottery.infrast.persist.po.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WithdrawConverter {

    WithdrawRuleDo toDo(WithdrawRulePo rule);

    WithdrawRulePo toPo(WithdrawRuleDo rule);

    UserWithdrawDo toDo(UserWithdrawPo withdraw);

    UserWithdrawPo toUser(UserWithdrawDo withdraw);

    AgentWithdrawPo toAgent(UserWithdrawDo withdraw);

    UserWithdrawDo toDo(AgentWithdrawPo withdraw);

    ExpertWithdrawPo toExpert(UserWithdrawDo withdraw);

    UserWithdrawDo toDo(ExpertWithdrawPo withdraw);

    WithdrawLevelPo toPo(WithdrawLevelDo level);

    WithdrawLevelDo toDo(WithdrawLevelPo level);

}
