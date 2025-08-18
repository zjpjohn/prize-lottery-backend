package com.prize.lottery.po.ssq;

import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.RankValue;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class SsqMasterRankPo {

    private Long          id;
    private String        period;
    private String        masterId;
    private Integer       hot;
    private Integer       isVip;
    private RankValue     rank;
    private RankValue     red1;
    private RankValue     red2;
    private RankValue     red3;
    private RankValue     red12;
    private RankValue     red20;
    private RankValue     red25;
    private RankValue     redKill3;
    private RankValue     redKill6;
    private RankValue     blue3;
    private RankValue     blue5;
    private RankValue     blueKill;
    private LocalDateTime gmtCreate;

    public SsqMasterRankPo calcRank(SsqMasterRatePo rate) {
        this.period   = rate.getPeriod();
        this.masterId = rate.getMasterId();
        this.calcTotalRank(rate);
        for (SsqChannel channel : SsqChannel.values()) {
            channel.calcRank(rate, this);
        }
        return this;
    }

    public void calcTotalRank(SsqMasterRatePo rate) {
        double weight = 999.99 * ((rate.getR25Ne().getRate() * 0.5 + rate.getR25Ne().getFullRate() * 0.50) * 0.35
                + (rate.getR25Me().getRate() * 0.5 + rate.getR25Me().getFullRate() * 0.50) * 0.34
                + (rate.getR25Hi().getRate() * 0.5 + rate.getR25Hi().getFullRate() * 0.50) * 0.31)
                + 39.99 * ((rate.getR20Ne().getRate() * 0.5 + rate.getR20Ne().getFullRate() * 0.55) * 0.35
                + (rate.getR20Me().getRate() * 0.5 + rate.getR20Me().getFullRate() * 0.55) * 0.33
                + (rate.getR20Hi().getRate() * 0.5 + rate.getR20Hi().getFullRate() * 0.55) * 0.32)
                + 5.99 * (rate.getR12Ne().getRate() * 0.36
                + rate.getR12Me().getRate() * 0.34
                + rate.getR12Hi().getRate() * 0.30)
                + 399.99 * (rate.getRk3Ne().getRate() * 0.35
                + rate.getRk3Me().getRate() * 0.34
                + rate.getRk3Hi().getRate() * 0.31)
                + 79.99 * (rate.getRk6Ne().getRate() * 0.36
                + rate.getRk6Me().getRate() * 0.34
                + rate.getRk6Hi().getRate() * 0.30)
                + 9.99 * (rate.getR3Ne().getRate() * 0.36
                + rate.getR3Me().getRate() * 0.34
                + rate.getR3Hi().getRate() * 0.30);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.rank = new RankValue(weight);
    }

    public void calcRed1Rank(SsqMasterRatePo rate) {
        double weight = 1.39 * ((rate.getR25Ne().getRate() * 0.65 + rate.getR25Ne().getFullRate() * 0.45) * 0.36
                + (rate.getR25Me().getRate() * 0.65 + rate.getR25Me().getFullRate() * 0.45) * 0.34
                + (rate.getR25Hi().getRate() * 0.65 + rate.getR25Hi().getFullRate() * 0.45) * 0.30)
                + 1.19 * ((rate.getR20Ne().getRate() * 0.65 + rate.getR20Ne().getFullRate() * 0.45) * 0.36
                + (rate.getR20Me().getRate() * 0.65 + rate.getR20Me().getFullRate() * 0.45) * 0.34
                + (rate.getR20Hi().getRate() * 0.65 + rate.getR20Hi().getFullRate() * 0.45) * 0.30)
                + 999.99 * (rate.getR1Ne().getRate() * 0.35
                + rate.getR1Me().getRate() * 0.34
                + rate.getR1Hi().getRate() * 0.31)
                + 1.71 * (rate.getRk3Ne().getRate() * 0.36
                + rate.getRk3Me().getRate() * 0.34
                + rate.getRk3Hi().getRate() * 0.30);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red1 = new RankValue(weight);
    }

    public void calcRed2Rank(SsqMasterRatePo rate) {
        double weight = 1.39 * ((rate.getR25Ne().getRate() * 0.4 + rate.getR25Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR25Me().getRate() * 0.4 + rate.getR25Me().getFullRate() * 0.6) * 0.35
                + (rate.getR25Hi().getRate() * 0.4 + rate.getR25Hi().getFullRate() * 0.6) * 0.25)
                + 1.19 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 999.99 * (rate.getR2Ne().getRate() * 0.35
                + rate.getR2Me().getRate() * 0.34
                + rate.getR2Hi().getRate() * 0.31)
                + 2.31 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red2 = new RankValue(weight);
    }

    public void calcRed3Rank(SsqMasterRatePo rate) {
        double weight = 1.79 * ((rate.getR25Ne().getRate() * 0.4 + rate.getR25Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR25Me().getRate() * 0.4 + rate.getR25Me().getFullRate() * 0.6) * 0.35
                + (rate.getR25Hi().getRate() * 0.4 + rate.getR25Hi().getFullRate() * 0.6) * 0.25)
                + 1.19 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 999.99 * (rate.getR3Ne().getRate() * 0.35
                + rate.getR3Me().getRate() * 0.34
                + rate.getR3Hi().getRate() * 0.31)
                + 2.31 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red3 = new RankValue(weight);
    }

    public void calcRed12Rank(SsqMasterRatePo rate) {
        double weight = 2.79 * ((rate.getR25Ne().getRate() * 0.4 + rate.getR25Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR25Me().getRate() * 0.4 + rate.getR25Me().getFullRate() * 0.6) * 0.35
                + (rate.getR25Hi().getRate() * 0.4 + rate.getR25Hi().getFullRate() * 0.6) * 0.25)
                + 1.19 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 999.99 * (rate.getR12Ne().getRate() * 0.35
                + rate.getR12Me().getRate() * 0.34
                + rate.getR12Hi().getRate() * 0.31)
                + 2.31 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red12 = new RankValue(weight);
    }

    public void calcRed20Rank(SsqMasterRatePo rate) {
        double weight = 2.79 * ((rate.getR25Ne().getRate() * 0.4 + rate.getR25Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR25Me().getRate() * 0.4 + rate.getR25Me().getFullRate() * 0.6) * 0.35
                + (rate.getR25Hi().getRate() * 0.4 + rate.getR25Hi().getFullRate() * 0.6) * 0.25)
                + 999.99 * ((rate.getR20Ne().getRate() * 0.50 + rate.getR20Ne().getFullRate() * 0.50) * 0.35
                + (rate.getR20Me().getRate() * 0.50 + rate.getR20Me().getFullRate() * 0.50) * 0.34
                + (rate.getR20Hi().getRate() * 0.50 + rate.getR20Hi().getFullRate() * 0.50) * 0.31)
                + 2.31 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red20 = new RankValue(weight);
    }

    public void calcRed25Rank(SsqMasterRatePo rate) {
        double weight = 999.99 * ((rate.getR25Ne().getRate() * 0.50 + rate.getR25Ne().getFullRate() * 0.50) * 0.35
                + (rate.getR25Me().getRate() * 0.50 + rate.getR25Me().getFullRate() * 0.50) * 0.34
                + (rate.getR25Hi().getRate() * 0.50 + rate.getR25Hi().getFullRate() * 0.50) * 0.31)
                + 1.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.38
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.33
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.29)
                + 2.31 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red25 = new RankValue(weight);
    }

    public void calcRedKill3Rank(SsqMasterRatePo rate) {
        double weight = 2.99 * ((rate.getR25Ne().getRate() * 0.4 + rate.getR25Ne().getFullRate() * 0.6) * 0.40
                + (rate.getR25Me().getRate() * 0.4 + rate.getR25Me().getFullRate() * 0.6) * 0.35
                + (rate.getR25Hi().getRate() * 0.4 + rate.getR25Hi().getFullRate() * 0.6) * 0.25)
                + 1.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.40
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 999.99 * (rate.getRk3Ne().getRate() * 0.35
                + rate.getRk3Me().getRate() * 0.34
                + rate.getRk3Hi().getRate() * 0.31);
        weight        = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.redKill3 = new RankValue(weight);
    }

    public void calcRedKill6Rank(SsqMasterRatePo rate) {
        double weight = 1.99 * ((rate.getR25Ne().getRate() * 0.4 + rate.getR25Ne().getFullRate() * 0.6) * 0.40
                + (rate.getR25Me().getRate() * 0.4 + rate.getR25Me().getFullRate() * 0.6) * 0.35
                + (rate.getR25Hi().getRate() * 0.4 + rate.getR25Hi().getFullRate() * 0.6) * 0.25)
                + 1.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.40
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 3.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25)
                + 999.99 * (rate.getRk6Ne().getRate() * 0.35
                + rate.getRk6Me().getRate() * 0.34
                + rate.getRk6Hi().getRate() * 0.31);
        weight        = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.redKill6 = new RankValue(weight);
    }

    public void calcBlue3Rank(SsqMasterRatePo rate) {
        double weight = 1.99 * (rate.getBkNe().getRate() * 0.4
                + rate.getBkMe().getRate() * 0.35
                + rate.getBkHi().getRate() * 0.25) + 999.99 * (rate.getB3Ne().getRate() * 0.35
                + rate.getB3Me().getRate() * 0.34
                + rate.getB3Hi().getRate() * 0.31);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blue3 = new RankValue(weight);
    }

    public void calcBlue6Rank(SsqMasterRatePo rate) {
        double weight = 5.99 * (rate.getBkNe().getRate() * 0.4
                + rate.getBkMe().getRate() * 0.35
                + rate.getBkHi().getRate() * 0.25) + 1.99 * (rate.getB3Ne().getRate() * 0.4
                + rate.getB3Me().getRate() * 0.35
                + rate.getB3Hi().getRate() * 0.25) + 999.99 * (rate.getB5Ne().getRate() * 0.35
                + rate.getB5Me().getRate() * 0.34
                + rate.getB5Hi().getRate() * 0.31);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blue5 = new RankValue(weight);
    }

    public void calcBlueKillRank(SsqMasterRatePo rate) {
        double weight = 999.99 * (rate.getBkNe().getRate() * 0.35
                + rate.getBkMe().getRate() * 0.34
                + rate.getBkHi().getRate() * 0.31) + 1.99 * (rate.getB3Ne().getRate() * 0.4
                + rate.getB3Me().getRate() * 0.35
                + rate.getB3Hi().getRate() * 0.25) + 5.99 * (rate.getB5Ne().getRate() * 0.4
                + rate.getB5Me().getRate() * 0.35
                + rate.getB5Hi().getRate() * 0.25);
        weight        = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blueKill = new RankValue(weight);
    }

}
