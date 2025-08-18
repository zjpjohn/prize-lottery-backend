package com.prize.lottery.po.dlt;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.value.RankValue;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class DltMasterRankPo {

    private Long          id;
    private String        period;
    private String        masterId;
    private Integer       hot;
    private Integer       isVip;
    private RankValue     rank;
    private RankValue     red1;
    private RankValue     red2;
    private RankValue     red3;
    private RankValue     red10;
    private RankValue     red20;
    private RankValue     redKill3;
    private RankValue     redKill6;
    private RankValue     blue1;
    private RankValue     blue2;
    private RankValue     blue6;
    private RankValue     blueKill;
    private LocalDateTime gmtCreate;

    public DltMasterRankPo calcRank(DltMasterRatePo rate) {
        this.period   = rate.getPeriod();
        this.masterId = rate.getMasterId();
        this.calcTotalRank(rate);
        for (DltChannel channel : DltChannel.values()) {
            channel.calcRank(rate, this);
        }
        return this;
    }

    public void calcTotalRank(DltMasterRatePo rate) {
        double weight = 999.99 * ((rate.getR20Ne().getRate() * 0.50 + rate.getR20Ne().getFullRate() * 0.50) * 0.35
                + (rate.getR20Me().getRate() * 0.50 + rate.getR20Me().getFullRate() * 0.50) * 0.34
                + (rate.getR20Hi().getRate() * 0.50 + rate.getR20Hi().getFullRate() * 0.50) * 0.31)
                + 599.99 * (rate.getRk3Ne().getRate() * 0.35
                + rate.getRk3Me().getRate() * 0.34
                + rate.getRk3Hi().getRate() * 0.31)
                + 39.99 * (rate.getRk6Ne().getRate() * 0.36
                + rate.getRk6Me().getRate() * 0.34
                + rate.getRk6Hi().getRate() * 0.30)
                + 9.99 * (rate.getR3Ne().getRate() * 0.36
                + rate.getR3Me().getRate() * 0.34
                + rate.getR3Hi().getRate() * 0.30)
                + 1.99 * (rate.getR10Ne().getRate() * 0.36
                + rate.getR10Me().getRate() * 0.34
                + rate.getR10Hi().getRate() * 0.30);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.rank = new RankValue(weight);
    }

    public void calcRed1Rank(DltMasterRatePo rate) {
        double weight = 1.79 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.35
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.33
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.32)
                + 9.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25)
                + 999.99 * (rate.getR1Ne().getRate() * 0.36
                + rate.getR1Me().getRate() * 0.33
                + rate.getR1Hi().getRate() * 0.31);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red1 = new RankValue(weight);
    }

    public void calcRed2Rank(DltMasterRatePo rate) {
        double weight = 2.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.35
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.33
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.32)
                + 9.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25)
                + 599.99 * (rate.getR2Ne().getRate() * 0.36
                + rate.getR2Me().getRate() * 0.33
                + rate.getR2Hi().getRate() * 0.31);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red2 = new RankValue(weight);
    }

    public void calcRed3Rank(DltMasterRatePo rate) {
        double weight = 3.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.35
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.33
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.32)
                + 9.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25)
                + 599.99 * (rate.getR3Ne().getRate() * 0.36
                + rate.getR3Me().getRate() * 0.33
                + rate.getR3Hi().getRate() * 0.31);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red3 = new RankValue(weight);
    }

    public void calcRed10Rank(DltMasterRatePo rate) {
        double weight = 2.59 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 5.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25)
                + 599.99 * (rate.getR10Ne().getRate() * 0.36
                + rate.getR10Me().getRate() * 0.34
                + rate.getR10Hi().getRate() * 0.30);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red10 = new RankValue(weight);
    }

    public void calcRed20Rank(DltMasterRatePo rate) {
        double weight = 599.99 * ((rate.getR20Ne().getRate() * 0.50 + rate.getR20Ne().getFullRate() * 0.50) * 0.35
                + (rate.getR20Me().getRate() * 0.50 + rate.getR20Me().getFullRate() * 0.50) * 0.34
                + (rate.getR20Hi().getRate() * 0.50 + rate.getR20Hi().getFullRate() * 0.50) * 0.31)
                + 9.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25)
                + 1.99 * (rate.getR10Ne().getRate() * 0.4
                + rate.getR10Me().getRate() * 0.35
                + rate.getR10Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red20 = new RankValue(weight);
    }

    public void calcRk3Rank(DltMasterRatePo rate) {
        double weight = 2.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 9.99 * (rate.getRk6Ne().getRate() * 0.4
                + rate.getRk6Me().getRate() * 0.35
                + rate.getRk6Hi().getRate() * 0.25)
                + 599.99 * (rate.getRk3Ne().getRate() * 0.35
                + rate.getRk3Me().getRate() * 0.34
                + rate.getRk3Hi().getRate() * 0.31);
        weight        = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.redKill3 = new RankValue(weight);
    }

    public void calcRk6Rank(DltMasterRatePo rate) {
        double weight = 2.99 * ((rate.getR20Ne().getRate() * 0.4 + rate.getR20Ne().getFullRate() * 0.6) * 0.4
                + (rate.getR20Me().getRate() * 0.4 + rate.getR20Me().getFullRate() * 0.6) * 0.35
                + (rate.getR20Hi().getRate() * 0.4 + rate.getR20Hi().getFullRate() * 0.6) * 0.25)
                + 599.99 * (rate.getRk6Ne().getRate() * 0.35
                + rate.getRk6Me().getRate() * 0.34
                + rate.getRk6Hi().getRate() * 0.31)
                + 9.99 * (rate.getRk3Ne().getRate() * 0.4
                + rate.getRk3Me().getRate() * 0.35
                + rate.getRk3Hi().getRate() * 0.25);
        weight        = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.redKill6 = new RankValue(weight);
    }

    public void calcBlue1Rank(DltMasterRatePo rate) {
        double weight = 9.99 * ((rate.getB6Ne().getRate() * 0.4 + rate.getB6Ne().getFullRate() * 0.6) * 0.4
                + (rate.getB6Me().getRate() * 0.4 + rate.getB6Me().getFullRate() * 0.6) * 0.35
                + (rate.getB6Hi().getRate() * 0.4 + rate.getB6Hi().getFullRate() * 0.6) * 0.25)
                + 599.99 * (rate.getB1Ne().getRate() * 0.35
                + rate.getB1Me().getRate() * 0.34
                + rate.getB1Hi().getRate() * 0.31)
                + 5.99 * (rate.getBkNe().getRate() * 0.4
                + rate.getBkMe().getRate() * 0.35
                + rate.getBkHi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blue1 = new RankValue(weight);
    }

    public void calcBlue2Rank(DltMasterRatePo rate) {
        double weight = 5.99 * ((rate.getB6Ne().getRate() * 0.45 + rate.getB6Ne().getFullRate() * 0.55) * 0.38
                + (rate.getB6Me().getRate() * 0.45 + rate.getB6Me().getFullRate() * 0.55) * 0.32
                + (rate.getB6Hi().getRate() * 0.45 + rate.getB6Hi().getFullRate() * 0.55) * 0.30)
                + 599.99 * (rate.getB2Ne().getRate() * 0.35
                + rate.getB2Me().getRate() * 0.34
                + rate.getB2Hi().getRate() * 0.31)
                + 5.99 * (rate.getBkNe().getRate() * 0.4
                + rate.getBkMe().getRate() * 0.35
                + rate.getBkHi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blue2 = new RankValue(weight);
    }

    public void calcBlue6Rank(DltMasterRatePo rate) {
        double weight = 599.99 * ((rate.getB6Ne().getRate() * 0.50 + rate.getB6Ne().getFullRate() * 0.55) * 0.36
                + (rate.getB6Me().getRate() * 0.50 + rate.getB6Me().getFullRate() * 0.55) * 0.34
                + (rate.getB6Hi().getRate() * 0.50 + rate.getB6Hi().getFullRate() * 0.55) * 0.30)
                + 1.99 * (rate.getB2Ne().getRate() * 0.4
                + rate.getB2Me().getRate() * 0.35
                + rate.getB2Hi().getRate() * 0.25)
                + 9.99 * (rate.getBkNe().getRate() * 0.4
                + rate.getBkMe().getRate() * 0.35
                + rate.getBkHi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blue6 = new RankValue(weight);
    }

    public void calcBlueKillRank(DltMasterRatePo rate) {
        double weight = 9.99 * ((rate.getB6Ne().getRate() * 0.4 + rate.getB6Ne().getFullRate() * 0.6) * 0.4
                + (rate.getB6Me().getRate() * 0.4 + rate.getB6Me().getFullRate() * 0.6) * 0.35
                + (rate.getB6Hi().getRate() * 0.4 + rate.getB6Hi().getFullRate() * 0.6) * 0.25) + 2.99 * (rate.getB2Ne()
                                                                                                              .getRate()
                * 0.4 + rate.getB2Me().getRate() * 0.35 + rate.getB2Hi().getRate() * 0.25) + 599.99 * (rate.getBkNe()
                                                                                                           .getRate()
                * 0.35 + rate.getBkMe().getRate() * 0.34 + rate.getBkHi().getRate() * 0.31);
        weight        = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.blueKill = new RankValue(weight);
    }

}
