package com.prize.lottery.domain.admin.repository;


import com.prize.lottery.domain.admin.model.AdminLogin;

public interface IAdminLoginRepository {

    void save(AdminLogin login);

    AdminLogin of(Long adminId);

}
