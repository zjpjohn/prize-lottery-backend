package com.prize.lottery.infrast.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class SchedulerConfigurer implements SchedulingConfigurer {

    @Resource
    private ThreadPoolTaskScheduler paymentScheduler;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(paymentScheduler);
    }

}
