package com.prize.lottery.blacklist;

import com.cloud.arch.web.ITokenBlackListValidator;
import com.cloud.arch.web.WebTokenProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.prize.lottery.LotteryAuth;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryCreatedListener;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.beans.factory.SmartInitializingSingleton;

public class RedisBlackListValidator implements ITokenBlackListValidator, SmartInitializingSingleton {

    private final Cache<String, Integer>     localCache;
    private final RMapCache<String, Integer> redisCache;

    public RedisBlackListValidator(WebTokenProperties properties, RedissonClient client) {
        this.redisCache = client.getMapCache(LotteryAuth.REDIS_BLACK_LIST_CACHE);
        this.localCache = Caffeine.newBuilder()
                                  .initialCapacity(1024)
                                  .maximumSize(8192)
                                  .expireAfterWrite(properties.getExpire())
                                  .build();
    }

    @Override
    public boolean validate(String tokenId) {
        return this.localCache.getIfPresent(tokenId) != null;
    }

    @Override
    public void afterSingletonsInstantiated() {
        redisCache.readAllEntrySetAsync()
                  .thenAccept(set -> set.forEach(entry -> localCache.put(entry.getKey(), entry.getValue())));
        //监听token过期事件
        redisCache.addListener((EntryExpiredListener<String, Integer>) event -> {
            this.localCache.invalidate(event.getKey());
        });
        //监听token添加事件
        redisCache.addListener((EntryCreatedListener<String, Integer>) event -> {
            this.redisCache.put(event.getKey(), event.getValue());
        });
    }
}
