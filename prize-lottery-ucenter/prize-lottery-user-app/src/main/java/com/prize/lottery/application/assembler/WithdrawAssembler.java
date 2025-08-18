package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.WithRuleCreateCmd;
import com.prize.lottery.application.command.dto.WithRuleEditCmd;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.application.query.vo.*;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.domain.withdraw.model.WithdrawRuleDo;
import com.prize.lottery.infrast.persist.po.AgentWithdrawPo;
import com.prize.lottery.infrast.persist.po.ExpertWithdrawPo;
import com.prize.lottery.infrast.persist.po.UserWithdrawPo;
import com.prize.lottery.infrast.persist.po.WithdrawRulePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WithdrawAssembler {

    void toDo(WithRuleCreateCmd command, @MappingTarget WithdrawRuleDo rule);

    void toDo(WithRuleEditCmd command, @MappingTarget WithdrawRuleDo rule);

    WithdrawRuleVo toVo(WithdrawRulePo rule);

    @Mapping(source = "amount", target = "withdraw")
    @Mapping(source = "amount", target = "money")
    void toDo(WithdrawDto withdrawDto, @MappingTarget UserWithdrawDo withdrawDo);

    UserWithdrawVo toVo(UserWithdrawPo withdraw);

    AgentWithdrawVo toVo(AgentWithdrawPo withdraw);

    ExpertWithdrawVo toVo(ExpertWithdrawPo withdraw);

    @Mapping(source = "seqNo", target = "bizNo")
    AppWithdrawVo toWithdrawVo(UserWithdrawPo withdraw);

    @Mapping(source = "seqNo", target = "bizNo")
    AppWithdrawVo toWithdrawVo(AgentWithdrawPo withdraw);

    @Mapping(source = "seqNo", target = "bizNo")
    AppWithdrawVo toWithdrawVo(ExpertWithdrawPo withdraw);

}
