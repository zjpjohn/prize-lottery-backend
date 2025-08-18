package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.DeviceCreateCmd;
import com.prize.lottery.application.command.dto.NotifyAppCreateCmd;
import com.prize.lottery.application.command.dto.NotifyAppModifyCmd;
import com.prize.lottery.domain.notify.model.NotifyAppDo;
import com.prize.lottery.domain.notify.model.NotifyDeviceDo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotifyAppAssembler {

    void toDo(NotifyAppCreateCmd command, @MappingTarget NotifyAppDo notifyApp);

    void toDo(NotifyAppModifyCmd command, @MappingTarget NotifyAppDo notifyApp);

    void toDo(DeviceCreateCmd command, @MappingTarget NotifyDeviceDo device);

}
