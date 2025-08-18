package com.prize.lottery.plugins.swagger;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationListener;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class OpenApiSwagger implements ApplicationListener<RefreshRoutesEvent> {

    private static final String API_URI = "v3/api-docs";

    private final String                    gatewayName;
    private final SwaggerUiConfigProperties properties;
    private final RouteDefinitionRepository routeLocator;

    public void discoverService() {
        routeLocator.getRouteDefinitions()
                    .filter(route -> route.getUri().getHost() != null)
                    .filter(route -> !gatewayName.equals(route.getUri().getHost()))
                    .flatMap(route -> {
                        List<Triple<String, String, String>> pairs = parsePaths(route);
                        return Flux.fromIterable(pairs);
                    })
                    .filter(distinct(Triple::getRight))
                    .subscribe(v -> {
                        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
                        swaggerUrl.setUrl(v.getRight());
                        swaggerUrl.setName(v.getLeft());
                        swaggerUrl.setDisplayName(v.getMiddle());
                        properties.getUrls().add(swaggerUrl);
                    });
    }

    @Override
    public void onApplicationEvent(RefreshRoutesEvent event) {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = properties.getUrls();
        if (urls == null) {
            properties.setUrls(Sets.newHashSet());
        }
        properties.getUrls().clear();
        this.discoverService();
    }

    /**
     * 解析服务swagger文档路径
     */
    private List<Triple<String, String, String>> parsePaths(RouteDefinition route) {
        String routeId = route.getId();
        String name = Optional.ofNullable(route.getMetadata())
                              .map(meta -> meta.get("name"))
                              .map(Object::toString)
                              .orElse(routeId);
        return route.getPredicates().stream().map(v -> {
            String pattern = v.getArgs().get("pattern").replaceAll("\\*", "");
            return Triple.of(routeId, name, pattern + API_URI);
        }).collect(Collectors.toList());
    }

    private <T> Predicate<T> distinct(Function<? super T, ?> function) {
        Set<Object> seen = Sets.newHashSet();
        return t -> seen.add(function.apply(t));
    }
}

