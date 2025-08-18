package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class MasterBrowseVo {

    private LotteryEnum  lottery;
    private MasterValue  master;
    private StatHitValue hit;

}
