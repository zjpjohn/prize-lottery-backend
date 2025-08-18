package com.prize.lottery.vo.dlt;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class DltMasterSchemaVo {

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
    //10码命中
    private Integer      r10Hit;
    //蓝6码命中
    private Integer      b6Hit;
    //20码命中信息
    private StatHitValue r20;
    //杀三码命中信息
    private StatHitValue rk3;
    //蓝6码命中信息
    private StatHitValue b6;
}
