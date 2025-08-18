package com.prize.lottery.application.command.vo;

import com.prize.lottery.infrast.persist.enums.ExpertState;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExpertAccountVo {

    /**
     * 用户标识
     */
    private Long          userId;
    /**
     * 专家标识
     */
    private String        masterId;
    /**
     * 用户名称
     */
    private String        name;
    /**
     * 专家手机号
     */
    private String        phone;
    /**
     * 专家账户收入
     */
    private Integer       income;
    /**
     * 提现场景标识
     */
    private TransferScene scene;
    /**
     * 专家累计提现收入
     */
    private Integer       withdraw;
    /**
     * 当前是否可提现
     */
    private Integer       canWithdraw;
    /**
     * 最新一次提现时间
     */
    private LocalDate     withLatest;
    /**
     * 当前账户状态
     */
    private ExpertState   state;
    /**
     * 账户创建时间
     */
    private LocalDateTime gmtCreate;
}
