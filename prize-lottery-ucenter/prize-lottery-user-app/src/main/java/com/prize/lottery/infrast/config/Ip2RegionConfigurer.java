package com.prize.lottery.infrast.config;

import com.cloud.arch.Ip2RegionSearcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Ip2RegionConfigurer {

    @Bean
    public Ip2RegionSearcher ip2RegionSearcher() {
        return new Ip2RegionSearcher();
    }

}
