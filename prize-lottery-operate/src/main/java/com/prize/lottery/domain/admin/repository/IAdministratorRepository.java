package com.prize.lottery.domain.admin.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.admin.model.Administrator;

public interface IAdministratorRepository {

    void save(Aggregate<Long, Administrator> aggregate);

    Aggregate<Long, Administrator> ofId(Long id);

    Aggregate<Long, Administrator> ofName(String name);

    boolean existName(String name);
}
