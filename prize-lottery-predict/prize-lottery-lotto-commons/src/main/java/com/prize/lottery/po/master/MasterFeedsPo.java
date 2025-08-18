package com.prize.lottery.po.master;

import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterFeedsPo {

    private Long          id;
    private LotteryEnum   type;
    private String        masterId;
    private String        field;
    private FeedsType     feedType;
    private String        rateText;
    private String        hitText;
    private Integer       fieldHit;
    private Double        fieldRate;
    //信息流提取期号
    private String        period;
    //是否更新预测
    private Integer       renew;
    //数据更新期号
    private String        renewPeriod;
    private Integer       state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
