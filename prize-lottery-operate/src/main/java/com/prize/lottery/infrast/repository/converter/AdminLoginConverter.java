package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.admin.model.AdminLogin;
import com.prize.lottery.infrast.persist.po.AdminLoginLogPo;
import com.prize.lottery.infrast.persist.po.AdminLoginPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminLoginConverter {

    AdminLogin from(AdminLoginPo login);

    AdminLoginPo to(AdminLogin login);

    AdminLoginLogPo toLog(AdminLogin login);
}
