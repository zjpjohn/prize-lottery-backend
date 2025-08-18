package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.PackInfoCreateCmd;
import com.prize.lottery.application.command.dto.PackInfoModifyCmd;
import com.prize.lottery.application.command.dto.PrivilegeCreateCmd;
import com.prize.lottery.application.command.dto.PrivilegeModifyCmd;
import com.prize.lottery.application.query.vo.PackPrivilegeVo;
import com.prize.lottery.domain.order.model.aggregate.PackInfoDo;
import com.prize.lottery.domain.order.model.entity.PackPrivilege;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PackInfoAssembler {

    @Mapping(source = "unit", target = "timeUnit")
    void toDo(PackInfoCreateCmd command, @MappingTarget PackInfoDo packInfo);

    @Mapping(source = "unit", target = "timeUnit")
    @Mapping(source = "priority", target = "priority", ignore = true)
    void toDo(PackInfoModifyCmd command, @MappingTarget PackInfoDo packInfo);

    PackPrivilege toDo(PrivilegeCreateCmd command);

    void toDo(PrivilegeModifyCmd command, @MappingTarget PackPrivilege privilege);

    PackPrivilegeVo toVo(PackPrivilegePo privilege);

    List<PackPrivilegeVo> toVoList(List<PackPrivilegePo> list);
}
