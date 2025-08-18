package com.prize.lottery.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.cloud.arch.web.utils.Assert;
import com.google.common.base.Charsets;
import com.prize.lottery.error.ThirdResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class AliPayThirdAccountExecutor implements InitializingBean {

    public static final String GRANT_TYPE = "authorization_code";

    private final AliPayProperties properties;

    private AlipayClient alipayClient;

    public AliPayThirdAccountExecutor(AliPayProperties properties) {
        this.properties = properties;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AlipayConfig config = new AlipayConfig();
        config.setFormat("json");
        config.setSignType("RSA2");
        config.setCharset(Charsets.UTF_8.name());
        config.setAppId(properties.getAppId());
        config.setServerUrl(properties.getServerUrl());
        config.setPrivateKey(properties.getPrivateKey());
        config.setAlipayPublicKey(properties.getAliPublicKey());
        try {
            this.alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException error) {
            log.error(error.getErrMsg(), error);
            throw new RuntimeException(error.getErrMsg(), error);
        }
    }

    /**
     * 临时授权code换取支付宝账户标识
     *
     * @param code 临时授权码
     */
    public String execute(String code) {
        AlipaySystemOauthTokenRequest oauthRequest = new AlipaySystemOauthTokenRequest();
        oauthRequest.setCode(code);
        oauthRequest.setGrantType(GRANT_TYPE);

        AlipaySystemOauthTokenResponse response;
        try {
            response = alipayClient.execute(oauthRequest);
        } catch (AlipayApiException error) {
            log.error(error.getErrMsg(), error);
            throw ThirdResponseHandler.THIRD_SERVER_ERROR.get();
        }
        Assert.state(response.isSuccess(), ThirdResponseHandler.ALI_PAY_ERROR);
        return response.getUserId();
    }

}
