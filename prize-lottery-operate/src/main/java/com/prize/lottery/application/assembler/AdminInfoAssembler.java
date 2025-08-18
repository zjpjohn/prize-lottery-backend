package com.prize.lottery.application.assembler;

import com.prize.lottery.application.vo.AdminDetailVo;
import com.prize.lottery.infrast.persist.po.AdministratorPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminInfoAssembler {

    AdminDetailVo toVo(AdministratorPo admin);

}
