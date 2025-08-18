package com.prize.lottery.props;

import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cloud.payment.wxpay")
public class WxPayProperties {

    private String  appId;
    private String  apiV3Key;
    private String  merchantId;
    private String  privateKey;
    private String  serialNumber;
    private String  notifyUrl;
    private Boolean pay      = false;
    private Boolean transfer = false;

    public RSAAutoCertificateConfig toConfig() {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKey(privateKey)
                .merchantSerialNumber(serialNumber)
                .apiV3Key(apiV3Key)
                .build();
    }

}
