package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.UserAccountRepo;
import com.prize.lottery.infrast.persist.po.*;
import com.prize.lottery.infrast.persist.vo.ActiveUserVo;
import com.prize.lottery.infrast.persist.vo.MemberTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.UserAdmInfoVo;
import com.prize.lottery.infrast.persist.vo.UserMemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserInfoMapper {

    int saveUserInfo(UserInfoPo userInfo);

    int editUserInfo(UserInfoPo userInfo);

    UserInfoPo getUserInfoByName(String name);

    UserInfoPo getUserInfoByPhone(String phone);

    Optional<UserInfoPo> getUserInfoById(Long userId);

    List<UserInfoPo> getUserInfoByIdList(List<Long> idList);

    UserAccountRepo getUserAccount(Long id);

    List<UserAccountRepo> getUserAccounts(List<Long> idList);

    UserAdmInfoVo getUserAdmInfo(Long id);

    List<UserInfoPo> getUserInfoList(PageCondition condition);

    int countUserInfo(PageCondition condition);

    List<UserInfoPo> getInviteUserList(PageCondition condition);

    int countInviteUsers(PageCondition condition);

    int addUserBalance(UserBalancePo balance);

    int editUserBalance(UserBalancePo balance);

    int editBalanceList(List<UserBalancePo> balances);

    UserBalancePo getUserBalance(Long userId);

    List<UserBalancePo> getBalanceByUsers(@Param("userIds") List<Long> userIds);

    int addUserBalanceLog(UserBalanceLogPo balanceLog);

    int addBalanceLogList(List<UserBalanceLogPo> logList);

    UserBalanceLogPo getLatestBalanceLog(Long userId);

    List<UserBalanceLogPo> getUserBalanceLogs(PageCondition condition);

    int countUserBalanceLogs(PageCondition condition);

    int saveUserSign(UserSignPo userSign);

    UserSignPo getUserSign(Long userId);

    int addUserSignLog(UserSignLogPo signLog);

    UserSignLogPo getLatestUserSignLog(Long userId);

    List<UserSignLogPo> getUserSignLogs(PageCondition condition);

    int countUserSignLogs(PageCondition condition);

    int addUserLogin(UserLoginPo userLogin);

    UserLoginPo getUserLogin(Long userId);

    int addUserLoginLog(UserLoginLogPo loginLog);

    int addUserMember(UserMemberPo member);

    int editUserMember(UserMemberPo member);

    int expireUserMembers(List<UserMemberPo> members);

    Optional<UserMemberPo> getUserMember(Long userId);

    Optional<UserMemberVo> getUserMemberVo(Long userId);

    List<UserMemberPo> getExpiredMembers(Integer limit);

    List<UserMemberVo> getUserMemberList(PageCondition condition);

    int countUserMembers(PageCondition condition);

    int addUserMemberLog(UserMemberLogPo memberLog);

    List<UserMemberLogPo> getUserMemberLogs(PageCondition condition);

    int countUserMemberLogs(PageCondition condition);

    MemberTotalMetricsVo getTotalMemberMetrics(@Param("startDay") LocalDate startDay,
                                               @Param("endDay") LocalDate endDay);

    List<ActiveUserVo> getActiveUserList(PageCondition condition);

    int countActiveUsers(PageCondition condition);

}
