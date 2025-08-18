package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.admin.model.Administrator;
import com.prize.lottery.domain.admin.repository.IAdministratorRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AdministratorMapper;
import com.prize.lottery.infrast.persist.po.AdministratorPo;
import com.prize.lottery.infrast.repository.converter.AdministratorConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdministratorRepository implements IAdministratorRepository {

    private final AdministratorMapper    mapper;
    private final AdministratorConverter converter;

    @Override
    public void save(Aggregate<Long, Administrator> aggregate) {
        Administrator root = aggregate.getRoot();
        if (root.isNew()) {
            AdministratorPo administrator = converter.toPo(root);
            mapper.addAdministrator(administrator);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editAdministrator);
    }

    @Override
    public Aggregate<Long, Administrator> ofId(Long id) {
        return Optional.ofNullable(mapper.getAdministratorById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.ADMIN_ACCT_NONE);
    }

    @Override
    public Aggregate<Long, Administrator> ofName(String name) {
        return Optional.ofNullable(mapper.getAdministratorByName(name))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.ADMIN_ACCT_NONE);
    }

    @Override
    public boolean existName(String name) {
        return mapper.getAdministratorByName(name) != null;
    }

}
