package com.prize.lottery.domain.mobile.domain;

import com.cloud.arch.mobile.sms.SmsParam;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.utils.VerifyCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class CloudSmsDo {

    private final String phone;
    private final String bizId;

    public CloudSmsDo(String phone) {
        this.phone = phone;
        this.bizId = String.valueOf(IdWorker.nextId());
    }

    /**
     * 构建发送短信参数
     *
     * @param sign     短信签名
     * @param template 短信模板
     */
    public SmsParam buildParam(String sign, String template) {
        SmsParam param = new SmsParam();
        param.setPhone(phone);
        param.setBizId(bizId);
        param.setCode(VerifyCode.MOBILE.code(6));
        param.setSignName(sign);
        Assert.state(StringUtils.isNotBlank(template), "短信模板为空");
        param.setTemplate(template);
        return param;
    }


}
