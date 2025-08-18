package com.prize.lottery.domain.voucher.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.VoucherCreateCmd;
import com.prize.lottery.application.command.dto.VoucherEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.VoucherState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class VoucherInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 6459648664902539676L;

    private Long         id;
    private String       seqNo;
    private String       name;
    private String       remark;
    private Integer      voucher;
    private VoucherState state;
    private Integer      disposable;
    private Integer      interval;
    private Integer      expire;

    public VoucherInfoDo(VoucherCreateCmd command, BiConsumer<VoucherCreateCmd, VoucherInfoDo> converter) {
        this.seqNo = String.valueOf(IdWorker.nextId());
        this.state = VoucherState.CREATED;
        converter.accept(command, this);
    }

    public void modify(VoucherEditCmd command, BiConsumer<VoucherEditCmd, VoucherInfoDo> converter) {
        VoucherState state = command.getState();
        if (state != null) {
            Set<VoucherState> transitions = this.state.transitions();
            Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
            this.state = state;
            return;
        }
        Assert.state(this.state == VoucherState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
        converter.accept(command, this);
        Integer disposable = command.getDisposable();
        if (!Objects.equals(this.disposable, disposable)) {
            this.disposable = disposable;
            Integer interval = command.getInterval();
            //设置为永久优惠券，领取间隔时间不允许为空
            if (disposable == 0) {
                Assert.state(interval != null && interval > 0, ResponseHandler.VOUCHER_INTERVAL_ERROR);
                this.interval = interval;
            } else {
                //设置为一次型优惠券,设置间隔时间为0
                this.interval = 0;
            }
        }
    }

    /**
     * 代金券是否为一次性的
     */
    public boolean isDisposable() {
        return this.disposable == 1;
    }

    /**
     * 是否允许领取
     *
     * @param lastDraw 上一次领取时间
     */
    public boolean canDraw(LocalDateTime lastDraw) {
        if (!this.state.isUsing()) {
            return false;
        }
        //未领取过代金券
        if (lastDraw == null) {
            return true;
        }
        //一次性代金券已领取过
        if (this.disposable == 1) {
            return false;
        }
        //可多次领取代金券判断
        LocalDateTime nextDraw = lastDraw.plusDays(this.interval);
        return nextDraw.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
