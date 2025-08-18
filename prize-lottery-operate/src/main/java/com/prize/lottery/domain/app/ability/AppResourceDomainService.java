package com.prize.lottery.domain.app.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.redis.RedissonTemplate;
import com.prize.lottery.domain.app.model.AppResourceDo;
import com.prize.lottery.domain.app.repository.IAppResourceRepository;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.persist.enums.ResourceState;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import com.prize.lottery.infrast.persist.valobj.AppResource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.redisson.api.RMap;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AppResourceDomainService {

    public static final String APP_RESOURCE_KEY_PATTERN = "app:resource:%s";

    private final IAppResourceRepository repository;
    private final RedissonTemplate       redissonTemplate;

    private String cacheKey(String appNo) {
        return String.format(APP_RESOURCE_KEY_PATTERN, appNo);
    }

    /**
     * 加载资源到redis缓存
     */
    public void loadResources(String appNo) {
        List<AppResourcePo> resources = repository.getUsingAppResources(appNo);
        if (CollectionUtils.isEmpty(resources)) {
            return;
        }
        String               key   = this.cacheKey(appNo);
        RMap<Object, Object> cache = redissonTemplate.getMap(key);
        Map<String, AppResource> resMap = resources.stream()
                                                   .map(v -> Pair.of(v.getFeNo(), new AppResource(v)))
                                                   .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        cache.putAll(resMap);
    }

    /**
     * 获取应用全部资源
     *
     * @param appNo 应用标识
     */
    public Map<String, AppResource> getAppResources(String appNo) {
        String                    key           = this.cacheKey(appNo);
        RMap<String, AppResource> resourceCache = redissonTemplate.getMap(key);
        return resourceCache.getAll(resourceCache.keySet());
    }

    /**
     * 更新资源信息
     *
     * @param id  资源标识
     * @param val 资源值
     */
    public void modify(Long id, AppResourceVal val) {
        Aggregate<Long, AppResourceDo> aggregate = repository.ofId(id);
        aggregate.peek(root -> root.newResource(val)).save(repository::save);
        AppResourceDo root = aggregate.getRoot();
        if (isResourceModified(val) && root.getState() == ResourceState.USING) {
            AppResourceVal resource    = root.getResource();
            AppResource    appResource = resource.toResource();
            String         key         = this.cacheKey(resource.getAppNo());
            redissonTemplate.put(key, resource.getFeNo(), appResource);
        }
    }

    private boolean isResourceModified(AppResourceVal val) {
        return StringUtils.isNotBlank(val.getUri())
                || StringUtils.isNotBlank(val.getDefUri())
                || val.getSpecs() != null;
    }

    /**
     * 使用资源
     */
    public void useResource(Long id) {
        repository.ofId(id).peek(AppResourceDo::useResource).save(repository::save).peek(root -> {
            AppResourceVal resource    = root.getResource();
            AppResource    appResource = resource.toResource();
            String         key         = this.cacheKey(resource.getAppNo());
            redissonTemplate.put(key, resource.getFeNo(), appResource);
        });
    }

    /**
     * 回滚资源
     *
     * @param id 资源标识
     */
    public void rollbackResource(Long id) {
        repository.ofId(id).peek(AppResourceDo::rollbackResource).save(repository::save).peek(root -> {
            if (root.getState() == ResourceState.USING) {
                //如果正在使用的资源要重置缓存
                AppResourceVal resource    = root.getResource();
                AppResource    appResource = resource.toResource();
                String         key         = this.cacheKey(resource.getAppNo());
                redissonTemplate.put(key, resource.getFeNo(), appResource);
            }
        });
    }

    /**
     * 下线资源
     */
    public void unUseResource(Long id) {
        repository.ofId(id).peek(AppResourceDo::underResource).save(repository::save).peek(root -> {
            AppResourceVal resource = root.getResource();
            String         key      = this.cacheKey(resource.getAppNo());
            redissonTemplate.remove(key, resource.getFeNo());
        });
    }

}
