package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.VoucherExpire;
import com.prize.lottery.infrast.persist.enums.VoucherLogState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVoucherLogPo {

    private Long            id;
    private Long            userId;
    private String          bizNo;
    private Integer         voucher;
    private Integer         used;
    private VoucherLogState state;
    private VoucherExpire   expired;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime   expireAt;
    private LocalDateTime   gmtCreate;
    private LocalDateTime   gmtModify;

}
