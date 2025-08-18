package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.user.invite")
public class CloudLotteryProperties {

    /**
     * 流量主业务开关
     */
    private Integer agent;
    /**
     * 邀请链接模板
     */
    private String  uri;

}
