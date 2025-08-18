package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.user.model.UserMember;
import com.prize.lottery.domain.user.repository.IUserMemberRepository;
import com.prize.lottery.domain.user.valobj.MemberLog;
import com.prize.lottery.infrast.persist.enums.MemberState;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.po.UserMemberLogPo;
import com.prize.lottery.infrast.persist.po.UserMemberPo;
import com.prize.lottery.infrast.repository.converter.UserInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMemberRepository implements IUserMemberRepository {

    private final UserInfoMapper    mapper;
    private final UserInfoConverter converter;

    @Override
    public void save(Aggregate<Long, UserMember> aggregate) {
        UserMember      root      = aggregate.getRoot();
        MemberLog       log       = root.getLog();
        UserMemberLogPo memberLog = converter.toPo(root.getUserId(), log);
        if (log != null) {
            mapper.addUserMemberLog(memberLog);
        }
        if (root.isNew()) {
            UserMemberPo member = converter.toPo(root);
            mapper.addUserMember(member);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editUserMember);
    }

    @Override
    public Optional<Aggregate<Long, UserMember>> ofUser(Long userId) {
        return mapper.getUserMember(userId).map(converter::toDo).map(AggregateFactory::create);
    }

    @Override
    public Integer expiredMembers(Integer size) {
        List<UserMemberPo> members = mapper.getExpiredMembers(size);
        List<UserMemberPo> expires = members.stream().map(e -> {
            UserMemberPo member = new UserMemberPo();
            member.setUserId(member.getUserId());
            member.setState(MemberState.EXPIRED);
            return member;
        }).toList();
        if (CollectionUtils.isNotEmpty(expires)) {
            mapper.expireUserMembers(expires);
        }
        return members.size();
    }

}
