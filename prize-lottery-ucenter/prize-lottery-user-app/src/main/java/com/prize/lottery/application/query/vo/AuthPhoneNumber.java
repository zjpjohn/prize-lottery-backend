package com.prize.lottery.application.query.vo;

import com.cloud.arch.encrypt.AESKit;
import com.prize.lottery.domain.user.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthPhoneNumber {

    private String phone;
    private String nonceStr;
    private String signature;

    public static AuthPhoneNumber of(String phone, String nonceStr) {
        String signature = AESKit.CBC.pkc7Enc(phone + nonceStr, UserInfo.AES_KEY, UserInfo.AES_IV);
        return new AuthPhoneNumber(phone, nonceStr, signature);
    }
}
