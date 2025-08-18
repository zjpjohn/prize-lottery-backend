package com.prize.lottery.domain.share.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.domain.share.valobj.ComWarnValue;
import com.prize.lottery.enums.HitType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.WarningComplex;
import com.prize.lottery.value.WarningInt;
import com.prize.lottery.value.WarningText;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Num3ComWarnDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 356200201244477094L;

    private Long           id;
    private String         period;
    private LotteryEnum    type;
    private WarningComplex dan;
    private WarningText    kill;
    private WarningComplex twoMa;
    private WarningComplex zu6;
    private WarningComplex zu3;
    private WarningInt     kuaList;
    private WarningInt     sumList;
    private HitType        hit;
    private Integer        edits;
    private LocalDateTime  editTime;
    private LocalDateTime  calcTime;
    //数据版本
    private Integer        version;

    public Num3ComWarnDo(String period, LotteryEnum type, ComWarnValue value) {
        this.period  = period;
        this.type    = type;
        this.id      = IdWorker.nextId();
        this.dan     = WarningComplex.of(value.getDanList());
        this.twoMa   = WarningComplex.of(value.getTwoMaList());
        this.zu6     = WarningComplex.of(value.getZu6List());
        this.zu3     = WarningComplex.of(value.getZu3List());
        this.kill    = new WarningText(value.getKillList());
        this.kuaList = new WarningInt(value.getKuaList());
        this.sumList = new WarningInt(value.getSumList());
        this.hit     = HitType.UN_OPEN;
        this.version = 0;
    }

    public void filter(List<String> zu6List, List<String> zu3List) {
        this.zu3 = this.zu3.filter(zu3List);
        this.zu6 = this.zu6.filter(zu6List);
    }

    public void modify(ComWarnValue value) {
        this.dan      = WarningComplex.of(value.getDanList());
        this.twoMa    = WarningComplex.of(value.getTwoMaList());
        this.zu6      = WarningComplex.of(value.getZu6List());
        this.zu3      = WarningComplex.of(value.getZu3List());
        this.kill     = new WarningText(value.getKillList());
        this.kuaList  = new WarningInt(value.getKuaList());
        this.sumList  = new WarningInt(value.getSumList());
        this.edits    = this.edits + 1;
        this.editTime = LocalDateTime.now();
    }

    public void calcHit(Map<String, Integer> lottery) {
        this.dan      = this.dan.calcHit(lottery);
        this.twoMa    = this.twoMa.calcHit(lottery);
        this.zu3      = this.zu3.calcHit(lottery);
        this.zu6      = this.zu6.calcHit(lottery);
        this.calcTime = LocalDateTime.now();
        this.hit      = HitType.UN_HIT;
        if (lottery.size() == 1 && this.zu3.hasHit()) {
            this.hit = HitType.HIT_SAME;
            return;
        }
        if (lottery.size() == 2 && this.zu3.hasHit()) {
            this.hit = HitType.HIT_ZU3;
            return;
        }
        if (lottery.size() == 3 && this.zu6.hasHit()) {
            this.hit = HitType.HIT_ZU6;
        }
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }
}
