package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.vo.UserBalanceVo;
import com.prize.lottery.application.command.vo.UserLoginResult;
import com.prize.lottery.application.command.vo.UserSignResult;
import com.prize.lottery.application.command.vo.UserSignVo;
import com.prize.lottery.application.query.vo.*;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.model.UserSign;
import com.prize.lottery.domain.user.valobj.UserSignLog;
import com.prize.lottery.dto.ExpertAcctRepo;
import com.prize.lottery.dto.UserInfoRepo;
import com.prize.lottery.infrast.persist.po.*;
import com.prize.lottery.infrast.props.UserSignProperties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoAssembler {

    UserLoginResult toVo(UserInfo user, String token, Long expire);

    @Mapping(source = "sign.coupon", target = "current")
    @Mapping(source = "sign.series", target = "series", ignore = true)
    @Mapping(source = "props.every", target = "ecoupon")
    @Mapping(source = "props.series", target = "scoupon")
    UserSignVo toVo(UserSign sign, UserSignProperties props);

    @Mapping(source = "sign.coupon", target = "current")
    @Mapping(source = "sign.userId", target = "userId")
    @Mapping(source = "sign.series", target = "series", ignore = true)
    @Mapping(source = "props.every", target = "ecoupon")
    @Mapping(source = "props.series", target = "scoupon")
    UserSignResult toResult(UserSign sign, UserSignLog log, UserSignProperties props);

    UserBalanceVo toVo(UserBalance balance);

    UserInviteVo toVo(UserInvitePo invite, Integer partner, Integer applying);

    @Mapping(source = "userAmt", target = "users")
    AgentAccountVo toVo(UserInvitePo invite);

    UserAgentRuleVo toVo(AgentRulePo rule);

    AgentMetricsVo toVo(AgentMetricsPo metrics);

    AgentIncomeVo toVo(AgentIncomePo income);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "state.state", target = "state")
    UserInfoRepo toRepo(UserInfoPo userInfo);

    List<UserInfoRepo> toRepoList(List<UserInfoPo> userList);

    @Mapping(source = "state.state", target = "state")
    ExpertAcctRepo toRepo(ExpertAcctPo acct);

}
