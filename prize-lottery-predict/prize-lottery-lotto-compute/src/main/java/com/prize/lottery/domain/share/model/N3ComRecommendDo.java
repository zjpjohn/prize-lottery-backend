package com.prize.lottery.domain.share.model;

import com.google.common.collect.Lists;
import com.prize.lottery.enums.WarningEnums;
import com.prize.lottery.enums.WarningHit;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.ComRecommend;
import com.prize.lottery.value.RecValueItem;
import com.prize.lottery.value.WarningValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class N3ComRecommendDo {

    private String             period;
    private ComRecommend       zu6;
    private ComRecommend       zu3;
    private Integer            type;
    private Integer            hit;
    private WarningHit         lastHit;
    private LocalDateTime      calcTime;
    private List<EarlyWarning> warnings;

    public N3ComRecommendDo(String period, ComRecommend zu6, ComRecommend zu3) {
        this.period = period;
        this.zu6    = zu6;
        this.zu3    = zu3;
        Map<WarningEnums, Set<String>> zu6Warnings = zu6.calcWarning();
        Map<WarningEnums, Set<String>> zu3Warnings = zu3.calcWarning();
        warnings = Lists.newArrayList();
        zu6Warnings.forEach((k, v) -> {
            v.addAll(zu3Warnings.get(k));
            List<String> value = v.stream().sorted().collect(Collectors.toList());
            warnings.add(new EarlyWarning(k, value));
        });
    }

    public List<String> getDanWarnings() {
        return warnings.stream()
                       .filter(e -> e.getType() == WarningEnums.DAN_WARNING)
                       .map(EarlyWarning::getWarn)
                       .findFirst()
                       .map(WarningValue::getValues)
                       .orElseGet(Collections::emptyList);
    }

    public List<String> getKillWarnings() {
        return warnings.stream()
                       .filter(e -> e.getType() == WarningEnums.KILL_WARNING)
                       .map(EarlyWarning::getWarn)
                       .findFirst()
                       .map(WarningValue::getValues)
                       .orElseGet(Collections::emptyList);
    }

    public void addDanWarning(List<String> dans) {
        warnings.removeIf(e -> e.getType() == WarningEnums.DAN_WARNING);
        warnings.add(new EarlyWarning(WarningEnums.DAN_WARNING, dans));
    }

    public void addKillWarning(List<String> kills) {
        warnings.removeIf(e -> e.getType() == WarningEnums.KILL_WARNING);
        warnings.add(new EarlyWarning(WarningEnums.KILL_WARNING, kills));
    }

    public void calcHit(List<String> lottos) {
        calcTime = LocalDateTime.now();
        Map<String, Integer> judgeLottery = ICaiConstants.judgeLottery(lottos);
        this.type = judgeLottery.size();
        Long zu3Hit = this.calcHit(zu3, judgeLottery);
        Long zu6Hit = this.calcHit(zu6, judgeLottery);
        this.hit = WarningHit.NONE_HIT.value();
        if (judgeLottery.size() == 1 && zu3Hit >= 1) {
            this.hit = WarningHit.BAOZI_HIT.value();
            return;
        }
        if (judgeLottery.size() == 2 && zu3Hit >= 1) {
            this.hit = WarningHit.ZU3_HIT.value();
            return;
        }
        if (judgeLottery.size() == 3 && zu6Hit >= 1) {
            this.hit = WarningHit.ZU6_HIT.value();
        }
    }

    private Long calcHit(ComRecommend recommend, Map<String, Integer> judge) {
        List<RecValueItem> hitItems = recommend.getItems()
                                               .stream()
                                               .map(v -> v.valueHit(judge))
                                               .collect(Collectors.toList());
        recommend.setItems(hitItems);
        return hitItems.stream().filter(v -> v.getHit() >= 1).count();
    }
}
