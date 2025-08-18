package com.prize.lottery.infrast.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public enum VerifyCode {

    MOBILE {
        @Override
        public String code(int size) {
            return generateCode(size, PHONE_CODE_COLLECTION);
        }
    },
    IMAGE {
        @Override
        public String code(int size) {
            return generateCode(size, IMAGE_CODE_COLLECTION);
        }
    };

    public abstract String code(int size);

    /* 手机验证码数字集合 */
    private static final String PHONE_CODE_COLLECTION = "1234567890";

    /* 图片验证码字符集合 */
    private static final String IMAGE_CODE_COLLECTION = "0Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9JjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    /**
     * 生成验证码字符串
     *
     * @param num     验证码长度
     * @param sources 生成验证码集合
     */
    private static String generateCode(int num, String sources) {
        Preconditions.checkState(StringUtils.isNotBlank(sources));
        Random        random     = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(num);
        int           codesLen   = sources.length();
        for (int i = 0; i < num; i++) {
            verifyCode.append(sources.charAt(random.nextInt(codesLen)));
        }
        return verifyCode.toString();
    }

    /**
     * 生成指定长度的token
     *
     * @param num token长度
     */
    public static String generateToken(Integer num) {
        return generateCode(num, IMAGE_CODE_COLLECTION);
    }

}
