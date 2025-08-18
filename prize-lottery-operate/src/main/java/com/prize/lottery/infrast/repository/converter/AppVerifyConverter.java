package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.app.model.AppVerifyDo;
import com.prize.lottery.infrast.persist.po.AppVerifyPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppVerifyConverter {

    AppVerifyPo toPo(AppVerifyDo verify);

    AppVerifyDo toDo(AppVerifyPo verify);

}
