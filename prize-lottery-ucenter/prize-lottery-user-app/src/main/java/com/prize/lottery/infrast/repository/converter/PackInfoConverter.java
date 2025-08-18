package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.pack.model.aggregate.PackInfoDo;
import com.prize.lottery.domain.pack.model.entity.PackPrivilege;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PackInfoConverter {

    PackInfoPo toPo(PackInfoDo packInfo);

    PackInfoDo toDo(PackInfoPo packInfo);

    PackPrivilegePo toPo(PackPrivilege privilege);

    List<PackPrivilegePo> toPoList(List<PackPrivilege> privileges);

    PackPrivilege toDo(PackPrivilegePo privilege);

    List<PackPrivilege> toDoList(List<PackPrivilegePo> privileges);

}
