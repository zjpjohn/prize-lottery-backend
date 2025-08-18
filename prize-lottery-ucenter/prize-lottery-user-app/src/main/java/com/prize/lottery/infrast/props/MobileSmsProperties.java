package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.mobile.sms")
public class MobileSmsProperties {
    /**
     * 短信签名
     */
    private String              sign;
    /**
     * 短信验证码到期时间
     */
    private Long                expire = 300L;
    /**
     * 短信模板集合
     */
    private Map<String, String> templates;

}
