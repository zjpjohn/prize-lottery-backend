package com.prize.lottery.domain.omit.model;

import com.google.common.collect.Lists;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryOmitPo;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class LottoPianOmitDo {

    private final LotteryEnum type;
    private final String      period;
    private final Integer     level;
    private       Omit        cb1;
    private       Omit        cb2;
    private       Omit        cb3;
    private       Omit        omit;

    public static List<LottoPianOmitDo> allLevelOmits(LotteryEnum type, String period, List<LotteryOmitPo> omits) {
        List<LottoPianOmitDo> result = Lists.newArrayList();
        for (int i = 0; i <= 9; i++) {
            LottoPianOmitDo pianOmit = new LottoPianOmitDo(type, period, i);
            if (pianOmit.n3Omit(omits)) {
                result.add(pianOmit);
            }
        }
        return result;
    }

    public LottoPianOmitDo(LotteryEnum type, String period, Integer level) {
        this.type   = type;
        this.period = period;
        this.level  = level;
    }

    public boolean n3Omit(List<LotteryOmitPo> omits) {
        String lastPeriod = type.lastPeriod(period, level);
        LotteryOmitPo lotteryOmit = omits.stream()
                                         .filter(e -> e.getPeriod().equals(lastPeriod))
                                         .findFirst()
                                         .orElse(null);
        if (lotteryOmit == null) {
            return false;
        }
        this.cb1 = fromN3Omit(lotteryOmit.getCb1());
        this.cb2 = fromN3Omit(lotteryOmit.getCb2());
        this.cb3 = fromN3Omit(lotteryOmit.getCb3());
        return true;
    }

    private Omit fromN3Omit(Omit base) {
        List<OmitValue> values = IntStream.range(0, 10).mapToObj(ball -> {
            String ballIdx = String.valueOf((ball + level) % 10);
            Integer value = base.getValues()
                                .stream()
                                .filter(e -> e.getKey().equals(ballIdx))
                                .findFirst()
                                .map(OmitValue::getValue)
                                .orElse(0);
            return OmitValue.of(String.valueOf(ball), value);
        }).sorted(Comparator.comparing(OmitValue::getKey)).collect(Collectors.toList());
        return new Omit(values);
    }

}
