package com.prize.lottery.domain.admin.model;

import com.cloud.arch.web.IHttpAuthSource;
import com.cloud.arch.web.ITokenCreator;
import com.cloud.arch.web.TokenResult;
import com.cloud.arch.web.utils.RequestUtils;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Data
@NoArgsConstructor
public class AdminLogin {

    private Long          adminId;
    private String        tokenId;
    private LocalDateTime expireAt;
    private String        loginIp;
    private String        ipRegion;
    private LocalDateTime loginTime;
    private Integer       state;

    public AdminLogin(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * 退出登录
     */
    public AdminLogin loginOut() {
        AdminLogin adminLogin = new AdminLogin(this.adminId);
        adminLogin.setState(0);
        adminLogin.setExpireAt(LocalDateTime.now());
        adminLogin.setLoginTime(LocalDateTime.now());
        adminLogin.setLoginIp(RequestUtils.ipAddress());
        adminLogin.setTokenId(UUID.randomUUID().toString().replaceAll("-", ""));
        return adminLogin;
    }

    /**
     * 登录授权信息生成
     *
     * @param tokenCreator token生成器
     */
    public TokenResult login(ITokenCreator tokenCreator,
                             IHttpAuthSource authSource,
                             Function<String, String> ipRegion) {
        //token自定义payload信息
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(authSource.authKey(), this.adminId);
        //生成授权token
        TokenResult result = tokenCreator.create(authSource, claims);
        this.tokenId   = result.tokenId();
        this.loginIp   = RequestUtils.ipAddress();
        this.ipRegion  = ipRegion.apply(this.loginIp);
        this.loginTime = LocalDateTime.now();
        this.expireAt  = result.expireAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.state     = 1;
        return result;
    }

    /**
     * 判断当前登录是否有效
     */
    public boolean isValidLogin() {
        return StringUtils.hasText(this.tokenId) && this.loginTime.isAfter(LocalDateTime.now());
    }

}
