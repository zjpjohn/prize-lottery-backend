package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.VoucherState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherInfoPo {

    private Long          id;
    private String        seqNo;
    private String        name;
    private String        remark;
    private Integer       voucher;
    private VoucherState  state;
    private Integer       disposable;
    private Integer       interval;
    private Integer       expire;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
