package com.prize.lottery.config;

import com.prize.lottery.plugins.swagger.OpenApiSwagger;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled")
public class OpenApiSwaggerConfigurer {

    @Value("${spring.application.name}")
    private String gatewayName;

    @Bean
    public OpenApiSwagger openApiSwagger(SwaggerUiConfigProperties properties, RouteDefinitionRepository routeLocator) {
        return new OpenApiSwagger(gatewayName, properties, routeLocator);
    }

}
