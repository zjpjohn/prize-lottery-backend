package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import lombok.Data;

@Data
public class BaseMasterRank {

    private Long        id;
    /**
     * 专家信息
     */
    private MasterValue master;
    /**
     * 最新期号
     */
    private String      period;
    /**
     * 是否vip专家
     */
    private Integer     vip;
    /**
     * 是否为热=热门专家
     */
    private Integer     hot;
    /**
     * 胆码标记标识
     */
    private Integer     mark = 0;
    /**
     * 本期排名
     */
    private Integer     rank;
    /**
     * 上一期排名
     */
    private Integer     lastRank;
    /**
     * 浏览次数
     */
    private Integer     browse;
}
