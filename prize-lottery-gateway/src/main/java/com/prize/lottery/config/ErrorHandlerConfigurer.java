package com.prize.lottery.config;

import com.prize.lottery.error.CustomErrorAttributes;
import com.prize.lottery.error.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;

@Configuration
@EnableConfigurationProperties({ServerProperties.class, WebProperties.class})
public class ErrorHandlerConfigurer {

    @Bean
    public CustomErrorAttributes errorAttributes() {
        return new CustomErrorAttributes();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalExceptionHandler globalExceptionHandler(ErrorAttributes errorAttributes,
                                                         ServerProperties serverProperties,
                                                         WebProperties webProperties,
                                                         ServerCodecConfigurer serverCodecConfigurer,
                                                         ApplicationContext applicationContext) {
        return new GlobalExceptionHandler(errorAttributes, webProperties.getResources(), serverProperties.getError(), applicationContext, serverCodecConfigurer);
    }

}
