package com.prize.lottery.infrast.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import static com.prize.lottery.infrast.config.OpenForecastConfigurer.LOTTERY_SCHEDULER;


@Configuration
@EnableScheduling
public class SchedulerConfigurer implements SchedulingConfigurer {

    @Resource(name = LOTTERY_SCHEDULER)
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler);
    }
}
