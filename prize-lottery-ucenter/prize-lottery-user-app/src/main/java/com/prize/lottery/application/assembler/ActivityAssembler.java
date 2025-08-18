package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.ActivityCreateCmd;
import com.prize.lottery.application.command.dto.ActivityEditCmd;
import com.prize.lottery.application.command.vo.ActivityChanceVo;
import com.prize.lottery.application.query.vo.ActivityDrawVo;
import com.prize.lottery.domain.activity.model.ActivityChance;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.po.ActivityDrawPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityAssembler {

    @Mapping(source = "remark", target = "remark", ignore = true)
    void toDo(ActivityCreateCmd command, @MappingTarget ActivityInfoDo activity);

    @Mapping(source = "remark", target = "remark", ignore = true)
    @Mapping(source = "state", target = "state", ignore = true)
    void toDo(ActivityEditCmd command, @MappingTarget ActivityInfoDo activity);

    ActivityDrawVo toVo(ActivityDrawPo draw);

    List<ActivityDrawVo> toVoList(List<ActivityDrawPo> drawList);

    ActivityChanceVo fromPo(ActivityChancePo chance);

    List<ActivityChanceVo> fromPoList(List<ActivityChancePo> chances);

    ActivityChanceVo fromEntity(ActivityChance chance);

    List<ActivityChanceVo> fromEntities(List<ActivityChance> chances);

}
