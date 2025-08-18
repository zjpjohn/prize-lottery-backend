package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.notify.model.NotifyAppDo;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyAppConverter {

    NotifyAppPo toPo(NotifyAppDo app);

    NotifyAppDo toDo(NotifyAppPo app);

}
