package com.prize.lottery.po.qlc;

import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.enums.RateLevel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QlcMasterRatePo {

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

    private StatHitValue r18Ne;
    private StatHitValue r18Me;
    private StatHitValue r18Hi;

    private StatHitValue r22Ne;
    private StatHitValue r22Me;
    private StatHitValue r22Hi;

    private StatHitValue k3Ne;
    private StatHitValue k3Me;
    private StatHitValue k3Hi;

    private StatHitValue k6Ne;
    private StatHitValue k6Me;
    private StatHitValue k6Hi;

    private LocalDateTime gmtCreate;

    public QlcMasterRatePo calcRate(List<QlcIcaiPo> data) {
        QlcIcaiPo po = data.get(0);
        this.period   = po.getPeriod();
        this.masterId = po.getMasterId();
        for (QlcChannel channel : QlcChannel.values()) {
            channel.calcRate(data, this);
        }
        return this;
    }

    public void calcRed1(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed1().getDataHit()).collect(Collectors.toList());
        this.setR1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed2(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed2().getDataHit()).collect(Collectors.toList());
        this.setR2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed3(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed3().getDataHit()).collect(Collectors.toList());
        this.setR3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setR3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setR3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcRed12(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed12().getDataHit()).collect(Collectors.toList());
        this.setR12Ne(RateLevel.NEAR.calcRate(hits, 3, false, 0));
        this.setR12Me(RateLevel.MIDDLE.calcRate(hits, 3, false, 0));
        this.setR12Hi(RateLevel.HIGH.calcRate(hits, 3, false, 0));
    }

    public void calcRed18(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed18().getDataHit()).collect(Collectors.toList());
        this.setR18Ne(RateLevel.NEAR.calcRate(hits, 4, true, 5));
        this.setR18Me(RateLevel.MIDDLE.calcRate(hits, 4, true, 5));
        this.setR18Hi(RateLevel.HIGH.calcRate(hits, 4, true, 5));
    }

    public void calcRed22(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getRed22().getDataHit()).collect(Collectors.toList());
        this.setR22Ne(RateLevel.NEAR.calcRate(hits, 5, true, 6));
        this.setR22Me(RateLevel.MIDDLE.calcRate(hits, 5, true, 6));
        this.setR22Hi(RateLevel.HIGH.calcRate(hits, 5, true, 6));
    }

    public void calcKill3(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getKill3().getDataHit()).collect(Collectors.toList());
        this.setK3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcKill6(List<QlcIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getKill6().getDataHit()).collect(Collectors.toList());
        this.setK6Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK6Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK6Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }
}
