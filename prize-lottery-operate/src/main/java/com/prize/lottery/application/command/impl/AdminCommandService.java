package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IAdminCommandService;
import com.prize.lottery.application.command.dto.AdminCreateCmd;
import com.prize.lottery.application.command.dto.AdminLoginCmd;
import com.prize.lottery.application.command.dto.AdminPasswordCmd;
import com.prize.lottery.application.command.dto.ResetPasswordCmd;
import com.prize.lottery.application.command.executor.AdminAuthLoginOutExe;
import com.prize.lottery.application.command.executor.AdminAuthenticateExe;
import com.prize.lottery.application.vo.AdminAuthVo;
import com.prize.lottery.domain.admin.model.Administrator;
import com.prize.lottery.domain.admin.repository.IAdministratorRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AdminLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCommandService implements IAdminCommandService {

    private final AdminAuthenticateExe     adminAuthenticateExe;
    private final AdminAuthLoginOutExe     adminAuthLoginOutExe;
    private final IAdministratorRepository administratorRepository;

    @Override
    public AdminAuthVo adminAuth(AdminLoginCmd command) {
        return adminAuthenticateExe.execute(command);
    }

    @Override
    public void loginOut(Long managerId) {
        adminAuthLoginOutExe.execute(managerId);
    }

    @Override
    public void addAdministrator(AdminCreateCmd command) {
        Assert.state(command.getConfirm().equals(command.getPassword()), ResponseHandler.PASSWORD_NOT_CONSTANT);
        AdminLevel adminLevel = AdminLevel.findOf(command.getLevel());

        boolean existed = administratorRepository.existName(command.getName());
        Assert.state(!existed, ResponseHandler.APP_NAME_EXIST);

        Administrator admin = new Administrator(command.getName(), command.getPassword(), command.getPhone(), adminLevel);
        AggregateFactory.create(admin).save(administratorRepository::save);
    }

    @Override
    public void resetMinePassword(ResetPasswordCmd command) {
        //密码参数校验
        command.validate();
        //修改账户密码
        administratorRepository.ofId(command.getManagerId())
                               .peek(root -> root.resetPassword(command.getPassword()))
                               .save(administratorRepository::save);
        //已登录的失效
        adminAuthLoginOutExe.execute(command.getManagerId());
    }

    @Override
    @Transactional
    public void resetAdminPassword(AdminPasswordCmd command) {
        //密码参数校验
        command.validate();
        //超级管理员允许修改别人账户密码
        Aggregate<Long, Administrator> manager = administratorRepository.ofId(command.getManagerId());
        Assert.state(manager.getRoot().getLevel() == AdminLevel.SUPER_ADMIN, ResponseHandler.ADMIN_NONE_PRIVILEGE);
        //修改账户密码
        administratorRepository.ofId(command.getId())
                               .peek(root -> root.resetPassword(command.getPassword()))
                               .save(administratorRepository::save);
        //已登录的失效
        adminAuthLoginOutExe.execute(command.getId());
    }

    @Override
    public void frozenAdministrator(Long id) {
        administratorRepository.ofId(id).peek(Administrator::frozen).save(administratorRepository::save);
        //冻结账户已登录的失效
        adminAuthLoginOutExe.execute(id);
    }

    @Override
    public void unfrozenAdministrator(Long id) {
        administratorRepository.ofId(id).peek(Administrator::unfrozen).save(administratorRepository::save);
    }

    @Override
    public void invalidateAdministrator(Long id) {
        administratorRepository.ofId(id).peek(Administrator::invalid).save(administratorRepository::save);
        //取消账户已登录的失效
        adminAuthLoginOutExe.execute(id);
    }

}
