package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QlcMasterRankVo {

    private Long          id;
    private MasterValue   master;
    private String        period;
    private Integer       vip;
    private Integer       hot;
    private Integer       type;
    //本期排名
    private Integer       rank;
    //上一期排名
    private Integer       lastRank;
    //浏览次数
    private Integer       browse;
    //指定类型数据的命中率信息
    private StatHitValue  rate;
    //三胆命中率
    private StatHitValue  red3;
    //18码命中率
    private StatHitValue  red18;
    //22码命中率
    private StatHitValue  red22;
    //杀三码命中率
    private StatHitValue  kill3;
    private LocalDateTime gmtCreate;
}
