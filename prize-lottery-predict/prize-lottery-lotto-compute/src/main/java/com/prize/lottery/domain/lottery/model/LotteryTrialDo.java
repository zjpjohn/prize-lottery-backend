package com.prize.lottery.domain.lottery.model;


import com.prize.lottery.enums.LotteryEnum;

public class LotteryTrialDo {

    private final LotteryEnum lottery;
    private final String      period;
    private       String      kai = "";
    private       String      shi = "";

    public LotteryTrialDo(LotteryEnum lottery, String period, String kai) {
        this.lottery = lottery;
        this.period  = period;
        this.kai     = kai;
    }

    public LotteryTrialDo(LotteryEnum lottery, String period, String kai, String shi) {
        this.lottery = lottery;
        this.period  = period;
        this.kai     = kai;
        this.shi     = shi;
    }

    public LotteryEnum getLottery() {
        return lottery;
    }

    public String getPeriod() {
        return period;
    }

    public String getKai() {
        return kai;
    }

    public String getShi() {
        return shi;
    }
}
