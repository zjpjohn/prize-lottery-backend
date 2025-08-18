package com.prize.lottery.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.prize.lottery.nacos.NacosConfigSource;
import com.prize.lottery.nacos.NacosDataProperty;
import com.prize.lottery.plugins.access.repository.IReqAccessRepository;
import com.prize.lottery.plugins.access.repository.impl.NacosReqAccessRepository;
import com.prize.lottery.plugins.route.NacosSourceRouteRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NacosDataProperty.class)
public class GatewayNacosConfigurer {

    @Bean
    public NacosConfigSource configSource(NacosConfigManager manager, NacosConfigProperties properties) {
        return new NacosConfigSource(manager, properties);
    }

    @Bean
    public NacosSourceRouteRepository routeRepository(NacosDataProperty property, NacosConfigSource configSource) {
        NacosDataProperty.Config config = property.getRoute();
        return new NacosSourceRouteRepository(config.getDataId(), config.getGroup(), configSource);
    }

    @Bean
    public IReqAccessRepository reqAccessRepository(NacosDataProperty property, NacosConfigSource configSource) {
        NacosDataProperty.Config access = property.getAccess();
        return new NacosReqAccessRepository(access.getDataId(), access.getGroup(), configSource);
    }

}
