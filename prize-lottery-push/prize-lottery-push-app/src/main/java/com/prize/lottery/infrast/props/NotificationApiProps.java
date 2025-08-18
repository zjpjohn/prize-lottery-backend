package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "cloud.lottery.push")
public class NotificationApiProps {

    private String accessKey;
    private String accessSecret;
    private String regionId;

}
