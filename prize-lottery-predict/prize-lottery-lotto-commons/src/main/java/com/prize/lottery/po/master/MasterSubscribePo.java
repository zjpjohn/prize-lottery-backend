package com.prize.lottery.po.master;

import com.prize.lottery.enums.LotteryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterSubscribePo {

    private Long          id;
    //用户标识
    private Long          userId;
    //专家表示
    private String        masterId;
    //彩票类型
    private LotteryEnum   type;
    //追踪字段
    private String        trace;
    //追踪字段中文名
    private String        traceZh;
    //特别关注:0-否,1-是
    private Integer       special;
    //创建时间
    private LocalDateTime gmtCreate;
    //更新时间
    private LocalDateTime gmtModify;

    public MasterSubscribePo(Long userId, String masterId, LotteryEnum lottery) {
        this.userId   = userId;
        this.masterId = masterId;
        this.type     = lottery;
    }
}
