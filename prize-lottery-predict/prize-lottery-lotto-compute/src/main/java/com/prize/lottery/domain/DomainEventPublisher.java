package com.prize.lottery.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Slf4j
@Component
public class DomainEventPublisher implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static void publish(Object event) {
        if (event == null) {
            log.warn("domain event is null,please confirm correct event data!");
            return;
        }
        applicationContext.publishEvent(event);
    }

}
