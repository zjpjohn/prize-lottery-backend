package com.prize.lottery.domain.user.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.valobj.UserInviteLog;

import java.util.Optional;

public interface IUserInviteRepository {

    void save(Aggregate<Long, UserInvite> aggregate);

    Aggregate<Long, UserInvite> ofId(Long id);

    Optional<Aggregate<Long, UserInvite>> ofCode(String code);

    Optional<UserInviteLog> ofUserLog(Long userId);

}
