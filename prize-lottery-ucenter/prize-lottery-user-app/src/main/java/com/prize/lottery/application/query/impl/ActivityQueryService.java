package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ActivityAssembler;
import com.prize.lottery.application.command.vo.ActivityChanceVo;
import com.prize.lottery.application.query.IActivityQueryService;
import com.prize.lottery.application.query.dto.ActivityDrawQuery;
import com.prize.lottery.application.query.dto.ActivityListQuery;
import com.prize.lottery.application.query.dto.UserDrawQuery;
import com.prize.lottery.application.query.vo.ActivityDrawVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.ActivityInfoMapper;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.po.ActivityDrawPo;
import com.prize.lottery.infrast.persist.po.ActivityMemberPo;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import com.prize.lottery.infrast.persist.vo.UserDrawResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityQueryService implements IActivityQueryService {

    private final UserInfoMapper     userMapper;
    private final ActivityInfoMapper activityMapper;
    private final ActivityAssembler  activityAssembler;

    @Override
    public Page<ActivityMemberPo> getActivityList(ActivityListQuery query) {
        return query.from().count(activityMapper::countActivities).query(activityMapper::getActivityList);
    }

    @Override
    public ActivityMemberPo getActivity(Long activityId) {
        return activityMapper.getActivityInfo(activityId);
    }

    @Override
    public Page<ActivityDrawVo> getActivityDrawList(ActivityDrawQuery query) {
        return query.from()
                    .count(activityMapper::countActivityDraws)
                    .query(activityMapper::getActivityDrawList)
                    .flatMap(list -> {
                        List<Long>            userIds = CollectionUtils.distinctList(list, ActivityDrawPo::getUserId);
                        List<UserInfoPo>      users   = userMapper.getUserInfoByIdList(userIds);
                        Map<Long, UserInfoPo> map     = CollectionUtils.toMap(users, UserInfoPo::getId);
                        List<ActivityDrawVo>  voList  = activityAssembler.toVoList(list);
                        voList.forEach(vo -> {
                            UserInfoPo user = map.get(vo.getUserId());
                            vo.setPhone(user.getPhone());
                            vo.setNickname(user.getNickname());
                        });
                        return voList;
                    });
    }

    @Override
    public ActivityDrawVo getActivityDraw(Long drawId) {
        //抽奖信息
        ActivityDrawPo activityDraw = activityMapper.getActivityDraw(drawId);
        Assert.notNull(activityDraw, ResponseHandler.DRAW_RESULT_NONE);
        ActivityDrawVo drawVo = activityAssembler.toVo(activityDraw);

        //抽奖机会集合
        List<ActivityChancePo> chances = activityMapper.getDrawChances(drawId);
        drawVo.setChances(chances);

        //抽奖用户信息
        userMapper.getUserInfoById(drawVo.getUserId()).ifPresent(user -> {
            drawVo.setPhone(user.getPhone());
            drawVo.setNickname(user.getNickname());
        });
        return drawVo;
    }

    @Override
    public List<ActivityChanceVo> getDrawChances(Long drawId) {
        List<ActivityChancePo> chances = activityMapper.getDrawChances(drawId);
        return activityAssembler.fromPoList(chances);
    }

    @Override
    public Page<UserDrawResultVo> getUserDrawResults(UserDrawQuery query) {
        return query.from()
                    .count(activityMapper::countUserDrawResults)
                    .query(activityMapper::getUserDrawResults)
                    .ifPresent(list -> {
                        List<Long>                        drawIds = CollectionUtils.distinctList(list, UserDrawResultVo::getDrawId);
                        List<ActivityChancePo>            chances = activityMapper.getDrawChancesByDrawIds(drawIds);
                        Map<Long, List<ActivityChancePo>> grouped = CollectionUtils.groupBy(chances, ActivityChancePo::getDrawId);
                        list.forEach(draw -> {
                            List<ActivityChancePo> chancePos = grouped.get(draw.getDrawId());
                            draw.setChances(activityAssembler.fromPoList(chancePos));
                        });
                    });
    }
}
