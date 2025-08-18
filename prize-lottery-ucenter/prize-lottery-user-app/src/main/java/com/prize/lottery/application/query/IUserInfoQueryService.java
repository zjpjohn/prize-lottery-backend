package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.vo.UserInvMasterVo;
import com.prize.lottery.application.query.vo.UserInviteVo;
import com.prize.lottery.dto.UserAccountRepo;
import com.prize.lottery.infrast.persist.po.*;
import com.prize.lottery.infrast.persist.vo.ActiveUserVo;
import com.prize.lottery.infrast.persist.vo.UserAdmInfoVo;
import com.prize.lottery.infrast.persist.vo.UserAgentApplyVo;
import com.prize.lottery.infrast.persist.vo.UserMemberVo;

import java.util.List;
import java.util.Optional;

public interface IUserInfoQueryService {

    Page<UserSignLogPo> getUserSignLogs(SignLogQuery query);

    UserInfoPo getUserInfo(Long userId);

    UserInfoPo getUserInfo(String phone);

    UserInvMasterVo getInviteMaster(Long userId);

    List<UserInfoPo> getUserList(List<Long> idList);

    UserAccountRepo getUserAccount(Long userId);

    List<UserAccountRepo> getUserAccounts(List<Long> userList);

    UserAdmInfoVo getAdmUserInfo(Long userId);

    Page<UserInfoPo> getUserInfoList(UserInfoQuery query);

    UserInviteVo getUserInviteInfo(Long userId);

    Page<UserInfoPo> getInvitedUserList(UserInviteQuery query);

    UserBalancePo getUserBalanceInfo(Long userId);

    Page<UserBalanceLogPo> getUserBalanceLogs(PageCondition condition);

    Page<UserAgentApplyVo> getUserAgentApplyList(AgentApplyQuery query);

    List<AgentApplyPo> getAgentApplies(Long userId);

    AgentApplyPo getApplyingAgentApply(Long userId);

    Page<ActiveUserVo> getActiveUsers(ActiveUserQuery query);

    Page<UserMemberLogPo> getMemberLogList(MemberLogQuery query);

    Optional<UserMemberPo> getUserMember(Long userId);

    UserMemberVo getUserMemberVo(Long userId);

    Page<UserMemberVo> getUserMemberList(UserMemberQuery query);

}
