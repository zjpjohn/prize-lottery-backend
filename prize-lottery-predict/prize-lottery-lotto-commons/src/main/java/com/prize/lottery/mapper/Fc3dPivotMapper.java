package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface Fc3dPivotMapper {

    int addFc3dPivot(Fc3dPivotPo pivot);

    int editFc3dPivot(Fc3dPivotPo pivot);

    Optional<Fc3dPivotPo> getFc3dPivot(String period);

    Optional<Fc3dPivotPo> getFc3dPivotById(Long id);

    List<Fc3dPivotPo> getFc3dPivotList(PageCondition condition);

    int countFc3dPivots(PageCondition condition);

    String latestPivotPeriod();

    List<String> fc3dPivotPeriods();

}
