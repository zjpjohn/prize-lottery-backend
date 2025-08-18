package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.prize.lottery.application.query.dto.AdminPwdQuery;
import com.prize.lottery.application.query.dto.LoginLogQuery;
import com.prize.lottery.application.vo.AdminDetailVo;
import com.prize.lottery.infrast.persist.po.AdminLoginLogPo;
import com.prize.lottery.infrast.persist.po.AdministratorPo;

import java.util.List;

public interface IAdminQueryService {

    AdminDetailVo getAdministratorDetail(Long id, Long managerId);

    AdminDetailVo getAdminDetail(Long managerId);

    List<AdministratorPo> getAdministratorList(Long managerId);

    Page<AdminLoginLogPo> getLoginLogList(PageCondition condition);

    Page<AdminLoginLogPo> getAdminLoginLogs(LoginLogQuery query);

    String passwordQuery(AdminPwdQuery query);

}
