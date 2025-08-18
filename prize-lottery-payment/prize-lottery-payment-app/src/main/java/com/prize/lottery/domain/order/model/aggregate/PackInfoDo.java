package com.prize.lottery.domain.order.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.PackInfoCreateCmd;
import com.prize.lottery.application.command.dto.PackInfoModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.PackState;
import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class PackInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -8647685593039452298L;

    private Long      id;
    private String    seqNo;
    private String    name;
    private String    remark;
    private Long      price;
    private Long      discount;
    private TimeUnit  timeUnit;
    private PackState state;
    private Integer   priority;
    private Integer   onTrial;

    public PackInfoDo(PackInfoCreateCmd command, BiConsumer<PackInfoCreateCmd, PackInfoDo> converter) {
        this.seqNo    = String.valueOf(IdWorker.nextId());
        this.state    = PackState.CREATED;
        this.priority = 0;
        converter.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    public boolean canDelete() {
        return this.state == PackState.INVALID;
    }

    public boolean isIssued() {
        return this.state == PackState.USING;
    }

    public void modify(PackInfoModifyCmd command, BiConsumer<PackInfoModifyCmd, PackInfoDo> converter) {
        PackState state = command.getState();
        if (state != null) {
            Set<PackState> transitions = this.state.transitions();
            Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
            //下线套餐，降套餐是否优先设置为0,设置试用标识为0
            if (state == PackState.CREATED && this.state == PackState.USING) {
                this.priority = 0;
                this.onTrial  = 0;
            }
            this.state = state;
            return;
        }
        if (command.getPriority() != null) {
            //仅已发布的套餐允许设置套餐优先级
            Assert.state(this.state == PackState.USING, ResponseHandler.DATA_STATE_ILLEGAL);
            this.priority = command.getPriority();
            return;
        }
        if (command.getOnTrial() != null) {
            //仅已发布的套餐允许设置套餐试用
            Assert.state(this.state == PackState.USING, ResponseHandler.DATA_STATE_ILLEGAL);
            this.onTrial = command.getOnTrial();
            return;
        }
        //已创建套餐允许进行编辑
        Assert.state(this.state == PackState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
        converter.accept(command, this);
        Assert.state(this.discount <= this.price, ResponseHandler.PACK_PRICE_ERROR);

    }

}
