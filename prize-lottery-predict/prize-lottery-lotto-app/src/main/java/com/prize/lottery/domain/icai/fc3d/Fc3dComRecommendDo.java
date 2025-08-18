package com.prize.lottery.domain.icai.fc3d;

import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.ComRecommend;
import com.prize.lottery.value.RecValueItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Fc3dComRecommendDo {

    private String        period;
    private ComRecommend  zu6;
    private ComRecommend  zu3;
    private Integer       type;
    private Integer       hit;
    private LocalDateTime calcTime;

    public Fc3dComRecommendDo(String period, ComRecommend zu6, ComRecommend zu3) {
        this.period = period;
        this.zu6    = zu6;
        this.zu3    = zu3;
    }

    public void calcHit(List<String> lottos) {
        calcTime = LocalDateTime.now();
        Map<String, Integer> judgeLottery = ICaiConstants.judgeLottery(lottos);
        this.type = judgeLottery.size();
        int hit = 0;
        //组三计算
        if (judgeLottery.size() <= 2) {
            for (RecValueItem item : zu3.getItems()) {
                String value = item.getValue();
                long   count = judgeLottery.entrySet().stream().filter(e -> value.contains(e.getKey())).count();
                if (count == 2) {
                    item.setHit(1);
                    hit = 1;
                }
            }
        } else {
            for (RecValueItem item : zu6.getItems()) {
                String value = item.getValue();
                long   count = judgeLottery.entrySet().stream().filter(e -> value.contains(e.getKey())).count();
                if (count == 3) {
                    item.setHit(1);
                    hit = 1;
                }
            }
        }
        this.hit = hit;
    }
}
