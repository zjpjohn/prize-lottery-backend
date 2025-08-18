package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AdminCreateCmd;
import com.prize.lottery.application.command.dto.AdminLoginCmd;
import com.prize.lottery.application.command.dto.AdminPasswordCmd;
import com.prize.lottery.application.command.dto.ResetPasswordCmd;
import com.prize.lottery.application.vo.AdminAuthVo;

public interface IAdminCommandService {

    AdminAuthVo adminAuth(AdminLoginCmd command);

    void loginOut(Long managerId);

    void addAdministrator(AdminCreateCmd command);

    void resetMinePassword(ResetPasswordCmd command);

    void resetAdminPassword(AdminPasswordCmd command);

    void frozenAdministrator(Long id);

    void unfrozenAdministrator(Long id);

    void invalidateAdministrator(Long id);

}
