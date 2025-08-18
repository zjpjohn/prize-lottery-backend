package com.prize.lottery.po.kl8;

import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.value.RankValue;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class Kl8MasterRankPo {

    private Long          id;
    private String        masterId;
    private String        period;
    private Integer       hot;
    private Integer       isVip;
    private RankValue     rank;
    private RankValue     d1;
    private RankValue     d2;
    private RankValue     d3;
    private RankValue     d4;
    private RankValue     d5;
    private RankValue     d6;
    private RankValue     d7;
    private RankValue     d8;
    private RankValue     d9;
    private RankValue     d10;
    private RankValue     d11;
    private RankValue     d12;
    private RankValue     d13;
    private RankValue     d14;
    private RankValue     d15;
    private RankValue     d20;
    private RankValue     k1;
    private RankValue     k2;
    private RankValue     k3;
    private RankValue     k4;
    private RankValue     k5;
    private LocalDateTime gmtCreate;

    public Kl8MasterRankPo calcRank(Kl8MasterRatePo rate) {
        this.period   = rate.getPeriod();
        this.masterId = rate.getMasterId();
        this.calcTotalRank(rate);
        for (Kl8Channel channel : Kl8Channel.values()) {
            channel.calcRank(rate, this);
        }
        return this;
    }

    public void calcTotalRank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.95 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 25.99 * (rate.getD12Ne().getRate() * 0.55 + rate.getD12Me().getRate() * 0.45)
                + 35.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 33.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 31.99 * (rate.getK3Ne().getRate() * 0.55 + rate.getK3Me().getRate() * 0.45);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.rank = new RankValue(weight);
    }

    public void calcD1Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD1Ne().getRate() * 0.55 + rate.getD1Me().getRate() * 0.45)
                + 39.97 * (rate.getD2Ne().getRate() * 0.55 + rate.getD2Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d1 = new RankValue(weight);
    }

    public void calcD2Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD2Ne().getRate() * 0.55 + rate.getD2Me().getRate() * 0.45)
                + 39.97 * (rate.getD3Ne().getRate() * 0.55 + rate.getD3Me().getRate() * 0.45)
                + 31.97 * (rate.getD1Ne().getRate() * 0.55 + rate.getD1Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d2 = new RankValue(weight);
    }

    public void calcD3Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD3Ne().getRate() * 0.55 + rate.getD3Me().getRate() * 0.45)
                + 39.97 * (rate.getD4Ne().getRate() * 0.55 + rate.getD4Me().getRate() * 0.45)
                + 31.97 * (rate.getD2Ne().getRate() * 0.55 + rate.getD2Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d3 = new RankValue(weight);
    }

    public void calcD4Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD4Ne().getRate() * 0.55 + rate.getD4Me().getRate() * 0.45)
                + 39.97 * (rate.getD5Ne().getRate() * 0.55 + rate.getD5Me().getRate() * 0.45)
                + 31.97 * (rate.getD3Ne().getRate() * 0.55 + rate.getD3Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d4 = new RankValue(weight);
    }

    public void calcD5Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD5Ne().getRate() * 0.55 + rate.getD5Me().getRate() * 0.45)
                + 39.97 * (rate.getD6Ne().getRate() * 0.55 + rate.getD6Me().getRate() * 0.45)
                + 31.97 * (rate.getD4Ne().getRate() * 0.55 + rate.getD4Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d5 = new RankValue(weight);
    }

    public void calcD6Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD6Ne().getRate() * 0.55 + rate.getD6Me().getRate() * 0.45)
                + 39.97 * (rate.getD7Ne().getRate() * 0.55 + rate.getD7Me().getRate() * 0.45)
                + 31.97 * (rate.getD5Ne().getRate() * 0.55 + rate.getD5Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d6 = new RankValue(weight);
    }

    public void calcD7Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD7Ne().getRate() * 0.55 + rate.getD7Me().getRate() * 0.45)
                + 39.97 * (rate.getD8Ne().getRate() * 0.55 + rate.getD8Me().getRate() * 0.45)
                + 31.97 * (rate.getD6Ne().getRate() * 0.55 + rate.getD6Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d7 = new RankValue(weight);
    }

    public void calcD8Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD8Ne().getRate() * 0.55 + rate.getD8Me().getRate() * 0.45)
                + 39.97 * (rate.getD9Ne().getRate() * 0.55 + rate.getD9Me().getRate() * 0.45)
                + 31.97 * (rate.getD7Ne().getRate() * 0.55 + rate.getD7Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d8 = new RankValue(weight);
    }

    public void calcD9Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD9Ne().getRate() * 0.55 + rate.getD9Me().getRate() * 0.45)
                + 39.97 * (rate.getD10Ne().getRate() * 0.55 + rate.getD10Me().getRate() * 0.45)
                + 31.97 * (rate.getD8Ne().getRate() * 0.55 + rate.getD8Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d9 = new RankValue(weight);
    }

    public void calcD10Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD10Ne().getRate() * 0.55 + rate.getD10Me().getRate() * 0.45)
                + 39.97 * (rate.getD11Ne().getRate() * 0.55 + rate.getD11Me().getRate() * 0.45)
                + 31.97 * (rate.getD9Ne().getRate() * 0.55 + rate.getD9Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d10 = new RankValue(weight);
    }

    public void calcD11Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD11Ne().getRate() * 0.55 + rate.getD11Me().getRate() * 0.45)
                + 39.97 * (rate.getD12Ne().getRate() * 0.55 + rate.getD12Me().getRate() * 0.45)
                + 31.97 * (rate.getD10Ne().getRate() * 0.55 + rate.getD10Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d11 = new RankValue(weight);
    }

    public void calcD12Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD12Ne().getRate() * 0.55 + rate.getD12Me().getRate() * 0.45)
                + 39.97 * (rate.getD13Ne().getRate() * 0.55 + rate.getD13Me().getRate() * 0.45)
                + 31.97 * (rate.getD11Ne().getRate() * 0.55 + rate.getD11Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d12 = new RankValue(weight);
    }

    public void calcD13Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD13Ne().getRate() * 0.55 + rate.getD13Me().getRate() * 0.45)
                + 39.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 31.97 * (rate.getD12Ne().getRate() * 0.55 + rate.getD12Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d13 = new RankValue(weight);
    }

    public void calcD14Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD13Ne().getRate() * 0.55 + rate.getD13Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d14 = new RankValue(weight);
    }

    public void calcD15Rank(Kl8MasterRatePo rate) {
        double weight = 55.57 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 39.97 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d15 = new RankValue(weight);
    }

    public void calcD20Rank(Kl8MasterRatePo rate) {
        double weight = 69.57 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 35.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 31.99 * (rate.getK3Ne().getRate() * 0.55 + rate.getK3Me().getRate() * 0.45);
        weight   = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.d20 = new RankValue(weight);
    }

    public void calcK1Rank(Kl8MasterRatePo rate) {
        double weight = 31.57 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 79.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 33.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 31.99 * (rate.getK3Ne().getRate() * 0.55 + rate.getK3Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.k1 = new RankValue(weight);
    }

    public void calcK2Rank(Kl8MasterRatePo rate) {
        double weight = 31.57 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 49.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 79.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 31.99 * (rate.getK3Ne().getRate() * 0.55 + rate.getK3Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.k2 = new RankValue(weight);
    }

    public void calcK3Rank(Kl8MasterRatePo rate) {
        double weight = 31.57 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 35.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 39.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 79.99 * (rate.getK3Ne().getRate() * 0.55 + rate.getK3Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.k3 = new RankValue(weight);
    }

    public void calcK4Rank(Kl8MasterRatePo rate) {
        double weight = 31.57 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 39.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 35.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 39.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 79.99 * (rate.getK4Ne().getRate() * 0.55 + rate.getK4Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.k4 = new RankValue(weight);
    }

    public void calcK5Rank(Kl8MasterRatePo rate) {
        double weight = 31.99 * (rate.getD20Ne().getRate() * 0.55 + rate.getD20Me().getRate() * 0.45)
                + 37.97 * (rate.getD15Ne().getRate() * 0.55 + rate.getD15Me().getRate() * 0.45)
                + 31.97 * (rate.getD14Ne().getRate() * 0.55 + rate.getD14Me().getRate() * 0.45)
                + 35.99 * (rate.getK1Ne().getRate() * 0.55 + rate.getK1Me().getRate() * 0.45)
                + 39.99 * (rate.getK2Ne().getRate() * 0.55 + rate.getK2Me().getRate() * 0.45)
                + 79.99 * (rate.getK5Ne().getRate() * 0.55 + rate.getK5Me().getRate() * 0.45);
        weight  = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.k5 = new RankValue(weight);
    }

}
