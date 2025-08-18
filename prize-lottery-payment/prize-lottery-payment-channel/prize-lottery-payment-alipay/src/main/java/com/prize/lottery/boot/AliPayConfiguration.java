package com.prize.lottery.boot;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.prize.lottery.pay.AliUnionPayExecutor;
import com.prize.lottery.pay.UnionPayExecutor;
import com.prize.lottery.props.AliPayProperties;
import com.prize.lottery.transfer.AliPayFundTransferExecutor;
import com.prize.lottery.transfer.FundTransferExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(AliPayProperties.class)
@ConditionalOnProperty(prefix = "cloud.payment.alipay", name = {"private-key", "server-url", "app-id", "public-key"})
public class AliPayConfiguration {

    @Bean
    public AlipayClient alipayClient(AliPayProperties properties) {
        return new DefaultAlipayClient(properties.getServerUrl(), properties.getAppId(), properties.getPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getPublicKey(), properties.getSignType());

    }

    @Bean
    @ConditionalOnProperty(prefix = "cloud.payment.alipay", name = "pay", havingValue = "true")
    public UnionPayExecutor aliUnionPayExecutor(AliPayProperties properties, AlipayClient alipayClient) {
        return new AliUnionPayExecutor(properties, alipayClient);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloud.payment.alipay", name = "transfer", havingValue = "true")
    public FundTransferExecutor alipayTransferExecutor(AlipayClient alipayClient) {
        return new AliPayFundTransferExecutor(alipayClient);
    }

}
