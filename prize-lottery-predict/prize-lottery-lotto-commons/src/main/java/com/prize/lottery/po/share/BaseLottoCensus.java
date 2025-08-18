package com.prize.lottery.po.share;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseLottoCensus {

    private Long          id;
    /**
     * 统计数据期号
     */
    private String        period;
    /**
     * 统计图标类型
     */
    private Integer       type;
    /**
     * 统计数据量级别
     */
    private Integer       level;
    /**
     * 统计数据
     */
    private CensusValue   census;
    /**
     * 统计创建时间
     */
    private LocalDateTime gmtCreate;

}
