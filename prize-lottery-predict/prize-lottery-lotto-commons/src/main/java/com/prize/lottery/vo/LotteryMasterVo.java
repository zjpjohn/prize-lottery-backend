package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class LotteryMasterVo {

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
     * 综合排名
     */
    private Integer      rank;
    /**
     * 分类排名
     */
    private Integer      iRank;
    /**
     * 上一期专家排名
     */
    private Integer      lastIRank;
    /**
     * 是否上首页专家:0-否,1-是
     */
    private Integer      homed;
    /**
     * 是否为热门专家:0-否,1-是
     */
    private Integer      hot;
    /**
     * 是否收费
     */
    private Integer      vip;
    /**
     * 专家来源
     */
    private Integer      source;
    /**
     * 浏览次数
     */
    private Integer      browse;
    /**
     * 订阅次数
     */
    private Integer      subscribe;
    /**
     * 最新预测期号
     */
    private String       latest;
    /**
     * 命中信息
     */
    private StatHitValue hit;

}
