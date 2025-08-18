package com.prize.lottery.domain.transfer.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.TransRuleCreateCmd;
import com.prize.lottery.application.command.dto.TransRuleModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.RuleState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class TransferRuleDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 7751251863470792662L;

    private Long      id;
    private String    scene;
    private Long      throttle;
    private Integer   force;
    private RuleState state;
    private String    remark;

    public TransferRuleDo(TransRuleCreateCmd command, BiConsumer<TransRuleCreateCmd, TransferRuleDo> converter) {
        this.state = RuleState.CREATED;
        converter.accept(command, this);
    }

    /**
     * 编辑转账规则信息
     *
     * @param command 编辑命令
     */
    public void modify(TransRuleModifyCmd command) {
        RuleState state = command.getState();
        if (state != null) {
            Set<RuleState> transitions = this.state.transitions();
            Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
            this.state = state;
            return;
        }
        //仅已创建的规则允许操作
        Assert.state(this.state == RuleState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
        String remark = command.getRemark();
        if (StringUtils.isNotBlank(remark)) {
            this.remark = remark;
        }
        Integer force = command.getForce();
        if (force != null && !Objects.equals(this.force, force)) {
            this.force = force;
        }
        if (this.force == 1) {
            this.throttle = 0L;
            return;
        }
        Long throttle = command.getThrottle();
        if (throttle != null && throttle > 0) {
            this.throttle = throttle;
        }
        Assert.state(this.throttle != null && this.throttle > 0, ResponseHandler.AUDIT_THROTTLE_NONE_NULL);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
