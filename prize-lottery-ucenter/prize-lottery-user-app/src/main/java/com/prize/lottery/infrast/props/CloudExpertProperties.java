package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.expert")
public class CloudExpertProperties {

    //专家收益奖励金币
    private Map<String, Integer> expertAward;

}
