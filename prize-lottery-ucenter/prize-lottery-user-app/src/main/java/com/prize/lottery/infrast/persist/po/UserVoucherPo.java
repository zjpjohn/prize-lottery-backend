package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVoucherPo {

    private Long          userId;
    private Integer       allNum;
    private Integer       usedNum;
    private Integer       expiredNum;
    private Integer       total;
    private Integer       used;
    private Integer       expired;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

    public static UserVoucherPo empty(Long userId) {
        UserVoucherPo voucher = new UserVoucherPo();
        voucher.setUserId(userId);
        voucher.setAllNum(0);
        voucher.setUsedNum(0);
        voucher.setExpiredNum(0);
        voucher.setTotal(0);
        voucher.setUsed(0);
        voucher.setExpired(0);
        voucher.setGmtCreate(LocalDateTime.now());
        voucher.setGmtModify(LocalDateTime.now());
        return voucher;
    }

}
