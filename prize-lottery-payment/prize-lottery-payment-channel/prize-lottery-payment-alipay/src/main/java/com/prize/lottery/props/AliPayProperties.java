package com.prize.lottery.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;

@Data
@ConfigurationProperties(prefix = "cloud.payment.alipay")
public class AliPayProperties {

    private String  privateKey;
    private String  publicKey;
    private String  serverUrl;
    private String  appId;
    private String  charset  = StandardCharsets.UTF_8.name();
    private String  signType = "RSA2";
    private String  format   = "json";
    private String  notifyUrl;
    private Integer sandbox  = 0;
    private Boolean pay      = false;
    private Boolean transfer = false;

}
