package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class SsqMasterSchemaVo {

    //专家标识
    private String       masterId;
    //计算期号
    private String       period;
    //最新期号
    private String       latest;
    //是否更新预测数据
    private Integer      modified;
    //专家信息
    private MasterValue  master;
    //12码命中
    private Integer      r12Hit;
    //20码命中
    private Integer      r20Hit;
    //蓝球5码命中
    private Integer      b5Hit;
    //20码命中率
    private StatHitValue r20;
    //25码命中率
    private StatHitValue r25;
    //杀3码命中率
    private StatHitValue rk3;
    //蓝球5码命中率
    private StatHitValue b5;
}
