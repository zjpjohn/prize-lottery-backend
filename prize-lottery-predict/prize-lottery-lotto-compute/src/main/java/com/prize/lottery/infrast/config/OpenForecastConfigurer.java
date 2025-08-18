package com.prize.lottery.infrast.config;

import com.cloud.arch.oss.store.OssStorageTemplate;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.facade.impl.HttpRequestWrapper;
import com.prize.lottery.infrast.props.OpenApiProperties;
import com.prize.lottery.infrast.spider.around.Fc3dAroundSpider;
import com.prize.lottery.infrast.spider.news.SinaLotteryNewsSpider;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.infrast.spider.open.OpenLayerFetcher;
import com.prize.lottery.infrast.spider.open.impl.*;
import com.prize.lottery.infrast.spider.skill.LotterySkillSpider;
import com.prize.lottery.mapper.LotteryDanMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.LotteryNewsMapper;
import com.prize.lottery.mapper.LotterySkillMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenForecastConfigurer {

    public static final String LOTTERY_SCHEDULER     = "lotteryScheduler";
    public static final String SPIDER_ASYNC_EXECUTOR = "asyncServiceExecutor";
    public static final String FC3D_FETCHER          = "fc3dOpenFetcher";
    public static final String PL3_FETCHER           = "pl3OpenFetcher";
    public static final String SSQ_FETCHER           = "ssqOpenFetcher";
    public static final String DLT_FETCHER           = "dltOpenFetcher";
    public static final String QLC_FETCHER           = "qlcOpenFetcher";

    @Resource(name = "DltOpenFetchHandler")
    private DltOpenFetchHandler  dltOpenFetchHandler;
    @Resource(name = "Fc3dOpenFetchHandler")
    private Fc3dOpenFetchHandler fc3dOpenFetchHandler;
    @Resource(name = "Pl3OpenFetchHandler")
    private Pl3OpenFetchHandler  pl3OpenFetchHandler;
    @Resource(name = "QlcOpenFetchHandler")
    private QlcOpenFetchHandler  qlcOpenFetchHandler;
    @Resource(name = "SsqOpenFetchHandler")
    private SsqOpenFetchHandler  ssqOpenFetchHandler;


    @Bean(name = LOTTERY_SCHEDULER)
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(16);
        executor.setThreadNamePrefix("lottery-scheduling-");
        executor.setAwaitTerminationSeconds(600);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


    @Bean(SPIDER_ASYNC_EXECUTOR)
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("cloud-lottery-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public SinaLotteryNewsSpider sinaLotteryNewsSpider(OssStorageTemplate storageTemplate,
                                                       Executor asyncServiceExecutor,
                                                       HttpRequestWrapper requestWrapper,
                                                       LotteryNewsMapper lotteryNewsMapper) {
        return new SinaLotteryNewsSpider(asyncServiceExecutor, requestWrapper, storageTemplate, lotteryNewsMapper);
    }

    @Bean
    public LotterySkillSpider lotterySkillSpider(OssStorageTemplate storageTemplate,
                                                 Executor asyncServiceExecutor,
                                                 HttpRequestWrapper requestWrapper,
                                                 LotterySkillMapper lotterySkillMapper) {
        return new LotterySkillSpider(asyncServiceExecutor, requestWrapper, storageTemplate, lotterySkillMapper);
    }

    @Bean
    public Fc3dAroundSpider fc3dAroundSpider(Executor asyncServiceExecutor,
                                             LotteryDanMapper aroundMapper,
                                             LotteryInfoMapper lotteryMapper) {
        return new Fc3dAroundSpider(asyncServiceExecutor, aroundMapper, lotteryMapper);
    }

    @Bean(name = FC3D_FETCHER)
    public OpenForecastFetcher fc3dForecastFetcher(Executor asyncServiceExecutor,
                                                   OpenApiProperties properties,
                                                   HttpRequestWrapper requestWrapper) {
        return new OpenForecastFetcher(LotteryEnum.FC3D, asyncServiceExecutor, properties, fc3dOpenFetchHandler, requestWrapper);
    }

    @Bean
    public OpenLayerFetcher openLayerFetcher(OpenApiProperties properties, HttpRequestWrapper requestWrapper) {
        return new OpenLayerFetcher(properties, requestWrapper);
    }

    @Bean(name = PL3_FETCHER)
    public OpenForecastFetcher pl3ForecastFetcher(Executor asyncServiceExecutor,
                                                  OpenApiProperties properties,
                                                  HttpRequestWrapper requestWrapper) {
        return new OpenForecastFetcher(LotteryEnum.PL3, asyncServiceExecutor, properties, pl3OpenFetchHandler, requestWrapper);
    }

    @Bean(name = DLT_FETCHER)
    public OpenForecastFetcher dltForecastFetcher(Executor asyncServiceExecutor,
                                                  OpenApiProperties properties,
                                                  HttpRequestWrapper requestWrapper) {
        return new OpenForecastFetcher(LotteryEnum.DLT, asyncServiceExecutor, properties, dltOpenFetchHandler, requestWrapper);
    }

    @Bean(name = SSQ_FETCHER)
    public OpenForecastFetcher ssqForecastFetcher(Executor asyncServiceExecutor,
                                                  OpenApiProperties properties,
                                                  HttpRequestWrapper requestWrapper) {
        return new OpenForecastFetcher(LotteryEnum.SSQ, asyncServiceExecutor, properties, ssqOpenFetchHandler, requestWrapper);
    }

    @Bean(name = QLC_FETCHER)
    public OpenForecastFetcher qlcForecastFetcher(Executor asyncServiceExecutor,
                                                  OpenApiProperties properties,
                                                  HttpRequestWrapper requestWrapper) {
        return new OpenForecastFetcher(LotteryEnum.QLC, asyncServiceExecutor, properties, qlcOpenFetchHandler, requestWrapper);
    }

}
