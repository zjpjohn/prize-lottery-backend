package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.po.ActivityDrawPo;
import com.prize.lottery.infrast.persist.po.ActivityMemberPo;
import com.prize.lottery.infrast.persist.po.ActivityUserPo;
import com.prize.lottery.infrast.persist.vo.UserDrawResultVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ActivityInfoMapper {

    int addActivityInfo(ActivityMemberPo activity);

    int editActivityInfo(ActivityMemberPo activity);

    ActivityMemberPo getActivityInfo(Long id);

    Optional<ActivityMemberPo> getUsingActivity();

    List<ActivityMemberPo> getActivityList(PageCondition condition);

    int countActivities(PageCondition condition);

    int addActivityDraw(ActivityDrawPo draw);

    int editActivityDraw(ActivityDrawPo draw);

    ActivityDrawPo getActivityDraw(Long id);

    ActivityDrawPo getActivityUserDraw(@Param("userId") Long userId, @Param("day") LocalDate day);

    List<ActivityDrawPo> getActivityDrawList(PageCondition condition);

    int countActivityDraws(PageCondition condition);

    int addActivityUser(ActivityUserPo user);

    int editActivityUser(ActivityUserPo user);

    ActivityUserPo getActivityUser(@Param("activityId") Long activityId, @Param("userId") Long userId);

    int addDrawChances(List<ActivityChancePo> chances);

    int editDrawChances(List<ActivityChancePo> chances);

    List<ActivityChancePo> getDrawChances(Long drawId);

    List<ActivityChancePo> getDrawChancesByDrawIds(List<Long> drawIds);

    List<UserDrawResultVo> getUserDrawResults(PageCondition condition);

    int countUserDrawResults(PageCondition condition);

}
