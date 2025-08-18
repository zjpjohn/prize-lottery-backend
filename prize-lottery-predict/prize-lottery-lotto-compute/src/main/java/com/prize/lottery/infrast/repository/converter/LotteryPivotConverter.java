package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.fc3d.model.Fc3dPivotDo;
import com.prize.lottery.domain.pl3.model.Pl3PivotDo;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotteryPivotConverter {

    Fc3dPivotPo toPo(Fc3dPivotDo pivot);

    Fc3dPivotDo toDo(Fc3dPivotPo pivot);

    Pl3PivotPo toPo(Pl3PivotDo pivot);

    Pl3PivotDo toDo(Pl3PivotPo pivot);

}
