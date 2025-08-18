package com.prize.lottery.infrast.config;

import com.cloud.arch.web.ITokenBlackListPublisher;
import com.cloud.arch.web.ITokenCreator;
import com.cloud.arch.web.WebTokenProperties;
import com.cloud.arch.web.impl.JwtTokenCreator;
import com.prize.lottery.AuthSourceProperty;
import com.prize.lottery.LotteryAuthSource;
import com.prize.lottery.blacklist.RedisBlackListPublisher;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticateConfigurer {

    @Bean
    @ConfigurationProperties(prefix = WebTokenProperties.PROPS_PREFIX)
    public WebTokenProperties jwtAuthProperties() {
        return new WebTokenProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "com.cloud.web.auth.domain.user")
    public AuthSourceProperty sourceProperty() {
        return new AuthSourceProperty();
    }

    @Bean
    public ITokenCreator jwtTokenCreator(WebTokenProperties authProperties) {
        return new JwtTokenCreator(authProperties);
    }

    @Bean
    public LotteryAuthSource authSource(AuthSourceProperty property) {
        return new LotteryAuthSource(property);
    }

    @Bean
    public ITokenBlackListPublisher blackTokenPublisher(RedissonClient redissonClient) {
        return new RedisBlackListPublisher(redissonClient);
    }

}
