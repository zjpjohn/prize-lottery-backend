package com.prize.lottery.boot;

import com.prize.lottery.pay.UnionPayExecutor;
import com.prize.lottery.pay.WxUnionPayExecutor;
import com.prize.lottery.props.WxPayProperties;
import com.prize.lottery.transfer.FundTransferExecutor;
import com.prize.lottery.transfer.WxPayFundTransferExecutor;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
@ConditionalOnProperty(
        prefix = "cloud.payment.wxpay", name = {"app-id", "merchant-id", "private-key", "serial-number", "api-v3-key"})
public class WxPayConfiguration {

    @Bean
    public RSAAutoCertificateConfig rsaConfig(WxPayProperties properties) {
        return properties.toConfig();
    }

    @Slf4j
    @Configuration
    @EnableConfigurationProperties(WxPayProperties.class)
    @ConditionalOnProperty(prefix = "cloud.payment.wxpay", name = "pay", havingValue = "true")
    public static class WxUnionPayConfiguration {

        @Bean
        public AppServiceExtension appServiceExtension(RSAAutoCertificateConfig config) {
            return new AppServiceExtension.Builder().config(config).build();
        }

        @Bean
        public NotificationParser notificationParser(RSAAutoCertificateConfig config) {
            return new NotificationParser(config);
        }

        @Bean
        public UnionPayExecutor wxUnionPayExecutor(AppServiceExtension appService,
                                                   NotificationParser notificationParser,
                                                   WxPayProperties properties) {
            return new WxUnionPayExecutor(properties, appService, notificationParser);
        }

    }

    @Slf4j
    @Configuration
    @EnableConfigurationProperties(WxPayProperties.class)
    @ConditionalOnProperty(prefix = "cloud.payment.wxpay", name = "transfer", havingValue = "true")
    public static class WxPayTransferConfiguration {

        @Bean
        public TransferBatchService transferService(RSAAutoCertificateConfig config) {
            return new TransferBatchService.Builder().config(config).build();
        }

        @Bean
        public FundTransferExecutor wxpayTransferExecutor(WxPayProperties properties,
                                                          TransferBatchService transferService) {
            return new WxPayFundTransferExecutor(properties, transferService);
        }
    }
}
