package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.pack.model.aggregate.PrivilegeListDo;
import com.prize.lottery.domain.pack.model.entity.PackPrivilege;
import com.prize.lottery.domain.pack.repository.IPrivilegeRepository;
import com.prize.lottery.infrast.persist.mapper.PackInfoMapper;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.repository.converter.PackInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrivilegeRepository implements IPrivilegeRepository {

    private final PackInfoMapper    mapper;
    private final PackInfoConverter converter;

    @Override
    public void save(Aggregate<Long, PrivilegeListDo> aggregate) {
        List<PackPrivilege> newEntities = aggregate.newEntities(PrivilegeListDo::getPrivileges);
        if (!CollectionUtils.isEmpty(newEntities)) {
            List<PackPrivilegePo> packPrivileges = converter.toPoList(newEntities);
            mapper.addPrivilegeList(packPrivileges);
        }
        List<PackPrivilege> changedEntities = aggregate.changedEntities(PrivilegeListDo::getPrivileges);
        if (!CollectionUtils.isEmpty(changedEntities)) {
            List<PackPrivilegePo> packPrivileges = converter.toPoList(changedEntities);
            mapper.editPrivilegeList(packPrivileges);
        }
        List<PackPrivilege> removedEntities = aggregate.removedEntities(PrivilegeListDo::getPrivileges);
        if (!CollectionUtils.isEmpty(removedEntities)) {
            List<Long> idList = removedEntities.stream().map(PackPrivilege::getId).collect(Collectors.toList());
            mapper.removePrivileges(idList);
        }
    }

    @Override
    public Aggregate<Long, PrivilegeListDo> of() {
        List<PackPrivilegePo> poList          = mapper.getPackPrivileges();
        List<PackPrivilege>   privileges      = converter.toDoList(poList);
        PrivilegeListDo       privilegeListDo = new PrivilegeListDo(privileges);
        return AggregateFactory.create(privilegeListDo);
    }

    @Override
    public boolean hasPrivileges() {
        return mapper.countPackPrivileges() > 0;
    }

}
