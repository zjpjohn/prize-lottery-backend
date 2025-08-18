package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class PutChannelStatsVo {

    //渠道码
    private String  code;
    //获取用户总数
    private Integer userCnt;
    //预期消费金额
    private Long    expectAmt;
    //投放总批次
    private Integer batches;

}
