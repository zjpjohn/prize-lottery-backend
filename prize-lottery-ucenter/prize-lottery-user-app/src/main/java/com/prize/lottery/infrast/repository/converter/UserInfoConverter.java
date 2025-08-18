package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.model.UserMember;
import com.prize.lottery.domain.user.model.UserSign;
import com.prize.lottery.domain.user.valobj.MemberLog;
import com.prize.lottery.domain.user.valobj.UserSignLog;
import com.prize.lottery.infrast.persist.po.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoConverter {

    UserSign toDo(UserSignPo userSign);

    UserSignPo toPo(UserSign userSign);

    UserSignLogPo toPo(UserSignLog signLog);

    UserInfoPo toPo(UserInfo userInfo);

    UserInfo toDo(UserInfoPo userInfo);

    UserMember toDo(UserMemberPo member);

    UserMemberLogPo toPo(Long userId, MemberLog log);

    UserMemberPo toPo(UserMember member);

}
