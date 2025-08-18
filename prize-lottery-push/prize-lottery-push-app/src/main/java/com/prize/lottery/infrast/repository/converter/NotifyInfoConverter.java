package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.notify.model.NotifyInfoDo;
import com.prize.lottery.domain.notify.model.NotifyTaskDo;
import com.prize.lottery.infrast.persist.po.NotifyInfoPo;
import com.prize.lottery.infrast.persist.po.NotifyTaskPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyInfoConverter {

    NotifyInfoDo toDo(NotifyInfoPo notifyInfo);

    NotifyInfoPo toPo(NotifyInfoDo notifyInfo);

    NotifyTaskPo toPo(NotifyTaskDo task);

}
