package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import lombok.Data;

@Data
public class RecommendMasterVo {
    //专家标识
    private String      masterId;
    //专家信息
    private MasterValue master;
    //彩种类型
    private LotteryEnum type;
    //命中率字符串
    private String      hitCount;
    //连续命中次数
    private Integer     series;
    //命中率
    private Double      hitRate;
}
