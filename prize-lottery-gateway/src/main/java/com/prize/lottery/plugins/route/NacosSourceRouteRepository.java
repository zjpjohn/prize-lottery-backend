package com.prize.lottery.plugins.route;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prize.lottery.nacos.INacosConfigParser;
import com.prize.lottery.nacos.NacosConfigSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class NacosSourceRouteRepository implements SmartInitializingSingleton,
        RouteDefinitionRepository,
        ApplicationEventPublisherAware,
        INacosConfigParser {

    private final Map<String, RouteDefinition> routes = Maps.newConcurrentMap();
    private       ApplicationEventPublisher    applicationEventPublisher;

    private final String            dataId;
    private final String            group;
    private final NacosConfigSource configSource;

    public NacosSourceRouteRepository(String dataId, String group, NacosConfigSource configSource) {
        this.dataId       = dataId;
        this.group        = group;
        this.configSource = configSource;
    }

    @Override
    @SneakyThrows
    public void afterSingletonsInstantiated() {
        configSource.parseAndListen(this.dataId, this.group, this);
    }

    @Override
    public void parse(String config) {
        Map<String, RouteDefinition> routeDefinitionMap = Optional.ofNullable(config)
                                                                  .map(v -> JSON.parseArray(config, RouteDefinition.class))
                                                                  .orElse(Lists.newArrayList())
                                                                  .stream()
                                                                  .collect(Collectors.toMap(RouteDefinition::getId, Function.identity()));
        this.routes.putAll(routeDefinitionMap);
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (StringUtils.isBlank(r.getId())) {
                return Mono.error(new RuntimeException("rout id must not be empty."));
            }
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            routes.remove(id);
            return Mono.empty();
        });
    }

    public RouteDefinition getRoute(String routeId) {
        return routes.get(routeId);
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
