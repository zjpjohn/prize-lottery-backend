package com.prize.lottery.po.fc3d;

import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.RateLevel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Fc3dMasterRatePo {

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

    private StatHitValue c5Ne;
    private StatHitValue c5Me;
    private StatHitValue c5Hi;

    private StatHitValue c6Ne;
    private StatHitValue c6Me;
    private StatHitValue c6Hi;

    private StatHitValue c7Ne;
    private StatHitValue c7Me;
    private StatHitValue c7Hi;

    private StatHitValue k1Ne;
    private StatHitValue k1Me;
    private StatHitValue k1Hi;

    private StatHitValue k2Ne;
    private StatHitValue k2Me;
    private StatHitValue k2Hi;

    private StatHitValue cb3Ne;
    private StatHitValue cb3Me;
    private StatHitValue cb3Hi;

    private StatHitValue cb4Ne;
    private StatHitValue cb4Me;
    private StatHitValue cb4Hi;

    private StatHitValue cb5Ne;
    private StatHitValue cb5Me;
    private StatHitValue cb5Hi;

    private LocalDateTime gmtCreate;

    public Fc3dMasterRatePo calcMasterRate(List<Fc3dIcaiPo> data) {
        Fc3dIcaiPo icaiPo = data.get(0);
        this.setPeriod(icaiPo.getPeriod());
        this.setMasterId(icaiPo.getMasterId());
        for (Fc3dChannel channel : Fc3dChannel.values()) {
            channel.calcRate(data, this);
        }
        return this;
    }

    public void calcDan1(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getDan1().getDataHit()).collect(Collectors.toList());
        this.setD1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcDan2(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getDan2().getDataHit()).collect(Collectors.toList());
        this.setD2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcDan3(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getDan3().getDataHit()).collect(Collectors.toList());
        this.setD3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setD3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setD3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcCom5(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getCom5().getDataHit()).collect(Collectors.toList());
        this.setC5Ne(RateLevel.NEAR.calcRate(hits, 2, true, 3));
        this.setC5Me(RateLevel.MIDDLE.calcRate(hits, 2, true, 3));
        this.setC5Hi(RateLevel.HIGH.calcRate(hits, 2, true, 3));
    }

    public void calcCom6(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getCom6().getDataHit()).collect(Collectors.toList());
        this.setC6Ne(RateLevel.NEAR.calcRate(hits, 2, true, 3));
        this.setC6Me(RateLevel.MIDDLE.calcRate(hits, 2, true, 3));
        this.setC6Hi(RateLevel.HIGH.calcRate(hits, 2, true, 3));
    }

    public void calcCom7(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getCom7().getDataHit()).collect(Collectors.toList());
        this.setC7Ne(RateLevel.NEAR.calcRate(hits, 2, true, 3));
        this.setC7Me(RateLevel.MIDDLE.calcRate(hits, 2, true, 3));
        this.setC7Hi(RateLevel.HIGH.calcRate(hits, 2, true, 3));
    }

    public void calcKill1(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getKill1().getDataHit()).collect(Collectors.toList());
        this.setK1Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK1Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK1Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcKill2(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getKill2().getDataHit()).collect(Collectors.toList());
        this.setK2Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setK2Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setK2Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcComb3(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getComb3().getDataHit()).collect(Collectors.toList());
        this.setCb3Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setCb3Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setCb3Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcComb4(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getComb4().getDataHit()).collect(Collectors.toList());
        this.setCb4Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setCb4Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setCb4Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }

    public void calcComb5(List<Fc3dIcaiPo> data) {
        List<Integer> hits = data.stream().map(v -> v.getComb5().getDataHit()).collect(Collectors.toList());
        this.setCb5Ne(RateLevel.NEAR.calcRate(hits, 1, false, 0));
        this.setCb5Me(RateLevel.MIDDLE.calcRate(hits, 1, false, 0));
        this.setCb5Hi(RateLevel.HIGH.calcRate(hits, 1, false, 0));
    }
}
