package com.prize.lottery.po.kl8;

import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.enums.RateLevel;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Kl8MasterRatePo {

    private Long   id;
    private String period;
    private String masterId;

    private StatHitValue d1Ne;
    private StatHitValue d1Me;
    private StatHitValue d1Hi;

    private StatHitValue d2Ne;
    private StatHitValue d2Me;
    private StatHitValue d2Hi;

    private StatHitValue d3Ne;
    private StatHitValue d3Me;
    private StatHitValue d3Hi;

    private StatHitValue d4Ne;
    private StatHitValue d4Me;
    private StatHitValue d4Hi;

    private StatHitValue d5Ne;
    private StatHitValue d5Me;
    private StatHitValue d5Hi;

    private StatHitValue d6Ne;
    private StatHitValue d6Me;
    private StatHitValue d6Hi;

    private StatHitValue d7Ne;
    private StatHitValue d7Me;
    private StatHitValue d7Hi;

    private StatHitValue d8Ne;
    private StatHitValue d8Me;
    private StatHitValue d8Hi;

    private StatHitValue d9Ne;
    private StatHitValue d9Me;
    private StatHitValue d9Hi;

    private StatHitValue d10Ne;
    private StatHitValue d10Me;
    private StatHitValue d10Hi;

    private StatHitValue d11Ne;
    private StatHitValue d11Me;
    private StatHitValue d11Hi;

    private StatHitValue d12Ne;
    private StatHitValue d12Me;
    private StatHitValue d12Hi;

    private StatHitValue d13Ne;
    private StatHitValue d13Me;
    private StatHitValue d13Hi;

    private StatHitValue d14Ne;
    private StatHitValue d14Me;
    private StatHitValue d14Hi;

    private StatHitValue d15Ne;
    private StatHitValue d15Me;
    private StatHitValue d15Hi;

    private StatHitValue d20Ne;
    private StatHitValue d20Me;
    private StatHitValue d20Hi;

    private StatHitValue k1Ne;
    private StatHitValue k1Me;
    private StatHitValue k1Hi;

    private StatHitValue k2Ne;
    private StatHitValue k2Me;
    private StatHitValue k2Hi;

    private StatHitValue k3Ne;
    private StatHitValue k3Me;
    private StatHitValue k3Hi;

    private StatHitValue k4Ne;
    private StatHitValue k4Me;
    private StatHitValue k4Hi;

    private StatHitValue k5Ne;
    private StatHitValue k5Me;
    private StatHitValue k5Hi;

    private LocalDateTime gmtCreate;


    private Function<List<Kl8IcaiInfoPo>, List<Integer>> fieldHit(Function<Kl8IcaiInfoPo, ForecastValue> function) {
        return list -> list.stream().map(v -> function.apply(v).getDataHit()).collect(Collectors.toList());
    }

    public Kl8MasterRatePo calcRate(List<Kl8IcaiInfoPo> data) {
        Kl8IcaiInfoPo icaiInfo = data.get(0);
        this.period   = icaiInfo.getPeriod();
        this.masterId = icaiInfo.getMasterId();
        for (Kl8Channel channel : Kl8Channel.values()) {
            channel.calcRate(data, this);
        }
        return this;
    }

    public void calcD1(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd1).apply(data);
        this.setD1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));

    }

    public void calcD2(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd2).apply(data);
        this.setD2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));

    }

    public void calcD3(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd3).apply(data);
        this.setD3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcD4(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd4).apply(data);
        this.setD4Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD4Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD4Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcD5(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd5).apply(data);
        this.setD5Ne(RateLevel.NEAR.calcRate(hits, 2, false, 0));
        this.setD5Me(RateLevel.MIDDLE.calcRate(hits, 2, false, 0));
        this.setD5Hi(RateLevel.HIGH.calcRate(hits, 2, false, 0));
    }

    public void calcD6(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd6).apply(data);
        this.setD6Ne(RateLevel.NEAR.calcRate(hits, 2, false, 0));
        this.setD6Me(RateLevel.MIDDLE.calcRate(hits, 2, false, 0));
        this.setD6Hi(RateLevel.HIGH.calcRate(hits, 2, false, 0));
    }

    public void calcD7(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd7).apply(data);
        this.setD7Ne(RateLevel.NEAR.calcRate(hits, 3, false, 0));
        this.setD7Me(RateLevel.MIDDLE.calcRate(hits, 3, false, 0));
        this.setD7Hi(RateLevel.HIGH.calcRate(hits, 3, false, 0));
    }

    public void calcD8(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd8).apply(data);
        this.setD8Ne(RateLevel.NEAR.calcRate(hits, 4, false, 0));
        this.setD8Me(RateLevel.MIDDLE.calcRate(hits, 4, false, 0));
        this.setD8Hi(RateLevel.HIGH.calcRate(hits, 4, false, 0));
    }

    public void calcD9(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd9).apply(data);
        this.setD9Ne(RateLevel.NEAR.calcRate(hits, 4, false, 0));
        this.setD9Me(RateLevel.MIDDLE.calcRate(hits, 4, false, 0));
        this.setD9Hi(RateLevel.HIGH.calcRate(hits, 4, false, 0));
    }

    public void calcD10(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd10).apply(data);
        this.setD10Ne(RateLevel.NEAR.calcRate(hits, 5, false, 0));
        this.setD10Me(RateLevel.MIDDLE.calcRate(hits, 5, false, 0));
        this.setD10Hi(RateLevel.HIGH.calcRate(hits, 5, false, 0));
    }

    public void calcD11(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd11).apply(data);
        this.setD11Ne(RateLevel.NEAR.calcRate(hits, 6, false, 0));
        this.setD11Me(RateLevel.MIDDLE.calcRate(hits, 6, false, 0));
        this.setD11Hi(RateLevel.HIGH.calcRate(hits, 6, false, 0));
    }

    public void calcD12(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd12).apply(data);
        this.setD12Ne(RateLevel.NEAR.calcRate(hits, 6, false, 0));
        this.setD12Me(RateLevel.MIDDLE.calcRate(hits, 6, false, 0));
        this.setD12Hi(RateLevel.HIGH.calcRate(hits, 6, false, 0));
    }

    public void calcD13(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd13).apply(data);
        this.setD13Ne(RateLevel.NEAR.calcRate(hits, 6, false, 0));
        this.setD13Me(RateLevel.MIDDLE.calcRate(hits, 6, false, 0));
        this.setD13Hi(RateLevel.HIGH.calcRate(hits, 6, false, 0));
    }

    public void calcD14(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd14).apply(data);
        this.setD14Ne(RateLevel.NEAR.calcRate(hits, 6, false, 0));
        this.setD14Me(RateLevel.MIDDLE.calcRate(hits, 6, false, 0));
        this.setD14Hi(RateLevel.HIGH.calcRate(hits, 6, false, 0));
    }

    public void calcD15(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd15).apply(data);
        this.setD15Ne(RateLevel.NEAR.calcRate(hits, 7, false, 0));
        this.setD15Me(RateLevel.MIDDLE.calcRate(hits, 7, false, 0));
        this.setD15Hi(RateLevel.HIGH.calcRate(hits, 7, false, 0));
    }

    public void calcD20(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getXd20).apply(data);
        this.setD20Ne(RateLevel.NEAR.calcRate(hits, 8, false, 0));
        this.setD20Me(RateLevel.MIDDLE.calcRate(hits, 8, false, 0));
        this.setD20Hi(RateLevel.HIGH.calcRate(hits, 8, false, 0));
    }

    public void calcK1(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getSk1).apply(data);
        this.setK1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcK2(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getSk2).apply(data);
        this.setK2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcK3(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getSk3).apply(data);
        this.setK3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcK4(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getSk4).apply(data);
        this.setK4Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK4Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK4Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcK5(List<Kl8IcaiInfoPo> data) {
        List<Integer> hits = fieldHit(Kl8IcaiInfoPo::getSk5).apply(data);
        this.setK5Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK5Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK5Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

}
