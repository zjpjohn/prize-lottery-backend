package com.prize.lottery.weixin;

import com.cloud.arch.http.HttpRequest;
import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.error.ThirdResponseHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeixinThirdAccountExecutor {

    private static final String SNS_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private final String appId;
    private final String appSecret;

    public WeixinThirdAccountExecutor(String appId, String appSecret) {
        this.appId     = appId;
        this.appSecret = appSecret;
    }

    /**
     * 临时授权码换取微信账户标识
     *
     * @param code 临时授权码
     */
    public String execute(String code) {
        String response;
        try {
            String requestApi = String.format(SNS_TOKEN_API, appId, appSecret, code);
            response = HttpRequest.instance().get(requestApi);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
            throw ThirdResponseHandler.THIRD_SERVER_ERROR.get();
        }
        SnsToken snsToken = JsonUtils.toBean(response, SnsToken.class);
        Assert.state(snsToken.isSuccess(), ThirdResponseHandler.WX_PAY_ERROR);
        return snsToken.getOpenid();
    }

    @Data
    public static class SnsToken {

        //错误码信息
        private String errcode;
        private String errmsg;

        //授权token信息
        private String access_token;
        private Long   expire_in;
        private String refresh_token;
        private String openid;
        private String scope;
        private String unionid;

        public boolean isSuccess() {
            return errcode == null || errcode.isEmpty() || "0".equals(errcode);
        }
    }
}
