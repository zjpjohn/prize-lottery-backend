package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.application.query.IUserInviteQueryService;
import com.prize.lottery.application.query.dto.AgentInvitedQuery;
import com.prize.lottery.application.query.dto.AgentUserQuery;
import com.prize.lottery.infrast.persist.mapper.AppLaunchMapper;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AppLauncherLogPo;
import com.prize.lottery.infrast.persist.vo.AgentInvitedUserVo;
import com.prize.lottery.infrast.persist.vo.AgentUserInviteVo;
import com.prize.lottery.infrast.persist.vo.UserLaunchVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInviteQueryService implements IUserInviteQueryService {

    private final UserInviteMapper userInviteMapper;
    private final AppLaunchMapper  appLaunchMapper;
    private final UserInfoMapper   userInfoMapper;

    @Override
    public List<AgentUserInviteVo> topUserInvite(Integer topN) {
        return userInviteMapper.getAgentInvitesTopN(topN);
    }

    @Override
    public Page<AgentUserInviteVo> getAgentUserList(AgentUserQuery query) {
        return query.from().count(userInviteMapper::countAgentUsers).query(userInviteMapper::getAgentUserList);
    }

    @Override
    public AgentUserInviteVo getAgentUserDetail(Long userId) {
        return userInviteMapper.getAgentUserInfo(userId);
    }

    @Override
    public Page<AgentInvitedUserVo> getAgentInvitedList(AgentInvitedQuery query) {
        return query.from()
                    .count(userInviteMapper::countAgentInvitedUsers)
                    .query(userInviteMapper::getAgentInvitedUsers)
                    .ifPresent(records -> {
                        List<Long>               userIds       = CollectionUtils.toList(records, AgentInvitedUserVo::getUserId);
                        List<UserLaunchVo>       sumLaunches   = appLaunchMapper.getUserTotalLaunches(userIds);
                        Map<Long, Integer>       launchesMap   = CollectionUtils.toMap(sumLaunches, UserLaunchVo::getUserId, UserLaunchVo::getLaunches);
                        List<AppLauncherLogPo>   launchLogs    = appLaunchMapper.getLatestUserLaunchLogs(userIds);
                        Map<Long, LocalDateTime> launchLogsMap = CollectionUtils.toMap(launchLogs, AppLauncherLogPo::getUserId, AppLauncherLogPo::getGmtCreate);
                        records.forEach(record -> {
                            record.setLaunches(launchesMap.get(record.getUserId()));
                            record.setLatestLaunch(launchLogsMap.get(record.getUserId()));
                        });
                    });
    }

}
