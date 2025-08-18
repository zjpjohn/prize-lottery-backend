package com.prize.lottery.domain.share.model;

import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.cmd.Num3LayerCmd;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.spider.open.OpenNum3Layer;
import com.prize.lottery.value.LayerValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
@Slf4j
@NoArgsConstructor
public class Num3LayerFilterDo {

    private Long          id;
    private String        period;
    private LotteryEnum   type;
    private LayerValue    layer1;
    private LayerValue    layer2;
    private LayerValue    layer3;
    private LayerValue    layer4;
    private LayerValue    layer5;
    private LocalDateTime editTime;
    private LocalDateTime calcTime;
    private Integer       version;

    public Num3LayerFilterDo(OpenNum3Layer layer, LotteryEnum type) {
        this.id      = IdWorker.nextId();
        this.type    = type;
        this.version = 0;
        this.period  = layer.getPeriod();
        this.setLayerValue(layer);
    }

    public Num3LayerFilterDo(Num3LayerCmd cmd) {
        this.id      = IdWorker.nextId();
        this.period  = cmd.getPeriod();
        this.type    = cmd.getType();
        this.version = 0;
        this.setLayerValue(cmd);
    }

    public Num3LayerFilterDo modify(OpenNum3Layer layer) {
        this.editTime = LocalDateTime.now();
        this.setLayerValue(layer);
        return this;
    }

    public Num3LayerFilterDo modify(Num3LayerCmd cmd) {
        this.setLayerValue(cmd);
        this.editTime = LocalDateTime.now();
        return this;
    }

    private void setLayerValue(OpenNum3Layer layer) {
        this.layer1 = layer.getLayer1();
        this.layer2 = layer.getLayer2();
        this.layer3 = layer.getLayer3();
        this.layer4 = layer.getLayer4();
        this.layer5 = layer.getLayer5();
    }

    private void setLayerValue(Num3LayerCmd cmd) {
        this.layer1 = Optional.ofNullable(cmd.getLayer1())
                              .filter(Num3LayerCmd.LayerInfo::isNotEmpty)
                              .map(Num3LayerCmd.LayerInfo::toValue)
                              .orElseGet(LayerValue::empty);
        this.layer2 = Optional.ofNullable(cmd.getLayer2())
                              .filter(Num3LayerCmd.LayerInfo::isNotEmpty)
                              .map(Num3LayerCmd.LayerInfo::toValue)
                              .orElseGet(LayerValue::empty);
        this.layer3 = Optional.ofNullable(cmd.getLayer3())
                              .filter(Num3LayerCmd.LayerInfo::isNotEmpty)
                              .map(Num3LayerCmd.LayerInfo::toValue)
                              .orElseGet(LayerValue::empty);
        this.layer4 = Optional.ofNullable(cmd.getLayer4())
                              .filter(Num3LayerCmd.LayerInfo::isNotEmpty)
                              .map(Num3LayerCmd.LayerInfo::toValue)
                              .orElseGet(LayerValue::empty);
        this.layer5 = Optional.ofNullable(cmd.getLayer5())
                              .filter(Num3LayerCmd.LayerInfo::isNotEmpty)
                              .map(Num3LayerCmd.LayerInfo::toValue)
                              .orElseGet(LayerValue::empty);
    }

    public boolean isEmpty() {
        return this.layer1 == null
                && this.layer2 == null
                && this.layer3 == null
                && this.layer4 == null
                && this.layer5 == null;
    }

    public void calcHit(Map<String, Integer> lottery) {
        this.calcTime = LocalDateTime.now();
        this.layer1.calcHit(lottery);
        this.layer2.calcHit(lottery);
        this.layer3.calcHit(lottery);
        Optional.ofNullable(this.layer4).ifPresent(v -> v.calcHit(lottery));
        Optional.ofNullable(this.layer5).ifPresent(v -> v.calcHit(lottery));
    }

}
