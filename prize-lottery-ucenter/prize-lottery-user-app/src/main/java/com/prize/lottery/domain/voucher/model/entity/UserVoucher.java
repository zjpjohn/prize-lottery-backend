package com.prize.lottery.domain.voucher.model.entity;

import com.cloud.arch.aggregate.Entity;
import com.prize.lottery.infrast.persist.enums.VoucherExpire;
import com.prize.lottery.infrast.persist.enums.VoucherLogState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class UserVoucher implements Entity<Long> {

    private static final long serialVersionUID = 8310029691927641896L;

    private Long            id;
    private Long            userId;
    private String          bizNo;
    private Integer         voucher;
    private Integer         used;
    private VoucherLogState state;
    private VoucherExpire   expired;
    private LocalDateTime   expireAt;
    private LocalDateTime   gmtCreate;

    public UserVoucher(Long userId, String bizNo, Integer voucher, Integer expire) {
        this.userId    = userId;
        this.bizNo     = bizNo;
        this.voucher   = voucher;
        this.used      = 0;
        this.state     = VoucherLogState.UN_USED;
        this.gmtCreate = LocalDateTime.now();
        this.expired   = expire == 0 ? VoucherExpire.NONE : VoucherExpire.IN_VALID;
        if (this.expired == VoucherExpire.IN_VALID) {
            this.expireAt = LocalDateTime.now().plusDays(expire.longValue()).with(LocalTime.MAX);
        }
    }

    /**
     * 领取的代金券是否能够使用
     */
    public boolean canUse() {
        return this.expired != VoucherExpire.EXPIRED
                && this.expireAt.isAfter(LocalDateTime.now())
                && this.state != VoucherLogState.ALL_USED;
    }

    /**
     * 领取的代金券是否过期
     */
    public boolean isExpired() {
        return this.expireAt.isBefore(LocalDateTime.now());
    }

    /**
     * 代金券过期
     */
    public Integer expireVoucher() {
        this.expired = VoucherExpire.EXPIRED;
        return this.voucher - this.used;
    }

    /**
     * 使用代金券计算
     *
     * @param amount 预计代金券使用金额
     * @return 返回剩余金额
     */
    public Integer useVoucher(Integer amount) {
        //可用代金券金额
        int voucher = this.voucher - this.used;
        //本次使用金额
        Integer used = Math.min(voucher, amount);
        //累计使用金额
        this.used = this.used + used;
        //计算代金券使用状态
        this.state = this.used >= this.voucher ? VoucherLogState.ALL_USED : VoucherLogState.PART_USED;
        //返回剩余不足的金额
        return amount - used;
    }

    public LocalDateTime nextDraw(Integer interval) {
        return this.gmtCreate.plusDays(interval);
    }

}
