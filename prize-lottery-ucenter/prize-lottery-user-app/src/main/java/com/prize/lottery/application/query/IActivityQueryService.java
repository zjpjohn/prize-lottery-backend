package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.command.vo.ActivityChanceVo;
import com.prize.lottery.application.query.dto.ActivityDrawQuery;
import com.prize.lottery.application.query.dto.ActivityListQuery;
import com.prize.lottery.application.query.dto.UserDrawQuery;
import com.prize.lottery.application.query.vo.ActivityDrawVo;
import com.prize.lottery.infrast.persist.po.ActivityMemberPo;
import com.prize.lottery.infrast.persist.vo.UserDrawResultVo;

import java.util.List;

public interface IActivityQueryService {

    Page<ActivityMemberPo> getActivityList(ActivityListQuery query);

    ActivityMemberPo getActivity(Long activityId);

    Page<ActivityDrawVo> getActivityDrawList(ActivityDrawQuery query);

    ActivityDrawVo getActivityDraw(Long drawId);

    List<ActivityChanceVo> getDrawChances(Long drawId);

    Page<UserDrawResultVo> getUserDrawResults(UserDrawQuery query);

}
