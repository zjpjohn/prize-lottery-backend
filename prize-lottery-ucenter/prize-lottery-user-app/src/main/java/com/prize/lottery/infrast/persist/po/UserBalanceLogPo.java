package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.ActionDirection;
import com.prize.lottery.infrast.persist.enums.BalanceAction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBalanceLogPo {

    private Long            id;
    //流水号
    private String          seq;
    //用户给标识
    private Long            userId;
    //操作方向
    private ActionDirection direct;
    //操作可提现余额金额
    private Integer         balance;
    //操作不可提现金额
    private Integer         surplus;
    //代金券金额变动
    private Integer         voucher;
    //变动来源数量
    private Integer         source;
    //操作类型
    private BalanceAction   action;
    //备注说明
    private String          remark;
    //操作状态
    private Integer         state;
    private LocalDateTime   gmtCreate;
    private LocalDateTime   gmtModify;

}
