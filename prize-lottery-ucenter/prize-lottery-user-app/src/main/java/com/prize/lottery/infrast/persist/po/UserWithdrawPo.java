package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.PayChannel;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserWithdrawPo {

    private Long          id;
    /**
     * 提现记录流水号
     */
    private String        seqNo;
    /**
     * 支付系统流水号
     */
    private String        transNo;
    /**
     * 用户标识
     */
    private Long          userId;
    /**
     * 提现消耗金币数量
     */
    private Long          withdraw;
    /**
     * 提现人民币金额
     */
    private Long          money;
    /**
     * 支付渠道
     */
    private PayChannel    channel;
    /**
     * 提现状态
     */
    private WithdrawState state;
    /**
     * 提现失败原因
     */
    private String        message;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModify;

}
