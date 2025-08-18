package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.cloud.open")
public class OpenApiProperties {

    private String appKey;
    private String appSecret;
    private String baseUri;

}
