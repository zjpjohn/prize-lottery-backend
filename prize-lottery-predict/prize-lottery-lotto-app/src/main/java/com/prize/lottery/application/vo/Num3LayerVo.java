package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.LotteryValue;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import com.prize.lottery.value.LayerValue;
import lombok.Data;

@Data
public class Num3LayerVo {

    private LotteryEnum  type;
    //上一期开奖信息
    private LotteryValue last;
    //本期开奖信息
    private LotteryValue current;
    //本期预测期号
    private String       period;
    //浏览次数
    private Integer      browses;
    //第一层过滤
    private LayerValue   layer1;
    //第二层过滤
    private LayerValue   layer2;
    //第三层过滤
    private LayerValue   layer3;
    //第四层过滤
    private LayerValue   layer4;
    //第五层过滤
    private LayerValue   layer5;

    public void setLayer(Num3LayerFilterPo layer) {
        this.layer1 = layer.getLayer1();
        this.layer2 = layer.getLayer2();
        this.layer3 = layer.getLayer3();
        this.layer4 = layer.getLayer4();
        this.layer5 = layer.getLayer5();
    }

    public void setLastLottery(LotteryInfoPo lottery) {
        this.last = LotteryValue.of(lottery);
    }

    public void setCurrentLottery(LotteryInfoPo lottery) {
        this.current = LotteryValue.of(lottery);
    }

}
