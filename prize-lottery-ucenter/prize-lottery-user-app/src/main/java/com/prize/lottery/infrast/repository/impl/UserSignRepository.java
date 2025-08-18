package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.user.model.UserSign;
import com.prize.lottery.domain.user.repository.IUserSignRepository;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.po.UserSignPo;
import com.prize.lottery.infrast.repository.converter.UserInfoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSignRepository implements IUserSignRepository {

    private final UserInfoMapper    userInfoMapper;
    private final UserInfoConverter userSignConverter;

    @Override
    public void save(UserSign userSign) {
        //更新签到信息
        userInfoMapper.saveUserSign(userSignConverter.toPo(userSign));
        //保存签到日志
        userInfoMapper.addUserSignLog(userSignConverter.toPo(userSign.getLog()));
    }

    @Override
    public UserSign ofId(Long userId) {
        UserSignPo userSign = userInfoMapper.getUserSign(userId);
        return Optional.ofNullable(userSign).map(userSignConverter::toDo).orElseGet(() -> new UserSign(userId));
    }
}
