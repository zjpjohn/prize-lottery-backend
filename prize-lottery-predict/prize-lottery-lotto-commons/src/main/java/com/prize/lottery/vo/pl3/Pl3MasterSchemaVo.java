package com.prize.lottery.vo.pl3;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class Pl3MasterSchemaVo {

    //专家标识
    private String       masterId;
    //计算期号
    private String      period;
    //专家信息
    private MasterValue master;
    //三胆命中数据
    private Integer     d3Hit;
    //组合五码命中
    private Integer      c5Hit;
    //三胆命中率
    private StatHitValue d3;
    //组合五码命中率
    private StatHitValue c5;
    //杀一码命中
    private StatHitValue k1;
    //杀二码命中
    private StatHitValue k2;
    //最新预测期号
    private String       latest;
    //是否已更新
    private Integer      modified;
}
