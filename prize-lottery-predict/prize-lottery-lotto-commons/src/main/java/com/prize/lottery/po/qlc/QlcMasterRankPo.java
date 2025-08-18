package com.prize.lottery.po.qlc;

import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.value.RankValue;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class QlcMasterRankPo {

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
    private RankValue     red18;
    private RankValue     red22;
    private RankValue     kill3;
    private RankValue     kill6;
    private LocalDateTime gmtCreate;

    public QlcMasterRankPo calcRank(QlcMasterRatePo rate) {
        this.period   = rate.getPeriod();
        this.masterId = rate.getMasterId();
        this.calcTotalTank(rate);
        for (QlcChannel channel : QlcChannel.values()) {
            channel.calcRank(rate, this);
        }
        return this;
    }

    public void calcTotalTank(QlcMasterRatePo rate) {
        double weight = 999.99 * ((rate.getR22Ne().getRate() * 0.50 + rate.getR22Ne().getFullRate() * 0.55) * 0.35
                + (rate.getR22Me().getRate() * 0.50 + rate.getR22Me().getFullRate() * 0.55) * 0.33
                + (rate.getR22Hi().getRate() * 0.50 + rate.getR22Hi().getFullRate() * 0.55) * 0.32)
                + 399.99 * (rate.getK3Ne().getRate() * 0.35
                + rate.getK3Me().getRate() * 0.33
                + rate.getK3Hi().getRate() * 0.32)
                + 59.99 * ((rate.getR18Ne().getRate() * 0.50 + rate.getR18Ne().getFullRate() * 0.55) * 0.35
                + (rate.getR18Me().getRate() * 0.50 + rate.getR18Me().getFullRate() * 0.55) * 0.33
                + (rate.getR18Hi().getRate() * 0.50 + rate.getR18Hi().getFullRate() * 0.55) * 0.32)
                + 29.99 * (rate.getK6Ne().getRate() * 0.45
                + rate.getK6Me().getRate() * 0.35
                + rate.getK6Hi().getRate() * 0.25)
                + 5.99 * (rate.getR3Ne().getRate() * 0.45
                + rate.getR3Me().getRate() * 0.35
                + rate.getR3Hi().getRate() * 0.25)
                + 9.99 * (rate.getR12Ne().getRate() * 0.45
                + rate.getR12Me().getRate() * 0.35
                + rate.getR12Hi().getRate() * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.rank = new RankValue(weight);
    }

    public void calcRed1Rank(QlcMasterRatePo rate) {
        double weight = 1.99 * ((rate.getR22Ne().getRate() * 0.45 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.45 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.45 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 2.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 999.99 * (rate.getR1Ne().getRate() * 0.35
                + rate.getR1Me().getRate() * 0.33
                + rate.getR1Hi().getRate() * 0.32);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red1 = new RankValue(weight);
    }

    public void calcRed2Rank(QlcMasterRatePo rate) {
        double weight = 1.99 * ((rate.getR22Ne().getRate() * 0.45 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.45 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.45 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 2.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 999.99 * (rate.getR2Ne().getRate() * 0.35
                + rate.getR2Me().getRate() * 0.33
                + rate.getR2Hi().getRate() * 0.32);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red2 = new RankValue(weight);
    }

    public void calcRed3Rank(QlcMasterRatePo rate) {
        double weight = 1.99 * ((rate.getR22Ne().getRate() * 0.45 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.45 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.45 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 2.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 999.99 * (rate.getR3Ne().getRate() * 0.35
                + rate.getR3Me().getRate() * 0.33
                + rate.getR3Hi().getRate() * 0.32);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red3 = new RankValue(weight);
    }

    public void calcRed12Rank(QlcMasterRatePo rate) {
        double weight = 1.99 * ((rate.getR22Ne().getRate() * 0.45 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.45 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.45 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 2.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 999.99 * (rate.getR12Ne().getRate() * 0.35
                + rate.getR12Me().getRate() * 0.33
                + rate.getR12Hi().getRate() * 0.32);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red12 = new RankValue(weight);
    }

    public void calcRed18Rank(QlcMasterRatePo rate) {
        double weight = 1.99 * ((rate.getR22Ne().getRate() * 0.50 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.50 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.50 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 2.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 999.99 * ((rate.getR18Ne().getRate() * 0.50 + rate.getR18Ne().getFullRate() * 0.55) * 0.35
                + (rate.getR18Me().getRate() * 0.50 + rate.getR18Me().getFullRate() * 0.55) * 0.33
                + (rate.getR18Hi().getRate() * 0.50 + rate.getR18Hi().getFullRate() * 0.55) * 0.32);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red18 = new RankValue(weight);
    }

    public void calcRed22Rank(QlcMasterRatePo rate) {
        double weight = 999.99 * ((rate.getR22Ne().getRate() * 0.50 + rate.getR22Ne().getFullRate() * 0.55) * 0.35
                + (rate.getR22Me().getRate() * 0.50 + rate.getR22Me().getFullRate() * 0.55) * 0.33
                + (rate.getR22Hi().getRate() * 0.50 + rate.getR22Hi().getFullRate() * 0.55) * 0.32)
                + 2.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 1.99 * (rate.getR18Ne().getRate() * 0.45
                + rate.getR18Me().getRate() * 0.35
                + rate.getR18Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.red22 = new RankValue(weight);
    }

    public void calcKill3Rank(QlcMasterRatePo rate) {
        double weight = 3.99 * ((rate.getR22Ne().getRate() * 0.45 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.45 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.45 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 999.99 * (rate.getK3Ne().getRate() * 0.35
                + rate.getK3Me().getRate() * 0.33
                + rate.getK3Hi().getRate() * 0.32)
                + 2.99 * (rate.getR18Ne().getRate() * 0.45
                + rate.getR18Me().getRate() * 0.35
                + rate.getR18Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.kill3 = new RankValue(weight);
    }

    public void calcKill6Rank(QlcMasterRatePo rate) {
        double weight = 2.99 * ((rate.getR22Ne().getRate() * 0.45 + rate.getR22Ne().getFullRate() * 0.55) * 0.4
                + (rate.getR22Me().getRate() * 0.45 + rate.getR22Me().getFullRate() * 0.55) * 0.35
                + (rate.getR22Hi().getRate() * 0.45 + rate.getR22Hi().getFullRate() * 0.55) * 0.25)
                + 7.99 * (rate.getK3Ne().getRate() * 0.45
                + rate.getK3Me().getRate() * 0.35
                + rate.getK3Hi().getRate() * 0.25)
                + 999.99 * (rate.getK6Ne().getRate() * 0.35
                + rate.getK6Me().getRate() * 0.33
                + rate.getK6Hi().getRate() * 0.32)
                + 1.99 * (rate.getR18Ne().getRate() * 0.45
                + rate.getR18Me().getRate() * 0.35
                + rate.getR18Hi().getRate() * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.kill6 = new RankValue(weight);
    }

}
