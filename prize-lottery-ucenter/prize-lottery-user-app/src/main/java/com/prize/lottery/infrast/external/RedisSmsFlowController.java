package com.prize.lottery.infrast.external;

import com.cloud.arch.mobile.sms.SmsFlowController;
import com.cloud.arch.redis.RedissonTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisSmsFlowController implements SmsFlowController {

    //手机验证码key pattern
    public static final String CODE_KEY_PATTERN = "t:m:%s:%s";
    //手机验证码缓存map key
    public static final String CODE_CACHE_KEY   = "t:m:cache";

    private final RedissonTemplate redissonTemplate;

    public RedisSmsFlowController(RedissonTemplate redissonTemplate) {
        this.redissonTemplate = redissonTemplate;
    }

    @Override
    public void cacheCode(String phone, String channel, String code, Long expire, TimeUnit timeUnit) {
        String key = String.format(CODE_KEY_PATTERN, phone, channel);
        redissonTemplate.set(key, code, expire, timeUnit);
    }

    @Override
    public Boolean checkCode(String phone, String channel, String code) {
        String  key    = String.format(CODE_KEY_PATTERN, phone, channel);
        String  cached = redissonTemplate.get(key);
        Boolean result = Optional.ofNullable(cached).map(code::equals).orElse(Boolean.FALSE);
        if (result) {
            redissonTemplate.del(key);
        }
        return result;
    }

    @Override
    public Boolean flowLimit(String phone, String channel) {
        String key  = String.format(CODE_KEY_PATTERN, phone, channel);
        String code = redissonTemplate.get(key);
        return StringUtils.isNotBlank(code);
    }

}
