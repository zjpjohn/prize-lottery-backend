package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import com.prize.lottery.infrast.repository.converter.UserInfoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserInfoRepository implements IUserInfoRepository {

    private final UserInfoMapper    mapper;
    private final UserInfoConverter converter;

    @Override
    public void save(Aggregate<Long, UserInfo> aggregate) {
        UserInfo root = aggregate.getRoot();
        if (root.isNew()) {
            int result = mapper.saveUserInfo(converter.toPo(root));
            Assert.state(result > 0, ResponseHandler.SAVE_USER_ERROR);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editUserInfo);
    }

    @Override
    public Aggregate<Long, UserInfo> ofId(Long userId) {
        return mapper.getUserInfoById(userId).map(converter::toDo).map(AggregateFactory::create).orElse(null);
    }

    @Override
    public Optional<Aggregate<Long, UserInfo>> ofPhone(String phone) {
        UserInfoPo user = mapper.getUserInfoByPhone(phone);
        return Optional.ofNullable(user).map(converter::toDo).map(AggregateFactory::create);
    }

    @Override
    public UserInfo query(Long userId) {
        return mapper.getUserInfoById(userId).map(converter::toDo).orElseThrow(ResponseHandler.USER_INFO_NONE);
    }

    @Override
    public boolean exist(Long userId) {
        return mapper.getUserInfoById(userId).isPresent();
    }
}
