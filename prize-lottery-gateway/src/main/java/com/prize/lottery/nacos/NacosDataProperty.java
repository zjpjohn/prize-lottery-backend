package com.prize.lottery.nacos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.cloud.gateway")
public class NacosDataProperty {

    //路由配置
    private Config route;
    //请求黑白名单配置
    private Config access;

    @Data
    public static class Config {
        private String dataId;
        private String group;
    }

}
