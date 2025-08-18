package com.prize.lottery.domain.voucher.valobj;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherOperation {

    private Long    userId;
    private Integer allNum     = 0;
    private Integer usedNum    = 0;
    private Integer expiredNum = 0;
    private Integer total      = 0;
    private Integer used       = 0;
    private Integer expired    = 0;

    public static VoucherOperation draw(Long userId, Integer total, Integer allNum) {
        VoucherOperation operation = new VoucherOperation();
        operation.setUserId(userId);
        operation.setTotal(total);
        operation.setAllNum(allNum);
        return operation;
    }

    public static VoucherOperation use(Long userId, Integer used, Integer usedNum) {
        VoucherOperation operation = new VoucherOperation();
        operation.setUserId(userId);
        operation.setUsed(used);
        operation.setUsedNum(usedNum);
        return operation;
    }

    public static VoucherOperation expire(Long userId, Integer expired, Integer expiredNum) {
        VoucherOperation operation = new VoucherOperation();
        operation.setUserId(userId);
        operation.setExpired(expired);
        operation.setExpiredNum(expiredNum);
        return operation;
    }

}
