package com.prize.lottery.infrast.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class CloudForecastProperties {

    /**
     * 查看专家预测消耗奖励金
     */
    @Value("${cloud.forecast.expend}")
    private Integer expend;
    /**
     * 查看系统推荐预测消耗奖励金
     */
    @Value("${cloud.forecast.recommend}")
    private Integer recommend;
    /**
     * 单次金币最多抵扣额度
     */
    @Value("${cloud.forecast.bounty}")
    private Integer bounty;
}
