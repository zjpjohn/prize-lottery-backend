package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.BlueBall;
import com.prize.lottery.value.RedBall;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryPickPo {

    private Long        id;
    //彩种类型
    private LotteryEnum lottery;
    //选号期号
    private String      period;
    //用户标识
    private Long     userId;
    //红球号码
    private RedBall       redBall;
    //蓝球号码
    private BlueBall      blueBall;
    //创建时间
    private LocalDateTime gmtCreate;
    //更新时间
    private LocalDateTime gmtModify;

}
