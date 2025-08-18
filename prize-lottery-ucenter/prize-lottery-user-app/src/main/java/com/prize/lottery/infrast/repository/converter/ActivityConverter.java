package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.activity.model.ActivityChance;
import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.domain.activity.model.ActivityUserDo;
import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.po.ActivityDrawPo;
import com.prize.lottery.infrast.persist.po.ActivityMemberPo;
import com.prize.lottery.infrast.persist.po.ActivityUserPo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityConverter {

    ActivityInfoDo toDo(ActivityMemberPo activity);

    ActivityMemberPo toPo(ActivityInfoDo activity);

    ActivityDrawPo toPo(ActivityDrawDo draw);

    ActivityDrawDo toDo(ActivityDrawPo draw);

    ActivityUserDo toDo(ActivityUserPo user);

    ActivityUserPo toPo(ActivityUserDo user);

    ActivityChance toEntity(ActivityChancePo chance);

    List<ActivityChance> toEntities(List<ActivityChancePo> chances);

    ActivityChancePo toPo(ActivityChance chance);

    List<ActivityChancePo> toPoList(List<ActivityChance> chances);

}
