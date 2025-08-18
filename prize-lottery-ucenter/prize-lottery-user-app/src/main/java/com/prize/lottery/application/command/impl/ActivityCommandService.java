package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.ActivityAssembler;
import com.prize.lottery.application.command.IActivityCommandService;
import com.prize.lottery.application.command.dto.ActivityCreateCmd;
import com.prize.lottery.application.command.dto.ActivityEditCmd;
import com.prize.lottery.application.command.vo.ActivityJoinResult;
import com.prize.lottery.application.command.vo.DrawMemberResult;
import com.prize.lottery.domain.activity.ability.ActivityDomainAbility;
import com.prize.lottery.domain.activity.ability.DrawMemberDomainAbility;
import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.domain.activity.model.ActivityUserDo;
import com.prize.lottery.domain.activity.repository.IActivityInfoRepository;
import com.prize.lottery.infrast.persist.enums.ActivityState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityCommandService implements IActivityCommandService {

    private final IActivityInfoRepository activityRepository;
    private final ActivityAssembler       activityAssembler;
    private final ActivityDomainAbility   activityAbility;
    private final DrawMemberDomainAbility drawDomainAbility;

    @Override
    @Transactional
    public void createActivity(ActivityCreateCmd command) {
        ActivityInfoDo activity = new ActivityInfoDo(command, activityAssembler::toDo);
        AggregateFactory.create(activity).save(activityRepository::save);
    }

    @Override
    @Transactional
    public void editActivity(ActivityEditCmd command) {
        ActivityState state = command.getState();
        if (state != null) {
            activityAbility.changeActivityState(command.getId(), state);
            return;
        }
        activityRepository.of(command.getId())
                          .peek(activity -> activity.modify(command, activityAssembler::toDo))
                          .save(activityRepository::save);
    }

    @Override
    @Transactional
    public ActivityJoinResult joinActivity(Long userId) {
        Triple<ActivityDrawDo, ActivityUserDo, ActivityInfoDo> result = drawDomainAbility.joinActivity(userId);
        return new ActivityJoinResult(result.getLeft(), result.getMiddle(), result.getRight());
    }

    @Override
    @Transactional
    public DrawMemberResult drawActivity(Long drawId, Long userId) {
        ActivityDrawDo activityDraw = drawDomainAbility.drawActivity(drawId, userId);
        return new DrawMemberResult(activityDraw);
    }

}
