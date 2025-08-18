package com.prize.lottery.po.pl3;

import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.value.RankValue;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class Pl3MasterRankPo {

    private Long          id;
    private String        period;
    private String        masterId;
    private Integer       hot;
    //是否是vip专家:0-否,1-是
    private Integer       isVip;
    //排名与权重
    private RankValue     rank;
    private RankValue     dan1;
    private RankValue     dan2;
    private RankValue     dan3;
    private RankValue     com5;
    private RankValue     com6;
    private RankValue     com7;
    private RankValue     kill1;
    private RankValue     kill2;
    private RankValue     comb3;
    private RankValue     comb4;
    private RankValue     comb5;
    private LocalDateTime gmtCreate;

    public Pl3MasterRankPo calc(Pl3MasterRatePo rate) {
        this.setPeriod(rate.getPeriod());
        this.setMasterId(rate.getMasterId());
        this.calcRank(rate);
        for (Pl3Channel channel : Pl3Channel.values()) {
            channel.calcRank(rate, this);
        }
        return this;
    }

    public void calcRank(Pl3MasterRatePo rate) {
        double weight = 999.99 * ((rate.getC7Ne().getRate() * 0.50 + rate.getC7Ne().getFullRate() * 0.55) * 0.35
                + (rate.getC7Me().getRate() * 0.50 + rate.getC7Me().getFullRate() * 0.55) * 0.33
                + (rate.getC7Hi().getRate() * 0.50 + rate.getC7Hi().getFullRate() * 0.55) * 0.32)
                + 39.99 * ((rate.getC6Ne().getRate() * 0.50 + rate.getC6Ne().getFullRate() * 0.55) * 0.35
                + (rate.getC6Me().getRate() * 0.50 + rate.getC6Me().getFullRate() * 0.55) * 0.33
                + (rate.getC6Hi().getRate() * 0.50 + rate.getC6Hi().getFullRate() * 0.55) * 0.32)
                + 19.99 * ((rate.getC5Ne().getRate() * 0.50 + rate.getC5Ne().getFullRate() * 0.55) * 0.35
                + (rate.getC5Me().getRate() * 0.50 + rate.getC5Me().getFullRate() * 0.55) * 0.33
                + (rate.getC5Hi().getRate() * 0.50 + rate.getC5Hi().getFullRate() * 0.55) * 0.32)
                + 699.99 * (rate.getK1Ne().getRate() * 0.35
                + rate.getK1Me().getRate() * 0.33
                + rate.getK1Hi().getRate() * 0.32)
                + 159.99 * (rate.getK2Ne().getRate() * 0.35
                + rate.getK2Me().getRate() * 0.33
                + rate.getK2Me().getRate() * 0.32)
                + 99.99 * (rate.getD3Ne().getRate() * 0.35
                + rate.getD3Me().getRate() * 0.33
                + rate.getD3Hi().getRate() * 0.32)
                + 39.99 * (rate.getD2Ne().getRate() * 0.35
                + rate.getD2Me().getRate() * 0.33
                + rate.getD2Hi().getRate() * 0.32)
                + 2.99 * (rate.getD1Ne().getRate() * 0.35
                + rate.getD1Me().getRate() * 0.33
                + rate.getD1Hi().getRate() * 0.32);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.rank = new RankValue(weight);
    }

    public void calcD1Rank(Pl3MasterRatePo rate) {
        double weight = 999.99 * (rate.getD1Ne().getRate() * 0.38
                + rate.getD1Me().getRate() * 0.32
                + rate.getD1Hi().getRate() * 0.30) + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25) + 1.99 * ((rate.getC7Ne().getRate() * 0.55
                + rate.getC7Ne().getFullRate() * 0.45) * 0.4
                + (rate.getC7Me().getRate() * 0.55 + rate.getC7Me().getFullRate() * 0.45) * 0.35
                + (rate.getC7Hi().getRate() * 0.55 + rate.getC7Hi().getFullRate() * 0.45) * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.dan1 = new RankValue(weight);
    }

    public void calcD2Rank(Pl3MasterRatePo rate) {
        double weight = 999.99 * (rate.getD2Ne().getRate() * 0.38
                + rate.getD2Me().getRate() * 0.32
                + rate.getD2Hi().getRate() * 0.30) + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25) + 1.99 * ((rate.getC7Ne().getRate() * 0.55
                + rate.getC7Ne().getFullRate() * 0.45) * 0.4
                + (rate.getC7Me().getRate() * 0.55 + rate.getC7Me().getFullRate() * 0.45) * 0.35
                + (rate.getC7Hi().getRate() * 0.55 + rate.getC7Hi().getFullRate() * 0.45) * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.dan2 = new RankValue(weight);
    }

    public void calcD3Rank(Pl3MasterRatePo rate) {
        double weight = 799.99 * (rate.getD3Ne().getRate() * 0.35
                + rate.getD3Me().getRate() * 0.33
                + rate.getD3Hi().getRate() * 0.32) + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25) + 1.99 * ((rate.getC7Ne().getRate() * 0.55
                + rate.getC7Ne().getFullRate() * 0.45) * 0.4
                + (rate.getC7Me().getRate() * 0.55 + rate.getC7Me().getFullRate() * 0.45) * 0.35
                + (rate.getC7Hi().getRate() * 0.55 + rate.getC7Hi().getFullRate() * 0.45) * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.dan3 = new RankValue(weight);
    }

    public void calcC5Rank(Pl3MasterRatePo rate) {
        double weight = 799.99 * ((rate.getC5Ne().getRate() * 0.50 + rate.getC5Ne().getFullRate() * 0.55) * 0.35
                + (rate.getC5Me().getRate() * 0.50 + rate.getC5Me().getFullRate() * 0.55) * 0.33
                + (rate.getC5Hi().getRate() * 0.50 + rate.getC5Hi().getFullRate() * 0.55) * 0.32)
                + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.com5 = new RankValue(weight);
    }

    public void calcC6Rank(Pl3MasterRatePo rate) {
        double weight = 799.99 * ((rate.getC6Ne().getRate() * 0.50 + rate.getC6Ne().getFullRate() * 0.55) * 0.35
                + (rate.getC6Me().getRate() * 0.50 + rate.getC6Me().getFullRate() * 0.55) * 0.33
                + (rate.getC6Hi().getRate() * 0.50 + rate.getC6Hi().getFullRate() * 0.55) * 0.32)
                + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.com6 = new RankValue(weight);
    }

    public void calcC7Rank(Pl3MasterRatePo rate) {
        double weight = 799.99 * ((rate.getC7Ne().getRate() * 0.50 + rate.getC7Ne().getFullRate() * 0.55) * 0.35
                + (rate.getC7Me().getRate() * 0.50 + rate.getC7Me().getFullRate() * 0.55) * 0.33
                + (rate.getC7Hi().getRate() * 0.50 + rate.getC7Hi().getFullRate() * 0.55) * 0.32)
                + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25);
        weight    = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.com7 = new RankValue(weight);
    }

    public void calcK1Rank(Pl3MasterRatePo rate) {
        double weight = 799.99 * (rate.getK1Ne().getRate() * 0.35
                + rate.getK1Me().getRate() * 0.33
                + rate.getK1Hi().getRate() * 0.32) + 2.99 * ((rate.getC7Ne().getRate() * 0.50
                + rate.getC7Ne().getFullRate() * 0.55) * 0.34
                + (rate.getC7Me().getRate() * 0.50 + rate.getC7Me().getFullRate() * 0.55) * 0.33
                + (rate.getC7Hi().getRate() * 0.50 + rate.getC7Hi().getFullRate() * 0.55) * 0.33);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.kill1 = new RankValue(weight);
    }

    public void calcK2Rank(Pl3MasterRatePo rate) {
        double weight = 799.99 * (rate.getK2Ne().getRate() * 0.35
                + rate.getK2Me().getRate() * 0.33
                + rate.getK2Hi().getRate() * 0.32) + 2.99 * (rate.getK1Ne().getRate() * 0.34
                + rate.getK1Me().getRate() * 0.33
                + rate.getK1Hi().getRate() * 0.33) + 1.99 * ((rate.getC7Ne().getRate() * 0.55
                + rate.getC7Ne().getFullRate() * 0.45) * 0.4
                + (rate.getC7Me().getRate() * 0.55 + rate.getC7Me().getFullRate() * 0.45) * 0.35
                + (rate.getC7Hi().getRate() * 0.55 + rate.getC7Hi().getFullRate() * 0.45) * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.kill2 = new RankValue(weight);
    }

    public void calcCb3Rank(Pl3MasterRatePo rate) {
        double weight = 999.99 * (rate.getCb3Ne().getRate() * 0.35
                + rate.getCb3Me().getRate() * 0.33
                + rate.getCb3Hi().getRate() * 0.32) + 1.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25) + 1.99 * ((rate.getC7Ne().getRate() * 0.5
                + rate.getC7Ne().getFullRate() * 0.6) * 0.4
                + (rate.getC7Me().getRate() * 0.5 + rate.getC7Me().getFullRate() * 0.6) * 0.35
                + (rate.getC7Hi().getRate() * 0.5 + rate.getC7Hi().getFullRate() * 0.6) * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.comb3 = new RankValue(weight);
    }

    public void calcCb4Rank(Pl3MasterRatePo rate) {
        double weight = 999.99 * (rate.getCb4Ne().getRate() * 0.35
                + rate.getCb4Me().getRate() * 0.33
                + rate.getCb4Hi().getRate() * 0.32) + 1.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25) + 1.99 * ((rate.getC7Ne().getRate() * 0.5
                + rate.getC7Ne().getFullRate() * 0.6) * 0.4
                + (rate.getC7Me().getRate() * 0.5 + rate.getC7Me().getFullRate() * 0.6) * 0.35
                + (rate.getC7Hi().getRate() * 0.5 + rate.getC7Hi().getFullRate() * 0.6) * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.comb4 = new RankValue(weight);
    }

    public void calcCb5Rank(Pl3MasterRatePo rate) {
        double weight = 999.99 * (rate.getCb5Ne().getRate() * 0.35
                + rate.getCb5Me().getRate() * 0.33
                + rate.getCb5Hi().getRate() * 0.32) + 2.99 * (rate.getK1Ne().getRate() * 0.4
                + rate.getK1Me().getRate() * 0.35
                + rate.getK1Hi().getRate() * 0.25) + 1.99 * ((rate.getC7Ne().getRate() * 0.5
                + rate.getC7Ne().getFullRate() * 0.6) * 0.4
                + (rate.getC7Me().getRate() * 0.5 + rate.getC7Me().getFullRate() * 0.6) * 0.35
                + (rate.getC7Hi().getRate() * 0.5 + rate.getC7Hi().getFullRate() * 0.6) * 0.25);
        weight     = BigDecimal.valueOf(weight).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.comb5 = new RankValue(weight);
    }
}
