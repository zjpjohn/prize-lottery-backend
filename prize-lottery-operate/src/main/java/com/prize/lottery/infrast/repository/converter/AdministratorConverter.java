package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.admin.model.Administrator;
import com.prize.lottery.infrast.persist.po.AdministratorPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdministratorConverter {

    Administrator toDo(AdministratorPo administrator);

    AdministratorPo toPo(Administrator administrator);

}
