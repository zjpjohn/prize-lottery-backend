package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.WarningHit;
import com.prize.lottery.infrast.commons.LotteryValue;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.value.ComRecommend;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class N3WarnRecommendVo {

    //彩票种类
    private LotteryEnum               type;
    //上一期开奖信息
    private LotteryValue              last;
    //本期开奖信息
    private LotteryValue              current;
    //本期预测期号
    private String                    period;
    //本期是否命中
    private Integer                   hit;
    //浏览次数
    private Integer                   browses;
    //上一期命中情况
    private WarningHit                lastHit;
    //组三推荐值
    private ComRecommend              zu3;
    //组六推荐值
    private ComRecommend              zu6;
    //预警信息
    public  Map<String, List<String>> warnings;

    public void setLastLottery(LotteryInfoPo lottery) {
        this.last = LotteryValue.of(lottery);
    }

    public void setCurrentLottery(LotteryInfoPo lottery) {
        this.current = LotteryValue.of(lottery);
    }

}
