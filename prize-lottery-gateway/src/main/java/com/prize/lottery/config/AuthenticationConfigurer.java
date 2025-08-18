package com.prize.lottery.config;

import com.alibaba.fastjson2.JSON;
import com.cloud.arch.web.ITokenBlackListValidator;
import com.cloud.arch.web.WebTokenProperties;
import com.cloud.arch.web.impl.DefaultAuthSourceManager;
import com.google.common.collect.Maps;
import com.prize.lottery.AuthSourceProperty;
import com.prize.lottery.LotteryAuthSource;
import com.prize.lottery.blacklist.RedisBlackListValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
public class AuthenticationConfigurer {

    @Bean
    @ConfigurationProperties(prefix = "com.cloud.web.auth")
    public MultipleHttpSourceManager multipleHttpSourceManager() {
        return new MultipleHttpSourceManager();
    }

    @Bean
    public ITokenBlackListValidator blackListValidator(WebTokenProperties properties, RedissonClient redissonClient) {
        return new RedisBlackListValidator(properties, redissonClient);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MultipleHttpSourceManager extends DefaultAuthSourceManager
            implements DisposableBean, InitializingBean {

        private Map<String, AuthSourceProperty> domain = Maps.newHashMap();

        @Override
        public void afterPropertiesSet() throws Exception {
            this.domain.values().stream().map(LotteryAuthSource::new).forEach(this::addSource);
        }

        @Override
        public void destroy() throws Exception {
            domain = Maps.newHashMap();
        }
    }

}
