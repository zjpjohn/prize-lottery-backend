package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.repository.IUserInviteRepository;
import com.prize.lottery.domain.user.valobj.InviteRewardVal;
import com.prize.lottery.domain.user.valobj.UserInviteLog;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.UserInviteLogPo;
import com.prize.lottery.infrast.persist.po.UserInvitePo;
import com.prize.lottery.infrast.repository.converter.UserInviteConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserInviteRepository implements IUserInviteRepository {

    private final UserInviteMapper    userInviteMapper;
    private final UserInviteConverter userInviteConverter;

    @Override
    public void save(Aggregate<Long, UserInvite> aggregate) {
        UserInvite invite = aggregate.getRoot();
        if (invite.isNew()) {
            UserInvitePo userInvite = userInviteConverter.toPo(invite);
            userInviteMapper.addUserInvite(userInvite);
            return;
        }
        UserInvite changed = aggregate.changed();
        if (changed != null) {
            UserInvitePo    userInvite;
            InviteRewardVal rewardVal = changed.getRewardVal();
            if (rewardVal != null) {
                userInvite = userInviteConverter.toPo(changed.getUserId(), rewardVal);
                UserInviteLogPo inviteLog = userInviteConverter.toPo(changed.getInviteLog());
                userInviteMapper.addUserInviteLog(inviteLog);
            } else {
                userInvite = userInviteConverter.toPo(changed);
            }
            userInviteMapper.editUserInvite(userInvite);
        }
    }

    @Override
    public Aggregate<Long, UserInvite> ofId(Long userId) {
        return Optional.ofNullable(userInviteMapper.getUserInvite(userId))
                       .map(userInviteConverter::toDo)
                       .map(AggregateFactory::create)
                       .orElse(null);
    }

    @Override
    public Optional<Aggregate<Long, UserInvite>> ofCode(String code) {
        return Optional.ofNullable(userInviteMapper.getUserInviteByCode(code))
                       .map(userInviteConverter::toDo)
                       .map(AggregateFactory::create);
    }

    @Override
    public Optional<UserInviteLog> ofUserLog(Long userId) {
        return Optional.ofNullable(userInviteMapper.getInviteLogByUserId(userId)).map(userInviteConverter::toValue);
    }

}
