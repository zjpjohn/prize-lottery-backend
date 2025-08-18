package com.prize.lottery.application.command.executor;

import com.cloud.arch.web.ITokenBlackListPublisher;
import com.prize.lottery.domain.user.model.UserLogin;
import com.prize.lottery.domain.user.repository.IUserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthLoginOutExecutor {

    private final IUserLoginRepository     userLoginRepository;
    private final ITokenBlackListPublisher blackTokenPublisher;

    @Transactional
    public void execute(Long userId) {
        UserLogin userLogin = userLoginRepository.ofId(userId);
        //失效上一次登录token，将token加入黑名单中
        if (userLogin.isValidToken()) {
            userLoginRepository.save(userLogin.loginOut());
            blackTokenPublisher.publish(userLogin.getTokenId(), userLogin.getExpireAt());
        }
    }

}
