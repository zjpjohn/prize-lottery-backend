package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.admin.model.AdminLogin;
import com.prize.lottery.domain.admin.repository.IAdminLoginRepository;
import com.prize.lottery.infrast.persist.mapper.AdministratorMapper;
import com.prize.lottery.infrast.persist.po.AdminLoginPo;
import com.prize.lottery.infrast.repository.converter.AdminLoginConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminLoginRepository implements IAdminLoginRepository {

    private final AdministratorMapper administratorMapper;
    private final AdminLoginConverter converter;

    @Override
    public void save(AdminLogin login) {
        //保存本次登录信息
        administratorMapper.addAdminLogin(converter.to(login));
        //仅保存登陆日志，退出登录日志不保存
        if (login.getLoginTime() != null && login.getState() == 1) {
            administratorMapper.addAdminLoginLog(converter.toLog(login));
        }
    }

    @Override
    public AdminLogin of(Long adminId) {
        AdminLoginPo adminLogin = administratorMapper.getAdminLogin(adminId);
        return Optional.ofNullable(adminLogin).map(converter::from).orElseGet(() -> new AdminLogin(adminId));
    }
}
