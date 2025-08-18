package com.prize.lottery.domain.order.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ChargeConfCreateCmd;
import com.prize.lottery.application.command.dto.ChargeConfEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class ChargeConfDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 7544376188571049216L;

    private Long        id;
    private String      name;
    private Long        amount;
    private Long        gift;
    private Integer     priority;
    private CommonState state;
    private Integer     version;

    public ChargeConfDo(ChargeConfCreateCmd command, BiConsumer<ChargeConfCreateCmd, ChargeConfDo> converter) {
        this.id      = IdWorker.nextId();
        this.state   = CommonState.CREATED;
        this.version = 0;
        converter.accept(command, this);
    }

    public void modify(ChargeConfEditCmd command, BiConsumer<ChargeConfEditCmd, ChargeConfDo> converter) {
        CommonState confState = command.getState();
        if (confState == null) {
            if (command.getPriority() != null) {
                //仅使用中的套餐允许设置套餐优先级
                Assert.state(this.state == CommonState.USING, ResponseHandler.DATA_STATE_ILLEGAL);
                this.priority = command.getPriority();
            }
            //已创建套餐允许编辑信息
            Assert.state(this.state == CommonState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
            converter.accept(command, this);
            return;
        }
        Set<CommonState> transitions = this.state.transitions();
        Assert.state(transitions.contains(confState), ResponseHandler.DATA_STATE_ILLEGAL);
        //下线使用中的配置，将优先级设为0
        if (this.state == CommonState.USING && confState == CommonState.CREATED) {
            this.priority = 0;
        }
        this.state = confState;
    }

    public boolean isUsing() {
        return this.state == CommonState.USING;
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }

}
