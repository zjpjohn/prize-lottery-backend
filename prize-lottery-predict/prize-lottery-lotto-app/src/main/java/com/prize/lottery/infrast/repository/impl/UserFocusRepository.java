package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.user.model.UserFocus;
import com.prize.lottery.domain.user.repository.IUserFocusRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.MasterInfoConverter;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterFocusPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFocusRepository implements IUserFocusRepository {

    private final MasterInfoMapper    mapper;
    private final MasterInfoConverter converter;

    @Override
    public void save(UserFocus focus) {
        MasterFocusPo focusPo = converter.toPo(focus);
        int           result  = mapper.addMasterFocus(Lists.newArrayList(focusPo));
        Assert.state(result > 0, ResponseHandler.HAS_SUBSCRIBE_MASTER);
        //todo 变更专家关注计数

    }

    @Override
    public UserFocus ofUk(Long userId, String masterId) {
        return Optional.ofNullable(mapper.getFocusMaster(userId, masterId))
                       .map(converter::toDo)
                       .orElseThrow(ResponseHandler.FOCUS_MASTER_NONE);
    }

    @Override
    public boolean hasFocused(Long userId, String masterId) {
        return mapper.hasFocusMaster(userId, masterId) == 0;
    }

    @Override
    public void remove(UserFocus focus) {
        int result = mapper.removeMasterFocus(focus.getId());
        Assert.state(result > 0, ResponseHandler.FOCUS_MASTER_NONE);
        //todo 变更专家关注计数

    }
}
