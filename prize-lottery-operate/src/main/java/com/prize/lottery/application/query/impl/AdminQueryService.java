package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.assembler.AdminInfoAssembler;
import com.prize.lottery.application.query.IAdminQueryService;
import com.prize.lottery.application.query.dto.AdminPwdQuery;
import com.prize.lottery.application.query.dto.LoginLogQuery;
import com.prize.lottery.application.vo.AdminDetailVo;
import com.prize.lottery.domain.admin.model.Administrator;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AdminLevel;
import com.prize.lottery.infrast.persist.mapper.AdministratorMapper;
import com.prize.lottery.infrast.persist.po.AdminLoginLogPo;
import com.prize.lottery.infrast.persist.po.AdminLoginPo;
import com.prize.lottery.infrast.persist.po.AdministratorPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminQueryService implements IAdminQueryService {

    private final AdminInfoAssembler  adminAssembler;
    private final AdministratorMapper administratorMapper;

    @Override
    public AdminDetailVo getAdministratorDetail(Long id, Long managerId) {
        AdministratorPo mine = this.administratorMapper.getAdministratorById(managerId);
        Assert.notNull(mine, ResponseHandler.ADMIN_ACCT_EXIST);
        Assert.state(mine.getLevel() == AdminLevel.SUPER_ADMIN, ResponseHandler.ADMIN_NONE_PRIVILEGE);

        return this.getAdminDetail(id);
    }

    @Override
    public AdminDetailVo getAdminDetail(Long managerId) {
        AdministratorPo admin = this.administratorMapper.getAdministratorById(managerId);
        Assert.notNull(admin, ResponseHandler.ADMIN_ACCT_EXIST);
        AdminDetailVo adminDetail = adminAssembler.toVo(admin);
        AdminLoginPo  loginInfo   = administratorMapper.getAdminLogin(managerId);
        if (loginInfo != null) {
            adminDetail.setLoginIp(loginInfo.getLoginIp());
            adminDetail.setExpireAt(loginInfo.getExpireAt());
            adminDetail.setLoginTime(loginInfo.getLoginTime());
        }
        return adminDetail;
    }

    @Override
    public List<AdministratorPo> getAdministratorList(Long managerId) {
        AdministratorPo administrator = this.administratorMapper.getAdministratorById(managerId);
        if (administrator == null) {
            return Collections.emptyList();
        }
        if (administrator.getLevel() == AdminLevel.PLAIN_ADMIN) {
            return Lists.newArrayList(administrator);
        }
        return this.administratorMapper.getAllAdministrators();
    }

    @Override
    public Page<AdminLoginLogPo> getLoginLogList(PageCondition condition) {
        return condition.count(administratorMapper::countAdminLoginLogs).query(administratorMapper::getAdminLoginLogs);
    }

    @Override
    public Page<AdminLoginLogPo> getAdminLoginLogs(LoginLogQuery query) {
        Long            managerId = query.getManagerId();
        AdministratorPo mine      = this.administratorMapper.getAdministratorById(managerId);
        Assert.notNull(mine, ResponseHandler.ADMIN_ACCT_EXIST);
        Assert.state(mine.getLevel() == AdminLevel.SUPER_ADMIN, ResponseHandler.ADMIN_NONE_PRIVILEGE);
        //超级管理员允许查看其他账户的登录日志
        return this.getLoginLogList(query.from());
    }

    @Override
    public String passwordQuery(AdminPwdQuery query) {
        AdministratorPo administrator = administratorMapper.getAdministratorById(query.getManagerId());
        Assert.notNull(administrator, ResponseHandler.ADMIN_ACCT_NONE);
        Long adminId = query.getAdminId();
        //查看自己的登录账户密码
        if (adminId == null || query.getManagerId().equals(adminId)) {
            return Administrator.decodePwd(administrator.getPassword());
        }
        //超级管理员查看其他账户登录密码
        Assert.state(administrator.getLevel() == AdminLevel.SUPER_ADMIN, ResponseHandler.ADMIN_NONE_PRIVILEGE);
        AdministratorPo admin = administratorMapper.getAdministratorById(adminId);
        return Administrator.decodePwd(admin.getPassword());
    }
}
