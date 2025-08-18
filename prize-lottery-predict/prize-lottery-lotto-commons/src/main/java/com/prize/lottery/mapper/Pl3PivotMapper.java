package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface Pl3PivotMapper {

    int addPl3Pivot(Pl3PivotPo pivot);

    int editPl3Pivot(Pl3PivotPo pivot);

    Optional<Pl3PivotPo> getPl3Pivot(String period);

    Optional<Pl3PivotPo> getPl3PivotById(Long id);

    List<Pl3PivotPo> getPl3PivotList(PageCondition condition);

    int countPl3Pivots(PageCondition condition);

    String latestPivotPeriod();

    List<String> pl3PivotPeriods();

}
