package com.prize.lottery.application.vo;

import com.prize.lottery.enums.HitType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.LotteryValue;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import com.prize.lottery.value.WarningComplex;
import com.prize.lottery.value.WarningInt;
import com.prize.lottery.value.WarningText;
import lombok.Data;

@Data
public class Num3ComWarnVo {

    private LotteryEnum    type;
    //上一期开奖信息
    private LotteryValue   last;
    //本期开奖信息
    private LotteryValue   current;
    //本期预测期号
    private String         period;
    //浏览次数
    private Integer        browses;
    //上一期命中情况
    private HitType        lastHit;
    //胆码数据
    private WarningComplex dan;
    //两码组合
    private WarningComplex twoMa;
    //组六推荐
    private WarningComplex zu6;
    //组三推荐
    private WarningComplex zu3;
    //杀码推荐
    private WarningText    kill;
    //跨度推荐
    private WarningInt     kuaList;
    //和值推荐
    private WarningInt     sumList;
    //本期命中
    private HitType        hit;

    public void setComWarning(Num3ComWarningPo warn) {
        this.dan     = warn.getDan();
        this.twoMa   = warn.getTwoMa();
        this.zu6     = warn.getZu6();
        this.zu3     = warn.getZu3();
        this.kill    = warn.getKill();
        this.kuaList = warn.getKuaList();
        this.sumList = warn.getSumList();
        this.hit     = warn.getHit();
    }

    public void setLastLottery(LotteryInfoPo lottery) {
        this.last = LotteryValue.of(lottery);
    }

    public void setCurrentLottery(LotteryInfoPo lottery) {
        this.current = LotteryValue.of(lottery);
    }

}
