package com.prize.lottery.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NacosConfigSource {

    private final NacosConfigManager    manager;
    private final NacosConfigProperties properties;

    public NacosConfigSource(NacosConfigManager manager, NacosConfigProperties properties) {
        this.manager    = manager;
        this.properties = properties;
    }

    public void parseAndListen(String dataId, String group, INacosConfigParser parser) throws NacosException {
        ConfigService configService = this.manager.getConfigService();
        parser.parse(configService.getConfig(dataId, group, properties.getTimeout()));
        configService.addListener(dataId, group, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String data) {
                parser.parse(data);
            }
        });
    }
}
