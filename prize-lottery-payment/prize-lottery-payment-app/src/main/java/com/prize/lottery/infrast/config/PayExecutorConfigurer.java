package com.prize.lottery.infrast.config;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.executor.transfer.TransCheckExecutor;
import com.prize.lottery.application.command.executor.transfer.TransferExecutor;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.pay.UnionPayExecutor;
import com.prize.lottery.transfer.FundTransferExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PayExecutorConfigurer {

    @Bean
    public EnumerableExecutorFactory<PayChannel, UnionPayExecutor> unionPayExecutorFactory() {
        return new EnumerableExecutorFactory<>(PayChannel.class, UnionPayExecutor.class);
    }

    @Bean
    public EnumerableExecutorFactory<PayChannel, FundTransferExecutor> fundTransferExecutorFactory() {
        return new EnumerableExecutorFactory<>(PayChannel.class, FundTransferExecutor.class);
    }

    @Bean
    public EnumerableExecutorFactory<PayChannel, TransferExecutor> transferExecutorFactory() {
        return new EnumerableExecutorFactory<>(PayChannel.class, TransferExecutor.class);
    }

    @Bean
    public EnumerableExecutorFactory<PayChannel, TransCheckExecutor> transCheckExecutorFactory() {
        return new EnumerableExecutorFactory<>(PayChannel.class, TransCheckExecutor.class);
    }
}

