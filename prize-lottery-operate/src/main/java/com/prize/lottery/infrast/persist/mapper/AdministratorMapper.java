package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AdminLoginLogPo;
import com.prize.lottery.infrast.persist.po.AdminLoginPo;
import com.prize.lottery.infrast.persist.po.AdministratorPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdministratorMapper {

    int addAdministrator(AdministratorPo administrator);

    int editAdministrator(AdministratorPo administrator);

    AdministratorPo getAdministratorById(Long id);

    AdministratorPo getAdministratorByName(String name);

    List<AdministratorPo> getAllAdministrators();

    List<AdministratorPo> getAdministratorsWithout(Long managerId);

    int addAdminLogin(AdminLoginPo login);

    AdminLoginPo getAdminLogin(Long adminId);

    int addAdminLoginLog(AdminLoginLogPo loginLog);

    List<AdminLoginLogPo> getAdminLoginLogs(PageCondition condition);

    int countAdminLoginLogs(PageCondition condition);

}
