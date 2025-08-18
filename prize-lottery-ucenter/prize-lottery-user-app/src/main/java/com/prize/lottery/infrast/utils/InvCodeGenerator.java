package com.prize.lottery.infrast.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class InvCodeGenerator {

    //字符串集合
    public static final String CHAR_CONTAINER = "2AaRr3BbKkSs4CcLTt5DdMmUu6EeNnVv7FPWw8GQXx9HhZz";

    /**
     * 生成邀请码 生成规则:随机数(4~5位)+时间戳(5~6位)
     */
    public static String generate() {
        //时间戳5~6位
        String suffix = Long.valueOf(System.currentTimeMillis() % 1000000).toString();
        //随机数4~5位
        int  base   = suffix.length() < 6 ? 100000 : 10000;
        Long prefix = Double.valueOf(Math.random() * base).longValue();
        long uid    = Double.valueOf(prefix + suffix).longValue();
        return encode(uid, 6);
    }

    /**
     * 生成8位渠道码
     */
    public static String channelCode() {
        //时间戳6~7位
        String suffix = Long.valueOf(System.currentTimeMillis() % 10000000).toString();
        //随机数5~6位
        int  base   = suffix.length() < 6 ? 1000000 : 100000;
        Long prefix = Double.valueOf(Math.random() * base).longValue();
        long uid    = Double.valueOf(prefix + suffix).longValue();
        return encode(uid, 8);
    }

    private static String encode(long uid, int throttle) {
        int           remainder = 0, radix = CHAR_CONTAINER.length();
        StringBuilder builder   = new StringBuilder();
        while (uid > radix - 1) {
            remainder = Long.valueOf(uid % radix).intValue();
            builder.append(CHAR_CONTAINER.charAt(remainder));
            uid = uid / radix;
        }
        builder.append(CHAR_CONTAINER.charAt((int)uid));
        int length = builder.length();
        if (length < throttle) {
            int    size   = throttle - length;
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                int index = random.nextInt(CHAR_CONTAINER.length());
                builder.append(CHAR_CONTAINER.charAt(index));
            }
        }
        return builder.toString();
    }

}
