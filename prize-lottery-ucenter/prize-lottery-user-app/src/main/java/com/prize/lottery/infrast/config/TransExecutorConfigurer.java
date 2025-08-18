package com.prize.lottery.infrast.config;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.executor.withdraw.WithdrawExecutor;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TransExecutorConfigurer {

    @Bean
    public EnumerableExecutorFactory<TransferScene, WithdrawExecutor> executorExecutorFactory() {
        return new EnumerableExecutorFactory<>(TransferScene.class, WithdrawExecutor.class);
    }

}
