package com.prize.lottery.po.ssq;

import com.prize.lottery.enums.RateLevel;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SsqMasterRatePo {

    private Long   id;
    private String period;
    private String masterId;

    private StatHitValue r1Ne;
    private StatHitValue r1Me;
    private StatHitValue r1Hi;

    private StatHitValue r2Ne;
    private StatHitValue r2Me;
    private StatHitValue r2Hi;

    private StatHitValue r3Ne;
    private StatHitValue r3Me;
    private StatHitValue r3Hi;

    private StatHitValue r12Ne;
    private StatHitValue r12Me;
    private StatHitValue r12Hi;

    private StatHitValue r20Ne;
    private StatHitValue r20Me;
    private StatHitValue r20Hi;

    private StatHitValue r25Ne;
    private StatHitValue r25Me;
    private StatHitValue r25Hi;

    private StatHitValue rk3Ne;
    private StatHitValue rk3Me;
    private StatHitValue rk3Hi;

    private StatHitValue rk6Ne;
    private StatHitValue rk6Me;
    private StatHitValue rk6Hi;

    private StatHitValue b3Ne;
    private StatHitValue b3Me;
    private StatHitValue b3Hi;

    private StatHitValue b5Ne;
    private StatHitValue b5Me;
    private StatHitValue b5Hi;

    private StatHitValue bkNe;
    private StatHitValue bkMe;
    private StatHitValue bkHi;

    private LocalDateTime gmtCreate;

    public SsqMasterRatePo calcRate(List<SsqIcaiPo> data) {
        SsqIcaiPo po = data.get(0);
        this.period   = po.getPeriod();
        this.masterId = po.getMasterId();
        for (SsqChannel channel : SsqChannel.values()) {
            channel.calcRate(data, this);
        }
        return this;
    }

    public void calcRed1(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed1().getDataHit()).collect(Collectors.toList());
        this.setR1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed2(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed2().getDataHit()).collect(Collectors.toList());
        this.setR2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed3(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed3().getDataHit()).collect(Collectors.toList());
        this.setR3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed12(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed12().getDataHit()).collect(Collectors.toList());
        this.setR12Ne(RateLevel.NEAR.calcRate(hits, 3, false, 0));
        this.setR12Me(RateLevel.MIDDLE.calcRate(hits, 3, false, 0));
        this.setR12Hi(RateLevel.HIGH.calcRate(hits, 3, false, 0));
    }

    public void calcRed20(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed20().getDataHit()).collect(Collectors.toList());
        this.setR20Ne(RateLevel.NEAR.calcRate(hits, 4, true, 5));
        this.setR20Me(RateLevel.MIDDLE.calcRate(hits, 4, true, 5));
        this.setR20Hi(RateLevel.HIGH.calcRate(hits, 4, true, 5));
    }

    public void calcRed25(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed25().getDataHit()).collect(Collectors.toList());
        this.setR25Ne(RateLevel.NEAR.calcRate(hits, 5, true, 6));
        this.setR25Me(RateLevel.MIDDLE.calcRate(hits, 5, true, 6));
        this.setR25Hi(RateLevel.HIGH.calcRate(hits, 5, true, 6));
    }

    public void calcRk3(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRedKill3().getDataHit()).collect(Collectors.toList());
        this.setRk3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setRk3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setRk3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRk6(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRedKill6().getDataHit()).collect(Collectors.toList());
        this.setRk6Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setRk6Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setRk6Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcB3(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlue3().getDataHit()).collect(Collectors.toList());
        this.setB3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setB3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setB3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcB5(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlue5().getDataHit()).collect(Collectors.toList());
        this.setB5Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setB5Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setB5Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcBk(List<SsqIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlueKill().getDataHit()).collect(Collectors.toList());
        this.setBkNe(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setBkMe(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setBkHi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }
}
