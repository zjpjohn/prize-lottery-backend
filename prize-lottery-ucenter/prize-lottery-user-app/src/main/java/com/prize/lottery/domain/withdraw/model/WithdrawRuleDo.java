package com.prize.lottery.domain.withdraw.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.WithRuleCreateCmd;
import com.prize.lottery.application.command.dto.WithRuleEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.WithdrawRuleState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class WithdrawRuleDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -1123326444283523692L;

    private Long              id;
    private String            scene;
    private Long              throttle;
    private Long              maximum;
    private Integer           interval;
    private String            remark;
    private WithdrawRuleState state;
    private LocalDateTime     startTime;

    public WithdrawRuleDo(WithRuleCreateCmd command, BiConsumer<WithRuleCreateCmd, WithdrawRuleDo> converter) {
        this.state = WithdrawRuleState.CREATED;
        converter.accept(command, this);
    }

    public void modify(WithRuleEditCmd command, BiConsumer<WithRuleEditCmd, WithdrawRuleDo> converter) {
        WithdrawRuleState state = command.getState();
        if (state != null) {
            this.stateSwitch(state, command.getStartTime());
            return;
        }
        //仅处于创建的规则允许操作
        Assert.state(this.state == WithdrawRuleState.CREATED, ResponseHandler.WITHDRAW_RULE_CREATED_CAN_EDIT);
        converter.accept(command, this);
        //提现最大值不小于提现门槛
        Assert.state(this.throttle < this.maximum, ResponseHandler.RULE_MAX_ERROR);
    }

    /**
     * 规则状态切换
     *
     * @param state     转换后的状态
     * @param startTime 预发布时指定启用的时间
     */
    private void stateSwitch(WithdrawRuleState state, LocalDateTime startTime) {
        Set<WithdrawRuleState> transitions = this.state.transitions();
        Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = state;
        if (this.state == WithdrawRuleState.ISSUED) {
            //启用规则，设置规则启用时间
            this.startTime = LocalDateTime.now();
            WithdrawRuleHint.of(this).pushUseMessage();
        } else if (this.state == WithdrawRuleState.PRE_ISSUED) {
            //规则预发布设置规则预发布时间
            Assert.notNull(startTime, ResponseHandler.RULE_PRE_START_TIME_NONE);
            this.startTime = startTime;
            WithdrawRuleHint.of(this).pushPreMessage();
        }

    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
