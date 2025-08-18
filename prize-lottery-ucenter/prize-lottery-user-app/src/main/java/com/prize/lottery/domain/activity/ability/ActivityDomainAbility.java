package com.prize.lottery.domain.activity.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.domain.activity.repository.IActivityInfoRepository;
import com.prize.lottery.infrast.persist.enums.ActivityState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityDomainAbility {

    private final IActivityInfoRepository activityRepository;

    /**
     * 改变活动状态
     */
    public void changeActivityState(Long activityId, ActivityState state) {
        Aggregate<Long, ActivityInfoDo> aggregate = activityRepository.of(activityId);
        if (state == ActivityState.USING) {
            activityRepository.ofUsing()
                              .map(agg -> agg.peek(root -> root.changeState(ActivityState.CREATED)))
                              .ifPresent(activityRepository::save);
        }
        aggregate.peek(root -> root.changeState(state)).save(activityRepository::save);
    }

}
