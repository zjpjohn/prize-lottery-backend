package com.prize.lottery.infrast.config;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.domain.glad.extractor.GladExtractor;
import com.prize.lottery.domain.index.ability.IndexAbility;
import com.prize.lottery.domain.omit.ability.executor.CodeExecutor;
import com.prize.lottery.domain.omit.ability.executor.OmitExecutor;
import com.prize.lottery.enums.LotteryEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LottoExecutorConfigurer {

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, FeedsExtractor> feedsExtractorFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, FeedsExtractor.class);
    }

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, GladExtractor> gladExecutorFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, GladExtractor.class);
    }

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, ICaiDomainAbility> iCaiDomainAbilityFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, ICaiDomainAbility.class);
    }

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, IMasterEvictAbility> masterEvictAbilityFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, IMasterEvictAbility.class);
    }

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, OmitExecutor> qlcOmitExecutorFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, OmitExecutor.class);
    }

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, IndexAbility> indexAbilityFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, IndexAbility.class);
    }

    @Bean
    public EnumerableExecutorFactory<LotteryEnum, CodeExecutor> codeExecutorFactory() {
        return new EnumerableExecutorFactory<>(LotteryEnum.class, CodeExecutor.class);
    }
}
