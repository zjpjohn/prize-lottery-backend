package com.prize.lottery.domain.user.model;

import com.cloud.arch.web.IHttpAuthSource;
import com.cloud.arch.web.ITokenCreator;
import com.cloud.arch.web.TokenResult;
import com.cloud.arch.web.utils.RequestUtils;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Data
@NoArgsConstructor
public class UserLogin {

    private Long          userId;
    private String        deviceId;
    private String        tokenId;
    private LocalDateTime expireAt;
    private String        loginIp;
    private String        ipRegion;
    private LocalDateTime loginTime;
    private Integer       state;

    public UserLogin(Long userId) {
        this.userId = userId;
    }

    public UserLogin loginOut() {
        UserLogin userLogin = new UserLogin(this.userId);
        userLogin.setTokenId(UUID.randomUUID().toString().replaceAll("-", ""));
        userLogin.setExpireAt(LocalDateTime.now());
        userLogin.setLoginTime(LocalDateTime.now());
        userLogin.setLoginIp(RequestUtils.ipAddress());
        userLogin.setState(0);
        return userLogin;
    }

    /**
     * 登录生成授权信息
     *
     * @param jwtTokenCreator token生成器
     */
    public TokenResult login(ITokenCreator jwtTokenCreator,
                             IHttpAuthSource authSource,
                             String deviceId,
                             Function<String, String> ipRegion) {
        //token自定义payload信息
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(authSource.authKey(), this.userId);
        //生成token信息
        TokenResult result = jwtTokenCreator.create(authSource, claims);
        this.tokenId   = result.tokenId();
        this.loginIp   = RequestUtils.ipAddress();
        this.ipRegion  = ipRegion.apply(this.loginIp);
        this.loginTime = LocalDateTime.now();
        this.expireAt  = result.expireAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.state     = 1;
        this.deviceId  = deviceId;
        return result;
    }

    /**
     * 判断当前登录是否未过期
     */
    public boolean isValidToken() {
        return StringUtils.isNotBlank(this.tokenId) && this.expireAt.isAfter(LocalDateTime.now());
    }
}
