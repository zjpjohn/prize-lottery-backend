package com.prize.lottery.po.ssq;

import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class SsqHomeMasterPo {

    private Long          id;
    /**
     * 数据期号
     */
    private String        period;
    /**
     * 专家标识
     */
    private String        masterId;
    /**
     * 数据指标类型
     */
    private SsqChannel    type;
    /**
     * 专家排名
     */
    private Integer       rank;
    /**
     * 数据命中率信息
     */
    private StatHitValue  rate;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
