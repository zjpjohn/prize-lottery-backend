package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.vo.UserInvMasterVo;
import com.prize.lottery.application.query.vo.UserInviteVo;
import com.prize.lottery.dto.UserAccountRepo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.*;
import com.prize.lottery.infrast.persist.vo.ActiveUserVo;
import com.prize.lottery.infrast.persist.vo.UserAdmInfoVo;
import com.prize.lottery.infrast.persist.vo.UserAgentApplyVo;
import com.prize.lottery.infrast.persist.vo.UserMemberVo;
import com.prize.lottery.infrast.props.CloudLotteryProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoQueryService implements IUserInfoQueryService {

    private final CloudLotteryProperties properties;
    private final UserInfoMapper         userInfoMapper;
    private final UserInviteMapper       userInviteMapper;
    private final UserInfoAssembler      userInfoAssembler;

    @Override
    public Page<UserSignLogPo> getUserSignLogs(SignLogQuery query) {
        return query.from().count(userInfoMapper::countUserSignLogs).query(userInfoMapper::getUserSignLogs);
    }

    @Override
    public UserInfoPo getUserInfo(Long userId) {
        return userInfoMapper.getUserInfoById(userId).orElseThrow(ResponseHandler.USER_INFO_NONE);
    }

    @Override
    public UserInfoPo getUserInfo(String phone) {
        return userInfoMapper.getUserInfoByPhone(phone);
    }

    @Override
    public UserInvMasterVo getInviteMaster(Long userId) {
        UserInviteLogPo inviteLog = userInviteMapper.getInviteLogByUserId(userId);
        return Optional.ofNullable(inviteLog).map(log -> {
            UserInvitePo invite = userInviteMapper.getUserInvite(inviteLog.getInvUid());
            return new UserInvMasterVo(log.getInvUid(), invite.getCode(), log.getInvAgent(), invite.getAgent());
        }).flatMap(master -> userInfoMapper.getUserInfoById(master.getUserId()).map(user -> {
            master.setNickName(user.getNickname());
            master.setPhone(user.getPhone());
            return master;
        })).orElse(null);
    }

    @Override
    public List<UserInfoPo> getUserList(List<Long> idList) {
        return userInfoMapper.getUserInfoByIdList(idList);
    }

    @Override
    public UserAccountRepo getUserAccount(Long userId) {
        return userInfoMapper.getUserAccount(userId);
    }

    @Override
    public List<UserAccountRepo> getUserAccounts(List<Long> userList) {
        return userInfoMapper.getUserAccounts(userList);
    }

    @Override
    public UserAdmInfoVo getAdmUserInfo(Long userId) {
        return userInfoMapper.getUserAdmInfo(userId);
    }

    @Override
    public Page<UserInfoPo> getUserInfoList(UserInfoQuery query) {
        return query.from().count(userInfoMapper::countUserInfo).query(userInfoMapper::getUserInfoList);
    }

    @Override
    public UserInviteVo getUserInviteInfo(Long userId) {
        UserInvitePo invite = userInviteMapper.getUserInvite(userId);
        Assert.notNull(invite, ResponseHandler.USER_INVITE_NONE);

        //流量主是否正在申请
        int applying = userInviteMapper.hasApplyingAgentApply(userId);

        return userInfoAssembler.toVo(invite, properties.getAgent(), applying);
    }

    @Override
    public Page<UserInfoPo> getInvitedUserList(UserInviteQuery query) {
        return query.from().count(userInfoMapper::countInviteUsers).query(userInfoMapper::getInviteUserList);

    }

    @Override
    public UserBalancePo getUserBalanceInfo(Long userId) {
        return userInfoMapper.getUserBalance(userId);
    }

    @Override
    public Page<UserBalanceLogPo> getUserBalanceLogs(PageCondition condition) {
        return condition.count(userInfoMapper::countUserBalanceLogs).query(userInfoMapper::getUserBalanceLogs);
    }

    @Override
    public Page<UserAgentApplyVo> getUserAgentApplyList(AgentApplyQuery query) {
        return query.from().count(userInviteMapper::countAgentApplies).query(userInviteMapper::getAgentApplyList);
    }

    @Override
    public List<AgentApplyPo> getAgentApplies(Long userId) {
        return userInviteMapper.getUserAgentApplies(userId);
    }

    @Override
    public AgentApplyPo getApplyingAgentApply(Long userId) {
        return userInviteMapper.getApplyingAgentApply(userId);
    }

    @Override
    public Page<ActiveUserVo> getActiveUsers(ActiveUserQuery query) {
        return query.from().count(userInfoMapper::countActiveUsers).query(userInfoMapper::getActiveUserList);
    }

    @Override
    public Page<UserMemberLogPo> getMemberLogList(MemberLogQuery query) {
        return query.from().count(userInfoMapper::countUserMemberLogs).query(userInfoMapper::getUserMemberLogs);
    }

    @Override
    public Optional<UserMemberPo> getUserMember(Long userId) {
        return userInfoMapper.getUserMember(userId);
    }

    @Override
    public UserMemberVo getUserMemberVo(Long userId) {
        return userInfoMapper.getUserMemberVo(userId).orElseThrow(ResponseHandler.USER_MEMBER_NONE);
    }

    @Override
    public Page<UserMemberVo> getUserMemberList(UserMemberQuery query) {
        return query.from().count(userInfoMapper::countUserMembers).query(userInfoMapper::getUserMemberList);
    }

}
