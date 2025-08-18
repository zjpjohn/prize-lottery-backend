package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class QlcMasterSchemaVo {

    //专家标识
    private String       masterId;
    //计算期号
    private String       period;
    //最新期号
    private String      latest;
    //专家信息
    private MasterValue master;
    //18码命中
    private Integer     r12Hit;
    //22码命中
    private Integer      r18Hit;
    //18码命中信息
    private StatHitValue red18;
    //22码命中信息
    private StatHitValue red22;
    //杀三码
    private StatHitValue k3;
    //是否已经更新
    private Integer      modified;

}
