package com.prize.lottery.domain.pack.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.pack.model.aggregate.PrivilegeListDo;

public interface IPrivilegeRepository {

    void save(Aggregate<Long, PrivilegeListDo> aggregate);

    Aggregate<Long, PrivilegeListDo> of();

    boolean hasPrivileges();

}
