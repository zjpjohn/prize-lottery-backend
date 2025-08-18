package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.oss")
public class CloudOssProperties {

    /**
     * oss接口appId
     */
    private String appId;
    /**
     * oss接口密钥
     */
    private String secret;
    /**
     * oss接口endpoint
     */
    private String endpoint;
    /**
     * oss接口bucket
     */
    private String bucket;
    /**
     * oss请求host地址
     */
    private String host;
    /**
     * oss上传请求回调地址
     */
    private String callback;
    /**
     * oss绑定域名
     */
    private String domain;
    /**
     * policy过期时间
     */
    private Long   expire;
    /**
     * 请求前缀
     */
    private String prefix;
}
