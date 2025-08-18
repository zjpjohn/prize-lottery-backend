package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.activity.model.ActivityChance;
import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.domain.activity.repository.IActivityDrawRepository;
import com.prize.lottery.infrast.persist.mapper.ActivityInfoMapper;
import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.po.ActivityDrawPo;
import com.prize.lottery.infrast.repository.converter.ActivityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityDrawRepository implements IActivityDrawRepository {

    private final ActivityInfoMapper mapper;
    private final ActivityConverter  converter;

    @Override
    public void save(Aggregate<Long, ActivityDrawDo> aggregate) {
        ActivityDrawDo root = aggregate.getRoot();
        if (root.isNew()) {
            ActivityDrawPo activityDraw = converter.toPo(root);
            mapper.addActivityDraw(activityDraw);
            List<ActivityChance> chances = root.getChances();
            //添加系统抽奖机会
            List<ActivityChancePo> chanceList = converter.toPoList(chances);
            mapper.addDrawChances(chanceList);
            return;
        }
        //新增抽奖机会
        List<ActivityChance> newChances = aggregate.newEntities(ActivityDrawDo::getChances);
        if (CollectionUtils.isNotEmpty(newChances)) {
            List<ActivityChancePo> chanceList = converter.toPoList(newChances);
            mapper.addDrawChances(chanceList);
        }
        //更新抽奖机会
        List<ActivityChance> changedChances = aggregate.changedEntities(ActivityDrawDo::getChances);
        if (CollectionUtils.isNotEmpty(changedChances)) {
            List<ActivityChancePo> chanceList = converter.toPoList(changedChances);
            mapper.editDrawChances(chanceList);
        }
        //更新抽奖信息
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editActivityDraw);
    }

    @Override
    public Optional<ActivityDrawDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getActivityDraw(id)).map(converter::toDo).map(this::build);
    }

    @Override
    public Optional<ActivityDrawDo> ofDay(Long userId, LocalDate day) {
        return Optional.ofNullable(mapper.getActivityUserDraw(userId, day)).map(converter::toDo).map(this::build);
    }

    private ActivityDrawDo build(ActivityDrawDo draw) {
        //本次抽奖机会
        List<ActivityChancePo> chances = mapper.getDrawChances(draw.getId());
        draw.setChances(converter.toEntities(chances));
        return draw;
    }

}
