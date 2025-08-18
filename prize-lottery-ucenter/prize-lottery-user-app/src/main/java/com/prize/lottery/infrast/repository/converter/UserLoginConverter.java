package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.user.model.UserLogin;
import com.prize.lottery.infrast.persist.po.UserDevicePo;
import com.prize.lottery.infrast.persist.po.UserLoginLogPo;
import com.prize.lottery.infrast.persist.po.UserLoginPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserLoginConverter {

    UserLoginPo to(UserLogin login);

    UserDevicePo toBind(UserLogin login);

    UserLogin from(UserLoginPo login);

    UserLoginLogPo toLog(UserLogin login);

}
