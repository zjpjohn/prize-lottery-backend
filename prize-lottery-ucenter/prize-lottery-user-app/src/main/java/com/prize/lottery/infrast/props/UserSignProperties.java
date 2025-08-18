package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.user.sign")
public class UserSignProperties {

    private Integer series;
    private Integer throttle;
    private Integer every;

}
