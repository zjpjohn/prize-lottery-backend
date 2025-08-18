package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.DanIndex;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryDanPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private DanIndex      index1;
    private DanIndex      index2;
    private DanIndex      index3;
    private DanIndex      index4;
    private DanIndex      index5;
    private DanIndex      index6;
    private DanIndex      index7;
    private DanIndex      index8;
    private DanIndex      index9;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
