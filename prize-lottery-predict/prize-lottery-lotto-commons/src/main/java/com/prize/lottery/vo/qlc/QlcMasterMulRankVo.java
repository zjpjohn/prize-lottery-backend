package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class QlcMasterMulRankVo {


    private Long         id;
    /**
     * 专家信息
     */
    private MasterValue  master;
    /**
     * 排名期号
     */
    private String       period;
    /**
     * 是否为收费专家
     */
    private Integer      vip;
    /**
     * 是否为热门专家
     */
    private Integer      hot;
    /**
     * 本期排名
     */
    private Integer      rank;
    /**
     * 上一期排名
     */
    private Integer      lastRank;
    /**
     * 浏览次数
     */
    private Integer      browse;
    /**
     * 红球3码命中
     */
    private StatHitValue red3;
    /**
     * 红球18码命中
     */
    private StatHitValue red18;
    /**
     * 红球22码命中
     */
    private StatHitValue red22;
    /**
     * 红球杀三码命中
     */
    private StatHitValue kill3;
}
