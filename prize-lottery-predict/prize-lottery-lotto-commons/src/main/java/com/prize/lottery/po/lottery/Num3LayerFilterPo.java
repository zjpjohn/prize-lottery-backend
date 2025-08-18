package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.LayerValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Num3LayerFilterPo {

    private Long          id;
    private String        period;
    private LotteryEnum   type;
    private LayerValue    layer1;
    private LayerValue    layer2;
    private LayerValue    layer3;
    private LayerValue    layer4;
    private LayerValue    layer5;
    private LocalDateTime editTime;
    private LocalDateTime calcTime;
    private Integer       edits;
    private Integer       version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
