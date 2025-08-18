package com.prize.lottery.po.dlt;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.RateLevel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DltMasterRatePo {

    private Long   id;
    private String period;
    private String masterId;
    private Date   gmtCreate;

    private StatHitValue r1Ne;
    private StatHitValue r1Me;
    private StatHitValue r1Hi;

    private StatHitValue r2Ne;
    private StatHitValue r2Me;
    private StatHitValue r2Hi;

    private StatHitValue r3Ne;
    private StatHitValue r3Me;
    private StatHitValue r3Hi;

    private StatHitValue r10Ne;
    private StatHitValue r10Me;
    private StatHitValue r10Hi;

    private StatHitValue r20Ne;
    private StatHitValue r20Me;
    private StatHitValue r20Hi;

    private StatHitValue rk3Ne;
    private StatHitValue rk3Me;
    private StatHitValue rk3Hi;

    private StatHitValue rk6Ne;
    private StatHitValue rk6Me;
    private StatHitValue rk6Hi;

    private StatHitValue b1Ne;
    private StatHitValue b1Me;
    private StatHitValue b1Hi;

    private StatHitValue b2Ne;
    private StatHitValue b2Me;
    private StatHitValue b2Hi;

    private StatHitValue b6Ne;
    private StatHitValue b6Me;
    private StatHitValue b6Hi;

    private StatHitValue bkNe;
    private StatHitValue bkMe;
    private StatHitValue bkHi;


    public DltMasterRatePo calcRate(List<DltIcaiPo> data) {
        DltIcaiPo po = data.get(0);
        this.period   = po.getPeriod();
        this.masterId = po.getMasterId();
        for (DltChannel channel : DltChannel.values()) {
            channel.calcRate(data, this);
        }
        return this;
    }

    public void calcRed1(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed1().getDataHit()).collect(Collectors.toList());
        this.setR1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed2(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed2().getDataHit()).collect(Collectors.toList());
        this.setR2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed3(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed3().getDataHit()).collect(Collectors.toList());
        this.setR3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed10(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed10().getDataHit()).collect(Collectors.toList());
        this.setR10Ne(RateLevel.NEAR.calcRate(hits, 2, false, 0));
        this.setR10Me(RateLevel.MIDDLE.calcRate(hits, 2, false, 0));
        this.setR10Hi(RateLevel.HIGH.calcRate(hits, 2, false, 0));
    }

    public void calcRed20(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed20().getDataHit()).collect(Collectors.toList());
        this.setR20Ne(RateLevel.NEAR.calcRate(hits, 4, true, 5));
        this.setR20Me(RateLevel.MIDDLE.calcRate(hits, 4, true, 5));
        this.setR20Hi(RateLevel.HIGH.calcRate(hits, 4, true, 5));
    }

    public void calcRk3(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRedKill3().getDataHit()).collect(Collectors.toList());
        this.setRk3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setRk3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setRk3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRk6(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRedKill6().getDataHit()).collect(Collectors.toList());
        this.setRk6Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setRk6Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setRk6Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcBlue1(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlue1().getDataHit()).collect(Collectors.toList());
        this.setB1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setB1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setB1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcBlue2(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlue2().getDataHit()).collect(Collectors.toList());
        this.setB2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setB2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setB2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcBlue6(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlue6().getDataHit()).collect(Collectors.toList());
        this.setB6Ne(RateLevel.NEAR.calcRate(hits, 1, true, 2));
        this.setB6Me(RateLevel.MIDDLE.calcRate(hits, 1, true, 2));
        this.setB6Hi(RateLevel.HIGH.calcRate(hits, 1, true, 2));
    }

    public void calcBk(List<DltIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getBlueKill3().getDataHit()).collect(Collectors.toList());
        this.setBkNe(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setBkMe(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setBkHi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }
}
