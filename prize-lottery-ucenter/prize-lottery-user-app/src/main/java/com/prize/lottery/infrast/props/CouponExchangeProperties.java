package com.prize.lottery.infrast.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.user.coupon")
public class CouponExchangeProperties {

    private Integer ratio;
    private Integer throttle;

}
