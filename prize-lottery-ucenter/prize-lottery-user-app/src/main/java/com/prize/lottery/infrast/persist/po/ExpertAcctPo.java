package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prize.lottery.infrast.persist.enums.ExpertState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExpertAcctPo {

    //用户标识
    private Long          userId;
    //专家标识
    private String        masterId;
    //提现校验密码
    @JsonIgnore
    private String        acctPwd;
    //账户金币余额
    private Integer       income;
    //累计提现金币
    private Integer       withdraw;
    //累计提现人民币
    private Integer       withRmb;
    //最新提现日期
    private LocalDate     withLatest;
    //专家账户状态
    private ExpertState   state;
    //创建时间
    private LocalDateTime gmtCreate;
    //更新时间
    private LocalDateTime gmtModify;

}
