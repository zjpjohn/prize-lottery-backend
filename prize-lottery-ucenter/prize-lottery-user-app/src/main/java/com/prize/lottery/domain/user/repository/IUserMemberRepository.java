package com.prize.lottery.domain.user.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.user.model.UserMember;

import java.util.List;
import java.util.Optional;

public interface IUserMemberRepository {

    void save(Aggregate<Long, UserMember> aggregate);

    Optional<Aggregate<Long, UserMember>> ofUser(Long userId);

    Integer expiredMembers(Integer size);

}
