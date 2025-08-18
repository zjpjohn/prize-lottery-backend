package com.prize.lottery.application.command.executor;

import com.cloud.arch.web.ITokenBlackListPublisher;
import com.prize.lottery.domain.admin.model.AdminLogin;
import com.prize.lottery.domain.admin.repository.IAdminLoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AdminAuthLoginOutExe {

    private final IAdminLoginRepository    adminLoginRepository;
    private final ITokenBlackListPublisher blackTokenPublisher;

    public AdminAuthLoginOutExe(IAdminLoginRepository adminLoginRepository,
                                ITokenBlackListPublisher blackTokenPublisher) {
        this.adminLoginRepository = adminLoginRepository;
        this.blackTokenPublisher  = blackTokenPublisher;
    }

    @Transactional
    public void execute(Long managerId) {
        AdminLogin adminLogin = adminLoginRepository.of(managerId);
        //上一次登录的token未失效，将未失效的token加入黑名单中标记失效
        if (adminLogin.isValidLogin()) {
            adminLoginRepository.save(adminLogin.loginOut());
            blackTokenPublisher.publish(adminLogin.getTokenId(), adminLogin.getExpireAt());
        }
    }

}
