package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.po.PutChannelPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PutChannelVo extends PutChannelPo {

    //投放总批次
    private Integer batches   = 0;
    //累计邀请获取总人数
    private Integer userCnt   = 0;
    //投放预期消费金额
    private Long    expectAmt = 0L;

}
