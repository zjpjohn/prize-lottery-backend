package com.prize.lottery.po.pl3;

import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Pl3HomeMasterPo {

    private Long          id;
    private String        period;
    private String        masterId;
    //首页专家类型
    private Pl3Channel    type;
    //专家排名
    private Integer       rank;
    //命中信息
    private StatHitValue  rate;
    private LocalDateTime gmtCreate;
}
