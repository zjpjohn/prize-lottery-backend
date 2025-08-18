package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import lombok.Data;

import java.util.List;

@Data
public class MasterFocusVo {

    private Long              id;
    private Long              userId;
    private String            masterId;
    private MasterValue       master;
    private List<LotteryTime> lotteries;

    @Data
    public static class LotteryTime {

        private String lottery;
        private String period;

    }

}
