package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class MemberTotalMetricsVo {

    //总用会员数
    private Integer totalUser;
    //时段内新增会员数
    private Integer timeUser;
    //累计支付金额
    private Long    totalPayed;
    //时段内支付金额
    private Long    timePayed;

}
