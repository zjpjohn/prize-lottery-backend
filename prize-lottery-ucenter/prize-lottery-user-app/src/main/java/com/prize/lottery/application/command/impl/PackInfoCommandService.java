package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.PackInfoAssembler;
import com.prize.lottery.application.command.IPackInfoCommandService;
import com.prize.lottery.application.command.dto.PackInfoCreateCmd;
import com.prize.lottery.application.command.dto.PackInfoModifyCmd;
import com.prize.lottery.application.command.dto.PrivilegeCreateCmd;
import com.prize.lottery.application.command.dto.PrivilegeModifyCmd;
import com.prize.lottery.domain.pack.model.aggregate.PackInfoDo;
import com.prize.lottery.domain.pack.repository.IPackInfoRepository;
import com.prize.lottery.domain.pack.repository.IPrivilegeRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackInfoCommandService implements IPackInfoCommandService {

    private final PackInfoAssembler    assembler;
    private final IPackInfoRepository  packRepository;
    private final IPrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void createPack(PackInfoCreateCmd command) {
        //价格校验
        Assert.state(command.getDiscount() <= command.getPrice(), ResponseHandler.PACK_PRICE_ERROR);
        //是否存在重名套餐
        boolean existName = packRepository.existName(command.getName());
        Assert.state(!existName, ResponseHandler.PACK_NAME_EXIST);
        //保存套餐信息
        PackInfoDo packInfo = new PackInfoDo(command, assembler::toDo);
        AggregateFactory.create(packInfo).save(packRepository::save);
    }

    @Override
    @Transactional
    public void modifyPack(PackInfoModifyCmd command) {
        packRepository.ofNo(command.getPackNo())
                      .peek(root -> root.modify(command, assembler::toDo))
                      .save(packRepository::save);
    }

    @Override
    @Transactional
    public void removePack(String packNo) {
        PackInfoDo packInfo = packRepository.ofPackNo(packNo).orElseThrow(ResponseHandler.PACK_NONE);
        Assert.state(packInfo.canDelete(), ResponseHandler.PACK_DELETE_ERROR);
        packRepository.delete(packNo);
    }

    @Override
    @Transactional
    public void addPrivilege(PrivilegeCreateCmd command) {
        privilegeRepository.of()
                           .peek(root -> root.addPrivilege(command, assembler::toDo))
                           .save(privilegeRepository::save);
    }

    @Override
    @Transactional
    public void editPrivilege(PrivilegeModifyCmd command) {
        privilegeRepository.of()
                           .peek(root -> root.modifyPrivilege(command, assembler::toDo))
                           .save(privilegeRepository::save);
    }

    @Override
    @Transactional
    public void removePrivilege(Long privilegeId) {
        privilegeRepository.of().peek(root -> root.removePrivilege(privilegeId)).save(privilegeRepository::save);
    }

    @Override
    @Transactional
    public void sortPrivilege(Long id, Integer index) {
        privilegeRepository.of().peek(root -> root.sortPrivilege(id, index)).save(privilegeRepository::save);
    }

}
