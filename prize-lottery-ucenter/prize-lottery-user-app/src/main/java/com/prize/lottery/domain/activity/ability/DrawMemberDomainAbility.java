package com.prize.lottery.domain.activity.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.domain.activity.model.ActivityUserDo;
import com.prize.lottery.domain.activity.repository.IActivityDrawRepository;
import com.prize.lottery.domain.activity.repository.IActivityInfoRepository;
import com.prize.lottery.domain.activity.repository.IActivityUserRepository;
import com.prize.lottery.domain.user.model.UserMember;
import com.prize.lottery.domain.user.repository.IUserMemberRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DrawMemberDomainAbility {

    private final IActivityDrawRepository drawRepository;
    private final IActivityInfoRepository activityRepository;
    private final IActivityUserRepository drawUserRepository;
    private final IUserMemberRepository   memberRepository;

    /**
     * 加入抽奖活动
     */
    public Triple<ActivityDrawDo, ActivityUserDo, ActivityInfoDo> joinActivity(Long userId) {
        Optional<ActivityDrawDo> drawOptional = drawRepository.ofDay(userId, LocalDate.now());
        //今日已存在抽奖记录
        if (drawOptional.isPresent()) {
            ActivityDrawDo activityDraw = drawOptional.get();
            Long           activityId   = activityDraw.getActivityId();
            ActivityInfoDo activity     = activityRepository.of(activityId).getRoot();
            ActivityUserDo drawUser     = drawUserRepository.of(activityId, userId).getRoot();
            return Triple.of(activityDraw, drawUser, activity);
        }
        Optional<Aggregate<Long, ActivityInfoDo>> activityOptional = activityRepository.ofUsing();
        ActivityInfoDo activity = activityOptional.map(Aggregate::getRoot)
                                                  .orElseThrow(ResponseHandler.ACTIVITY_MEMBER_NONE);
        Aggregate<Long, ActivityUserDo> drawUserAggregate = drawUserRepository.of(activity.getId(), userId);
        drawUserRepository.save(drawUserAggregate);

        //用户抽奖活动规则校验
        ActivityUserDo drawUser = drawUserAggregate.getRoot();
        Assert.state(drawUser.drawPreCheck(), ResponseHandler.ACTIVITY_MEMBER_NONE);

        //创建抽奖记录
        ActivityDrawDo activityDraw = new ActivityDrawDo(activity.getId(), userId);
        AggregateFactory.create(activityDraw).save(drawRepository::save);
        return Triple.of(activityDraw, drawUser, activity);
    }

    /**
     * 活动抽奖
     */
    public ActivityDrawDo drawActivity(Long drawId, Long userId) {
        ActivityDrawDo activityDraw = drawRepository.ofId(drawId)
                                                    .orElseThrow(Assert.supply(ResponseHandler.ACTIVITY_DRAW_NONE));
        Assert.state(!activityDraw.hasDrawn(), ResponseHandler.ACTIVITY_HAS_DRAWN);
        Assert.state(activityDraw.getUserId().equals(userId), ResponseHandler.ACTIVITY_DRAW_FORBIDDEN);

        Aggregate<Long, ActivityUserDo> drawUserAggregate = drawUserRepository.of(activityDraw.getActivityId(), activityDraw.getUserId());
        ActivityUserDo                  activityUser      = drawUserAggregate.getRoot();
        ActivityInfoDo                  activity          = activityRepository.of(activityDraw.getActivityId())
                                                                              .getRoot();
        Assert.state(activityDraw.satisfiedChances(activity.getMinimum()), ResponseHandler.CHANCES_NOT_ENOUGH);

        Aggregate<Long, ActivityDrawDo> drawAggregate = AggregateFactory.create(activityDraw);
        boolean                         result        = activityDraw.drawLottery(activity.getThrottle(), activity.getDuration(), activityUser);
        drawRepository.save(drawAggregate);
        drawUserRepository.save(drawUserAggregate);
        if (result) {
            //抽奖成功，更新会员有效期
            memberRepository.ofUser(userId)
                            .map(agg -> agg.peek(member -> member.renewMember(activity.getDuration())))
                            .orElseGet(() -> AggregateFactory.create(new UserMember(userId, activity.getDuration())))
                            .save(memberRepository::save);
        }
        return activityDraw;
    }

}
