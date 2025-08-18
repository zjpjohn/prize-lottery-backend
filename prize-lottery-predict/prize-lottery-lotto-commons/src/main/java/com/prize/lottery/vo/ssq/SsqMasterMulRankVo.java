package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class SsqMasterMulRankVo {

    private Long        id;
    /**
     * 专家信息
     */
    private MasterValue master;
    /**
     * 排名期号
     */
    private String      period;
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
     * 红球3胆命中
     */
    private StatHitValue red3;
    /**
     * 红球25码命中
     */
    private StatHitValue red25;
    /**
     * 红球杀三码命中
     */
    private StatHitValue rk3;
    /**
     * 篮球杀码命中
     */
    private StatHitValue bk;
}
