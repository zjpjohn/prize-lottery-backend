package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.notify.model.NotifyDeviceDo;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyDeviceConverter {

    NotifyDeviceDo toDo(NotifyDevicePo device);

    NotifyDevicePo toPo(NotifyDeviceDo device);

}
