package com.prize.lottery.blacklist;

import com.cloud.arch.web.ITokenBlackListPublisher;
import com.prize.lottery.LotteryAuth;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public class RedisBlackListPublisher implements ITokenBlackListPublisher {

    private final RMapCache<String, Integer> blackList;

    public RedisBlackListPublisher(RedissonClient client) {
        this.blackList = client.getMapCache(LotteryAuth.REDIS_BLACK_LIST_CACHE);
    }

    @Override
    public void publish(String tokenId, LocalDateTime expireAt) {
        long expireMillis = expireAt.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long expireTime   = expireMillis - System.currentTimeMillis();
        if (expireTime > 0) {
            blackList.put(tokenId, LotteryAuth.TOKEN_INDEX_VALUE, expireTime, TimeUnit.MILLISECONDS);
        }
    }

}
