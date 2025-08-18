package com.prize.lottery.infrast.props;

import com.prize.lottery.domain.transfer.model.specs.TransferRuleSpec;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cloud.payment.transfer")
public class DefaultRuleProperties {

    /**
     * 提现金额审核门槛
     */
    private Long    throttle = 1000L;
    /**
     * 是否强制审核:0-否,1-是
     */
    private Integer force    = 0;

    public TransferRuleSpec toRule() {
        return new TransferRuleSpec(this.throttle, this.force);
    }

}
