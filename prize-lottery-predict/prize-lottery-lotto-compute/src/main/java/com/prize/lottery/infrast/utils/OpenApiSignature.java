package com.prize.lottery.infrast.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenApiSignature {

    public static final String APP_KEY       = "app_key";
    public static final String SIGNATURE_KEY = "sign";
    public static final String ALGORITHM_KEY = "SHA-1";

    /**
     * 请求参数签名
     */
    public static String signature(String appKey, String secret, Map<String, String> params) {
        return signature(appKey, secret, params, null);
    }

    /**
     * 对请求参数进行签名
     * 参数签名形式[body&]sign&params1&params2&...
     */
    public static String signature(String appKey, String secret, Map<String, String> params, String body) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(body)) {
            builder.append(body).append("&");
        }
        builder.append(secret);
        params.put(APP_KEY, appKey);
        Map<String, String> sortedParams = Maps.newTreeMap();
        sortedParams.putAll(params);
        sortedParams.forEach((key, value) -> builder.append("&").append(value));
        String signature = sha1(builder.toString());
        params.put(SIGNATURE_KEY, signature);
        return joinQuery(params);
    }

    /**
     * 请求参数拼接成k1=v1&k2=v2
     */
    public static String joinQuery(Map<String, String> params) {
        return params.entrySet()
                     .stream()
                     .map(entry -> entry.getKey() + "=" + entry.getValue())
                     .collect(Collectors.joining("&", "?", ""));
    }

    /**
     * 字符串sha1哈希计算
     */
    public static String sha1(String plain) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_KEY);
            digest.update(plain.getBytes(StandardCharsets.UTF_8));
            byte[]        target = digest.digest();
            StringBuilder hexStr = new StringBuilder();
            String        shaHex = "";
            for (byte value : target) {
                shaHex = Integer.toHexString(value & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
