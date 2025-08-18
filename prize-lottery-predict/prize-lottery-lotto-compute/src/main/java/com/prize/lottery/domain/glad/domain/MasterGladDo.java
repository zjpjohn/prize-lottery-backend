package com.prize.lottery.domain.glad.domain;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.master.MasterGladPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterGladDo {

    private LotteryEnum lottery;
    private String      masterId;
    private String      period;
    private Integer     type;
    private String      content;

    public static MasterGladDo hitLottery(LotteryEnum lottery, String masterId, String period, String content) {
        return new MasterGladDo(lottery, masterId, period, 1, content);
    }

    public static MasterGladDo highRate(LotteryEnum lottery, String masterId, String period, String content) {
        return new MasterGladDo(lottery, masterId, period, 2, content);
    }

    public MasterGladPo convert() {
        MasterGladPo glad = new MasterGladPo();
        glad.setLottery(lottery);
        glad.setMasterId(masterId);
        glad.setPeriod(period);
        glad.setType(type);
        glad.setContent(content);
        return glad;
    }

}
